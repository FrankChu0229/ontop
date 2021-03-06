package it.unibz.inf.ontop.injection.impl;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Module;
import it.unibz.inf.ontop.iq.executor.ProposalExecutor;
import it.unibz.inf.ontop.iq.executor.construction.ConstructionNodeCleaningExecutor;
import it.unibz.inf.ontop.iq.executor.expression.PushDownBooleanExpressionExecutor;
import it.unibz.inf.ontop.iq.executor.expression.PushUpBooleanExpressionExecutor;
import it.unibz.inf.ontop.iq.executor.groundterm.GroundTermRemovalFromDataNodeExecutor;
import it.unibz.inf.ontop.iq.executor.join.InnerJoinExecutor;
import it.unibz.inf.ontop.iq.executor.leftjoin.LeftJoinExecutor;
import it.unibz.inf.ontop.iq.executor.merging.QueryMergingExecutor;
import it.unibz.inf.ontop.iq.executor.projection.ProjectionShrinkingExecutor;
import it.unibz.inf.ontop.iq.executor.pullout.PullVariableOutOfDataNodeExecutor;
import it.unibz.inf.ontop.iq.executor.pullout.PullVariableOutOfSubTreeExecutor;
import it.unibz.inf.ontop.iq.executor.substitution.SubstitutionPropagationExecutor;
import it.unibz.inf.ontop.iq.executor.truenode.TrueNodeRemovalExecutor;
import it.unibz.inf.ontop.iq.executor.union.FlattenUnionExecutor;
import it.unibz.inf.ontop.iq.executor.union.UnionLiftExecutor;
import it.unibz.inf.ontop.iq.executor.unsatisfiable.RemoveEmptyNodesExecutor;
import it.unibz.inf.ontop.injection.OntopOptimizationConfiguration;
import it.unibz.inf.ontop.injection.OntopOptimizationSettings;
import it.unibz.inf.ontop.iq.proposal.*;

import java.util.Properties;
import java.util.stream.Stream;

