package it.unibz.inf.ontop.owlrefplatform.core;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import it.unibz.inf.ontop.exception.OntopInvalidInputQueryException;
import it.unibz.inf.ontop.exception.OntopReformulationException;
import it.unibz.inf.ontop.injection.OntopQueryAnsweringSettings;
import it.unibz.inf.ontop.injection.ReformulationFactory;
import it.unibz.inf.ontop.mapping.Mapping;
import it.unibz.inf.ontop.model.*;
import it.unibz.inf.ontop.model.impl.OBDAVocabulary;
import it.unibz.inf.ontop.owlrefplatform.core.basicoperations.*;
import it.unibz.inf.ontop.owlrefplatform.core.dagjgrapht.TBoxReasoner;
import it.unibz.inf.ontop.owlrefplatform.core.mappingprocessing.MappingSameAsPredicateExtractor;
import it.unibz.inf.ontop.owlrefplatform.core.optimization.*;
import it.unibz.inf.ontop.answering.reformulation.IRIDictionary;
import it.unibz.inf.ontop.answering.reformulation.unfolding.QueryUnfolder;
import it.unibz.inf.ontop.owlrefplatform.core.reformulation.QueryRewriter;
import it.unibz.inf.ontop.owlrefplatform.core.srcquerygeneration.NativeQueryGenerator;
import it.unibz.inf.ontop.owlrefplatform.core.translator.*;
import it.unibz.inf.ontop.pivotalrepr.EmptyQueryException;
import it.unibz.inf.ontop.pivotalrepr.IntermediateQuery;
import it.unibz.inf.ontop.pivotalrepr.MetadataForQueryOptimization;
import it.unibz.inf.ontop.pivotalrepr.datalog.DatalogProgram2QueryConverter;
import it.unibz.inf.ontop.pivotalrepr.utils.ExecutorRegistry;
import it.unibz.inf.ontop.renderer.DatalogProgramRenderer;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import it.unibz.inf.ontop.spec.OBDASpecification;
import it.unibz.inf.ontop.answering.reformulation.OntopQueryReformulator;
import org.eclipse.rdf4j.query.MalformedQueryException;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.UnsupportedQueryLanguageException;
import org.eclipse.rdf4j.query.parser.ParsedQuery;
import org.eclipse.rdf4j.query.parser.QueryParser;
import org.eclipse.rdf4j.query.parser.QueryParserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;

import static it.unibz.inf.ontop.model.impl.OntopModelSingletons.DATA_FACTORY;

/**
 * TODO: rename it OntopQueryReformulatorImpl ?
 */
public class QuestQueryProcessor implements OntopQueryReformulator {

	private final Map<String, ParsedQuery> parsedQueryCache = new ConcurrentHashMap<>();

	private final QueryRewriter rewriter;
	private final LinearInclusionDependencies sigma;
	private final VocabularyValidator vocabularyValidator;
	private final Optional<IRIDictionary> iriDictionary;
	private final NativeQueryGenerator datasourceQueryGenerator;
	private final QueryCache queryCache;

	private final QueryUnfolder queryUnfolder;
	
	private static final Logger log = LoggerFactory.getLogger(QuestQueryProcessor.class);
	private final ExecutorRegistry executorRegistry;
	private final MetadataForQueryOptimization metadataForOptimization;
	private final DatalogProgram2QueryConverter datalogConverter;
	private final ImmutableSet<Predicate> dataPropertiesAndClassesMapped;
	private final ImmutableSet<Predicate> objectPropertiesMapped;
	private final OntopQueryAnsweringSettings settings;

	@AssistedInject
	private QuestQueryProcessor(@Assisted OBDASpecification obdaSpecification,
                                @Assisted ExecutorRegistry executorRegistry,
                                @Nullable IRIDictionary iriDictionary,
                                QueryCache queryCache,
                                OntopQueryAnsweringSettings settings,
                                DatalogProgram2QueryConverter datalogConverter,
                                ReformulationFactory reformulationFactory,
								QueryRewriter rewriter) {
		TBoxReasoner saturatedTBox = obdaSpecification.getSaturatedTBox();
		this.sigma = LinearInclusionDependencyTools.getABoxDependencies(saturatedTBox, true);

		this.rewriter = rewriter;
		this.rewriter.setTBox(saturatedTBox, obdaSpecification.getVocabulary(), sigma);

		Mapping saturatedMapping = obdaSpecification.getSaturatedMapping();

		this.queryUnfolder = reformulationFactory.create(saturatedMapping);
		this.metadataForOptimization = saturatedMapping.getMetadataForOptimization();

		this.vocabularyValidator = new VocabularyValidator(obdaSpecification.getSaturatedTBox(),
				obdaSpecification.getVocabulary());
		this.iriDictionary = Optional.ofNullable(iriDictionary);
		this.datasourceQueryGenerator = reformulationFactory.create(metadataForOptimization.getDBMetadata());
		this.queryCache = queryCache;
		this.settings = settings;
		this.executorRegistry = executorRegistry;
		this.datalogConverter = datalogConverter;

		if (settings.isSameAsInMappingsEnabled()) {
			MappingSameAsPredicateExtractor msa = new MappingSameAsPredicateExtractor(saturatedMapping);
			dataPropertiesAndClassesMapped = msa.getDataPropertiesAndClassesWithSameAs();
			objectPropertiesMapped = msa.getObjectPropertiesWithSameAs();
		} else {
			dataPropertiesAndClassesMapped = ImmutableSet.of();
			objectPropertiesMapped = ImmutableSet.of();
		}
	}