public class OntopOptimizationConfigurationImpl extends OntopModelConfigurationImpl
        implements OntopOptimizationConfiguration {

    protected OntopOptimizationConfigurationImpl(OntopOptimizationSettings settings, OntopOptimizationOptions options) {
        super(settings, options.getModelOptions());
    }

    public static class OntopOptimizationOptions {
        private final OntopModelConfigurationOptions modelOptions;

        OntopOptimizationOptions(OntopModelConfigurationOptions modelOptions) {
            this.modelOptions = modelOptions;
        }

        public OntopModelConfigurationOptions getModelOptions() {
            return modelOptions;
        }
    }

    @Override
    public OntopOptimizationSettings getSettings() {
        return (OntopOptimizationSettings) super.getSettings();
    }

    /**
     * To be overloaded
     *
     */
    @Override
    protected Stream<Module> buildGuiceModules() {
        return Stream.concat(
                super.buildGuiceModules(),
                Stream.of(new OntopOptimizationModule(this)));
    }

    /**
     * Can be overloaded by sub-classes
     */
    @Override
    protected ImmutableMap<Class<? extends QueryOptimizationProposal>, Class<? extends ProposalExecutor>>
    generateOptimizationConfigurationMap() {
        ImmutableMap.Builder<Class<? extends QueryOptimizationProposal>, Class<? extends ProposalExecutor>>
                internalExecutorMapBuilder = ImmutableMap.builder();
        internalExecutorMapBuilder.putAll(super.generateOptimizationConfigurationMap());

        internalExecutorMapBuilder.put(InnerJoinOptimizationProposal.class, InnerJoinExecutor.class);
        internalExecutorMapBuilder.put(SubstitutionPropagationProposal.class, SubstitutionPropagationExecutor.class);
        internalExecutorMapBuilder.put(PushDownBooleanExpressionProposal.class, PushDownBooleanExpressionExecutor.class);
        internalExecutorMapBuilder.put(PushUpBooleanExpressionProposal.class, PushUpBooleanExpressionExecutor.class);
        internalExecutorMapBuilder.put(GroundTermRemovalFromDataNodeProposal.class, GroundTermRemovalFromDataNodeExecutor.class);
        internalExecutorMapBuilder.put(PullVariableOutOfDataNodeProposal.class, PullVariableOutOfDataNodeExecutor.class);
        internalExecutorMapBuilder.put(PullVariableOutOfSubTreeProposal.class, PullVariableOutOfSubTreeExecutor.class);
        internalExecutorMapBuilder.put(RemoveEmptyNodeProposal.class, RemoveEmptyNodesExecutor.class);
        internalExecutorMapBuilder.put(QueryMergingProposal.class, QueryMergingExecutor.class);
        internalExecutorMapBuilder.put(UnionLiftProposal.class, UnionLiftExecutor.class);
        internalExecutorMapBuilder.put(LeftJoinOptimizationProposal.class, LeftJoinExecutor.class);
        internalExecutorMapBuilder.put(ProjectionShrinkingProposal.class, ProjectionShrinkingExecutor.class);
        internalExecutorMapBuilder.put(TrueNodeRemovalProposal.class, TrueNodeRemovalExecutor.class);
        internalExecutorMapBuilder.put(FlattenUnionProposal.class, FlattenUnionExecutor.class);
        internalExecutorMapBuilder.put(ConstructionNodeCleaningProposal.class, ConstructionNodeCleaningExecutor.class);
        return internalExecutorMapBuilder.build();
    }

    protected static class DefaultOntopOptimizationBuilderFragment<B extends OntopOptimizationConfiguration.Builder<B>>
            implements OntopOptimizationBuilderFragment<B> {

        private final B builder;

        /**
         * For sub-classes ONLY!
         */
        protected DefaultOntopOptimizationBuilderFragment() {
            builder = (B) this;
        }

        /**
         * When not inheriting
         */
        protected DefaultOntopOptimizationBuilderFragment(B builder) {
            this.builder = builder;
        }

        protected Properties generateProperties() {
            return new Properties();
        }

        protected final OntopOptimizationOptions generateOptimizationOptions(
                OntopModelConfigurationOptions modelOptions) {
            return new OntopOptimizationOptions(modelOptions);
        }

    }

    /**
     * Inherits from DefaultOntopModelBuilderFragment because it has more methods
     * than DefaultOntopOptimizationBuilderFragment (more convenient).
     */
    protected static abstract class AbstractOntopOptimizationBuilderMixin<B extends OntopOptimizationConfiguration.Builder<B>>
            extends DefaultOntopModelBuilderFragment<B>
            implements OntopOptimizationConfiguration.Builder<B> {

        private final DefaultOntopOptimizationBuilderFragment<B> optimizationBuilderFragment;

        protected AbstractOntopOptimizationBuilderMixin() {
            optimizationBuilderFragment = new DefaultOntopOptimizationBuilderFragment<>((B)this);
        }

        @Override
        protected Properties generateProperties() {
            // Properties from OntopModelBuilderFragmentImpl
            Properties properties = super.generateProperties();
            // Higher priority (however should be orthogonal) for the OntopOptimizationBuilderFragment.
            properties.putAll(optimizationBuilderFragment.generateProperties());

            return properties;
        }

        protected OntopOptimizationOptions generateOntopOptimizationConfigurationOptions() {
            OntopModelConfigurationOptions modelOptions = generateModelOptions();
            return optimizationBuilderFragment.generateOptimizationOptions(modelOptions);
        }
    }


    public final static class BuilderImpl<B extends OntopOptimizationConfiguration.Builder<B>>
            extends AbstractOntopOptimizationBuilderMixin<B> {

        @Override
        public OntopOptimizationConfiguration build() {
            Properties properties = generateProperties();

            OntopOptimizationOptions options = generateOntopOptimizationConfigurationOptions();
            OntopOptimizationSettings settings = new OntopOptimizationSettingsImpl(properties);

            return new OntopOptimizationConfigurationImpl(settings, options);
        }
    }
}