	/**
	 * BC: TODO: rename parseSPARQL
     */
	@Override
	public ParsedQuery getParsedQuery(String sparql) throws OntopInvalidInputQueryException {
		ParsedQuery pq = parsedQueryCache.get(sparql);
		if (pq == null) {
			try {
				QueryParser parser = QueryParserUtil.createParser(QueryLanguage.SPARQL);
				pq = parser.parseQuery(sparql, null);
				parsedQueryCache.put(sparql, pq);
			} catch (UnsupportedQueryLanguageException | MalformedQueryException e) {
				throw new OntopInvalidInputQueryException(e);
			}
		}
		return pq;
	}

	
	private DatalogProgram translateAndPreProcess(ParsedQuery pq) throws OntopInvalidInputQueryException {
		SparqlAlgebraToDatalogTranslator translator = new SparqlAlgebraToDatalogTranslator(
				metadataForOptimization.getUriTemplateMatcher(), iriDictionary);
		SparqlQuery translation = translator.translate(pq);
		return preProcess(translation);
	}

	private DatalogProgram preProcess(SparqlQuery translation) {
		DatalogProgram program = translation.getProgram();
		log.debug("Datalog program translated from the SPARQL query: \n{}", program);

		if (settings.isSameAsInMappingsEnabled()) {
			SameAsRewriter sameAs = new SameAsRewriter(dataPropertiesAndClassesMapped, objectPropertiesMapped);
			program = sameAs.getSameAsRewriting(program);
			//System.out.println("SAMEAS" + program);
		}

		log.debug("Replacing equivalences...");
		DatalogProgram newprogramEq = DATA_FACTORY.getDatalogProgram(program.getQueryModifiers());
		Predicate topLevelPredicate = null;
		for (CQIE query : program.getRules()) {
			// TODO: fix cloning
			CQIE rule = query.clone();
			// TODO: get rid of EQNormalizer
			EQNormalizer.enforceEqualities(rule);

			CQIE newquery = vocabularyValidator.replaceEquivalences(rule);
			if (newquery.getHead().getFunctionSymbol().getName().equals(OBDAVocabulary.QUEST_QUERY))
				topLevelPredicate = newquery.getHead().getFunctionSymbol();
			newprogramEq.appendRule(newquery);
		}

		SPARQLQueryFlattener fl = new SPARQLQueryFlattener(newprogramEq);
		List<CQIE> p = fl.flatten(newprogramEq.getRules(topLevelPredicate).get(0));
		DatalogProgram newprogram = DATA_FACTORY.getDatalogProgram(program.getQueryModifiers(), p);

		return newprogram;
	}
	
	
	public void clearNativeQueryCache() {
		queryCache.clear();
	}
	

	@Override
	public ExecutableQuery translateIntoNativeQuery(ParsedQuery pq,
													Optional<SesameConstructTemplate> optionalConstructTemplate)
			throws OntopReformulationException {

		ExecutableQuery executableQuery = queryCache.get(pq);
		if (executableQuery != null)
			return executableQuery;

		try {
			SparqlAlgebraToDatalogTranslator translator = new SparqlAlgebraToDatalogTranslator(
					metadataForOptimization.getUriTemplateMatcher(), iriDictionary);
			SparqlQuery translation = translator.translate(pq);
			DatalogProgram newprogram = preProcess(translation);

			for (CQIE q : newprogram.getRules()) 
				DatalogNormalizer.unfoldJoinTrees(q);
			log.debug("Normalized program: \n{}", newprogram);

			if (newprogram.getRules().size() < 1)
				throw new OntopInvalidInputQueryException("Error, the translation of the query generated 0 rules. " +
						"This is not possible for any SELECT query (other queries are not supported by the translator).");

			log.debug("Start the rewriting process...");

			//final long startTime0 = System.currentTimeMillis();
			for (CQIE cq : newprogram.getRules())
				CQCUtilities.optimizeQueryWithSigmaRules(cq.getBody(), sigma);
			DatalogProgram programAfterRewriting = rewriter.rewrite(newprogram);

			//rewritingTime = System.currentTimeMillis() - startTime0;

			//final long startTime = System.currentTimeMillis();

			DatalogProgram programAfterUnfolding;
			try {
				IntermediateQuery intermediateQuery = datalogConverter.convertDatalogProgram(
						metadataForOptimization, programAfterRewriting, ImmutableList.of(), executorRegistry);

				log.debug("Directly translated (SPARQL) intermediate query: \n" + intermediateQuery.toString());

				log.debug("Start the unfolding...");

				intermediateQuery = queryUnfolder.optimize(intermediateQuery);

				log.debug("Unfolded query: \n" + intermediateQuery.toString());


				//lift bindings and union when it is possible
				IntermediateQueryOptimizer substitutionOptimizer = new FixedPointBindingLiftOptimizer();
				intermediateQuery = substitutionOptimizer.optimize(intermediateQuery);


				log.debug("New lifted query: \n" + intermediateQuery.toString());


				JoinLikeOptimizer joinLikeOptimizer = new FixedPointJoinLikeOptimizer();
				intermediateQuery = joinLikeOptimizer.optimize(intermediateQuery);
				log.debug("New query after fixed point join optimization: \n" + intermediateQuery.toString());

//				BasicLeftJoinOptimizer leftJoinOptimizer = new BasicLeftJoinOptimizer();
//				intermediateQuery = leftJoinOptimizer.optimize(intermediateQuery);
//				log.debug("New query after left join optimization: \n" + intermediateQuery.toString());
//
//				BasicJoinOptimizer joinOptimizer = new BasicJoinOptimizer();
//				intermediateQuery = joinOptimizer.optimize(intermediateQuery);
//				log.debug("New query after join optimization: \n" + intermediateQuery.toString());

				executableQuery = generateExecutableQuery(intermediateQuery, ImmutableList.copyOf(translation.getSignature()),
						optionalConstructTemplate);
				queryCache.put(pq, executableQuery);
				return executableQuery;

			}
			/**
			 * No solution.
			 */
			catch (EmptyQueryException e) {
				ExecutableQuery emptyQuery = datasourceQueryGenerator.generateEmptyQuery(
						ImmutableList.copyOf(translation.getSignature()), optionalConstructTemplate);

				log.debug("Empty query --> no solution.");
				queryCache.put(pq, emptyQuery);
				return emptyQuery;
			}

			//unfoldingTime = System.currentTimeMillis() - startTime;
		}
		catch (OntopReformulationException e) {
			throw e;
		}
		/*
		 * Bug: should normally not be reached
		 * TODO: remove it
		 */
		catch (Exception e) {
			log.warn("Unexpected exception: " + e.getMessage(), e);
			e.printStackTrace();
			throw new OntopReformulationException(e);
			//throw new OntopReformulationException("Error rewriting and unfolding into SQL\n" + e.getMessage());
		}
	}

	private ExecutableQuery generateExecutableQuery(IntermediateQuery intermediateQuery, ImmutableList<String> signature,
													Optional<SesameConstructTemplate> optionalConstructTemplate)
			throws OntopReformulationException {
		log.debug("Producing the native query string...");

		ExecutableQuery executableQuery = datasourceQueryGenerator.generateSourceQuery(intermediateQuery, signature,
				optionalConstructTemplate);

		log.debug("Resulting native query: \n{}", executableQuery);

		return executableQuery;
	}


	/**
	 * Returns the final rewriting of the given query
	 */
	@Override
	public String getRewriting(ParsedQuery query) throws OntopReformulationException {
		try {
			DatalogProgram program = translateAndPreProcess(query);
			DatalogProgram rewriting = rewriter.rewrite(program);
			return DatalogProgramRenderer.encode(rewriting);
		}
		catch (OntopReformulationException e) {
			throw e;
		}
		/*
		 * Bug: should be reached
		 * TODO: remove it
		 */
		catch (Exception e) {
			log.debug("Unexpected exception: " + e.getMessage(), e);
			throw new OntopReformulationException(e);
		}
	}

	@Override
	public boolean hasDistinctResultSet() {
		return settings.isDistinctPostProcessingEnabled();
	}

	@Override
	public DBMetadata getDBMetadata() {
		return metadataForOptimization.getDBMetadata();
	}
}