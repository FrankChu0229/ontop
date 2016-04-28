// $ANTLR 3.5.2 TurtleOBDA.g 2016-04-28 14:51:16

package it.unibz.krdb.obda.parser;

import it.unibz.krdb.obda.model.CQIE;
import it.unibz.krdb.obda.model.Constant;
import it.unibz.krdb.obda.model.Function;
import it.unibz.krdb.obda.model.Term;
import it.unibz.krdb.obda.model.OBDADataFactory;
import it.unibz.krdb.obda.model.DatatypeFactory;
import it.unibz.krdb.obda.model.OBDALibConstants;
import it.unibz.krdb.obda.model.Predicate;
import it.unibz.krdb.obda.model.URIConstant;
import it.unibz.krdb.obda.model.ValueConstant;
import it.unibz.krdb.obda.model.Variable;
import it.unibz.krdb.obda.model.Predicate.COL_TYPE;
import it.unibz.krdb.obda.model.impl.OBDADataFactoryImpl;
import it.unibz.krdb.obda.model.impl.OBDAVocabulary;
import it.unibz.krdb.obda.utils.QueryUtils;
import it.unibz.krdb.obda.model.URITemplatePredicate;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.antlr.runtime.BitSet;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.MismatchedTokenException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class TurtleOBDAParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "ALPHA", "ALPHANUM", "AMPERSAND", 
		"ANON", "APOSTROPHE", "ASTERISK", "AT", "BACKSLASH", "BASE", "BLANK_NODE_LABEL", 
		"BLANK_PREFIX", "CARET", "CHAR", "COLON", "COMMA", "DECIMAL", "DECIMAL_NEGATIVE", 
		"DECIMAL_POSITIVE", "DIGIT", "DOLLAR", "DOUBLE", "DOUBLE_NEGATIVE", "DOUBLE_POSITIVE", 
		"DOUBLE_SLASH", "ECHAR", "EQUALS", "EXCLAMATION", "FALSE", "GREATER", 
		"HASH", "ID", "ID_CORE", "ID_START", "INTEGER", "INTEGER_NEGATIVE", "INTEGER_POSITIVE", 
		"LCR_BRACKET", "LESS", "LPAREN", "LSQ_BRACKET", "LTSIGN", "MINUS", "NAMESPACE", 
		"NAME_CHAR", "NAME_START_CHAR", "NCNAME", "NCNAME_EXT", "PERCENT", "PERIOD", 
		"PLUS", "PREFIX", "PREFIXED_NAME", "QUESTION", "QUOTE_DOUBLE", "QUOTE_SINGLE", 
		"RCR_BRACKET", "REFERENCE", "RPAREN", "RSQ_BRACKET", "RTSIGN", "SCHEMA", 
		"SEMI", "SLASH", "STRING_URI", "STRING_WITH_BRACKET", "STRING_WITH_CURLY_BRACKET", 
		"STRING_WITH_QUOTE", "STRING_WITH_QUOTE_DOUBLE", "TILDE", "TRUE", "UNDERSCORE", 
		"URI_PATH", "VARNAME", "WS", "'a'"
	};
	public static final int EOF=-1;
	public static final int T__78=78;
	public static final int ALPHA=4;
	public static final int ALPHANUM=5;
	public static final int AMPERSAND=6;
	public static final int ANON=7;
	public static final int APOSTROPHE=8;
	public static final int ASTERISK=9;
	public static final int AT=10;
	public static final int BACKSLASH=11;
	public static final int BASE=12;
	public static final int BLANK_NODE_LABEL=13;
	public static final int BLANK_PREFIX=14;
	public static final int CARET=15;
	public static final int CHAR=16;
	public static final int COLON=17;
	public static final int COMMA=18;
	public static final int DECIMAL=19;
	public static final int DECIMAL_NEGATIVE=20;
	public static final int DECIMAL_POSITIVE=21;
	public static final int DIGIT=22;
	public static final int DOLLAR=23;
	public static final int DOUBLE=24;
	public static final int DOUBLE_NEGATIVE=25;
	public static final int DOUBLE_POSITIVE=26;
	public static final int DOUBLE_SLASH=27;
	public static final int ECHAR=28;
	public static final int EQUALS=29;
	public static final int EXCLAMATION=30;
	public static final int FALSE=31;
	public static final int GREATER=32;
	public static final int HASH=33;
	public static final int ID=34;
	public static final int ID_CORE=35;
	public static final int ID_START=36;
	public static final int INTEGER=37;
	public static final int INTEGER_NEGATIVE=38;
	public static final int INTEGER_POSITIVE=39;
	public static final int LCR_BRACKET=40;
	public static final int LESS=41;
	public static final int LPAREN=42;
	public static final int LSQ_BRACKET=43;
	public static final int LTSIGN=44;
	public static final int MINUS=45;
	public static final int NAMESPACE=46;
	public static final int NAME_CHAR=47;
	public static final int NAME_START_CHAR=48;
	public static final int NCNAME=49;
	public static final int NCNAME_EXT=50;
	public static final int PERCENT=51;
	public static final int PERIOD=52;
	public static final int PLUS=53;
	public static final int PREFIX=54;
	public static final int PREFIXED_NAME=55;
	public static final int QUESTION=56;
	public static final int QUOTE_DOUBLE=57;
	public static final int QUOTE_SINGLE=58;
	public static final int RCR_BRACKET=59;
	public static final int REFERENCE=60;
	public static final int RPAREN=61;
	public static final int RSQ_BRACKET=62;
	public static final int RTSIGN=63;
	public static final int SCHEMA=64;
	public static final int SEMI=65;
	public static final int SLASH=66;
	public static final int STRING_URI=67;
	public static final int STRING_WITH_BRACKET=68;
	public static final int STRING_WITH_CURLY_BRACKET=69;
	public static final int STRING_WITH_QUOTE=70;
	public static final int STRING_WITH_QUOTE_DOUBLE=71;
	public static final int TILDE=72;
	public static final int TRUE=73;
	public static final int UNDERSCORE=74;
	public static final int URI_PATH=75;
	public static final int VARNAME=76;
	public static final int WS=77;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public TurtleOBDAParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public TurtleOBDAParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	@Override public String[] getTokenNames() { return TurtleOBDAParser.tokenNames; }
	@Override public String getGrammarFileName() { return "TurtleOBDA.g"; }


	/** Map of directives */
	private HashMap<String, String> directives = new HashMap<String, String>();

	/** The current subject term */
	private Term currentSubject;

	/** All variables */
	private Set<Term> variableSet = new HashSet<Term>();

	/** A factory to construct the predicates and terms */
	private static final OBDADataFactory dfac = OBDADataFactoryImpl.getInstance();
	private static final DatatypeFactory dtfac = OBDADataFactoryImpl.getInstance().getDatatypeFactory();

	private String error = "";

	public String getError() {
	   return error;
	}

	protected void mismatch(IntStream input, int ttype, BitSet follow) throws RecognitionException {
	   throw new MismatchedTokenException(ttype, input);
	}

	public Object recoverFromMismatchedSet(IntStream input, RecognitionException e, BitSet follow) throws RecognitionException {
	   throw e;
	}

	@Override
	public void recover(IntStream input, RecognitionException re) {
	   throw new RuntimeException(error);
	}

	@Override
	public void displayRecognitionError(String[] tokenNames, RecognitionException e) {
	   String hdr = getErrorHeader(e);
	   String msg = getErrorMessage(e, tokenNames);
	   emitErrorMessage("Syntax error: " + msg + " Location: " + hdr);
	}

	@Override
	public void emitErrorMessage(String msg) {
	   error = msg;
	}
	    
	public Object recoverFromMismatchedTokenrecoverFromMismatchedToken(IntStream input, int ttype, BitSet follow) throws RecognitionException {
	   throw new RecognitionException(input);
	}

	private String removeBrackets(String text) {
	   return text.substring(1, text.length()-1);
	}

		private Term construct(String text) {
		   Term toReturn = null;
		   final String PLACEHOLDER = "{}";
		   List<Term> terms = new LinkedList<Term>();
		   List<FormatString> tokens = parse(text);
		   int size = tokens.size();
		   if (size == 1) {
		      FormatString token = tokens.get(0);
		      if (token instanceof FixedString) {
		          ValueConstant uriTemplate = dfac.getConstantLiteral(token.toString()); // a single URI template
		          toReturn = dfac.getUriTemplate(uriTemplate);
		      }
		      else if (token instanceof ColumnString) {
		         ValueConstant uriTemplate = dfac.getConstantLiteral(PLACEHOLDER); // a single URI template
		         Variable column = dfac.getVariable(token.toString());
		         terms.add(0, uriTemplate);
		         terms.add(column);
		         toReturn = dfac.getUriTemplate(terms);
		      }
		   }
		   else {
		      StringBuilder sb = new StringBuilder();
		      for(FormatString token : tokens) {
		         if (token instanceof FixedString) { // if part of URI template
		            sb.append(token.toString());
		         }
		         else if (token instanceof ColumnString) {
		            sb.append(PLACEHOLDER);
		            Variable column = dfac.getVariable(token.toString());
		            terms.add(column);
		         }
		      }
		      ValueConstant uriTemplate = dfac.getConstantLiteral(sb.toString()); // complete URI template
		      terms.add(0, uriTemplate);
		      toReturn = dfac.getUriTemplate(terms);
		   }
		   return toReturn;
		}

		/** A method which construct BNode templates according to the pattern given in the string text
		 *  It is very similar to construct function for URI templates
		 */
	    private Term constructBNode(String text) {
	       Term toReturn = null;
	       final String PLACEHOLDER = "{}";
	       List<Term> terms = new LinkedList<Term>();
	       List<FormatString> tokens = parse(text);
	       int size = tokens.size();
	       if (size == 1) {
	          FormatString token = tokens.get(0);
	          if (token instanceof FixedString) {
	              ValueConstant uriTemplate = dfac.getConstantLiteral(token.toString()); // a single URI template
	              toReturn = dfac.getBNodeTemplate(uriTemplate);
	          }
	          else if (token instanceof ColumnString) {
	             ValueConstant uriTemplate = dfac.getConstantLiteral(PLACEHOLDER); // a single URI template
	             Variable column = dfac.getVariable(token.toString());
	             terms.add(0, uriTemplate);
	             terms.add(column);
	             toReturn = dfac.getBNodeTemplate(terms);
	          }
	       }
	       else {
	          StringBuilder sb = new StringBuilder();
	          for(FormatString token : tokens) {
	             if (token instanceof FixedString) { // if part of URI template
	                sb.append(token.toString());
	             }
	             else if (token instanceof ColumnString) {
	                sb.append(PLACEHOLDER);
	                Variable column = dfac.getVariable(token.toString());
	                terms.add(column);
	             }
	          }
	          ValueConstant uriTemplate = dfac.getConstantLiteral(sb.toString()); // complete URI template
	          terms.add(0, uriTemplate);
	          toReturn = dfac.getBNodeTemplate(terms);
	       }
	       return toReturn;
	    }
		
	// Column placeholder pattern
	private static final String formatSpecifier = "\\{([^\\}]+)?\\}";
	private static Pattern chPattern = Pattern.compile(formatSpecifier);

	private List<FormatString> parse(String text) {
	   List<FormatString> toReturn = new ArrayList<FormatString>();
	   Matcher m = chPattern.matcher(text);
	   int i = 0;
	   while (i < text.length()) {
	      if (m.find(i)) {
	         if (m.start() != i) {
	            toReturn.add(new FixedString(text.substring(i, m.start())));
	         }
	         String value = m.group(1);
	         toReturn.add(new ColumnString(value));
	         i = m.end();
	      }
	      else {
	         toReturn.add(new FixedString(text.substring(i)));
	         break;
	      }
	   }
	   return toReturn;
	}

	private interface FormatString {
	   int index();
	   String toString();
	}

	private class FixedString implements FormatString {
	   private String s;
	   FixedString(String s) { this.s = s; }
	   @Override public int index() { return -1; }  // flag code for fixed string
	   @Override public String toString() { return s; }
	}

	private class ColumnString implements FormatString {
	   private String s;
	   ColumnString(String s) { this.s = s; }
	   @Override public int index() { return 0; }  // flag code for column string
	   @Override public String toString() { return s; }
	}

		//this function distinguishes curly bracket with 
		//back slash "\{" from curly bracket "{" 
		private int getIndexOfCurlyB(String str){
		   int i;
		   int j;
		   i = str.indexOf("{");
		   j = str.indexOf("\\{");
		      while((i-1 == j) &&(j != -1)){		
			i = str.indexOf("{",i+1);
			j = str.indexOf("\\{",j+1);		
		      }	
		  return i;
		}
		
		//in case of concat this function parses the literal 
		//and adds parsed constant literals and template literal to terms list
		private ArrayList<Term> addToTermsList(String str){
		   ArrayList<Term> terms = new ArrayList<Term>();
		   int i,j;
		   String st;
		   str = str.substring(1, str.length()-1);
		   while(str.contains("{")){
		      i = getIndexOfCurlyB(str);
		      if (i > 0){
		    	 st = str.substring(0,i);
		    	 st = st.replace("\\\\", "");
		         terms.add(dfac.getConstantLiteral(st));
		         str = str.substring(str.indexOf("{", i), str.length());
		      }else if (i == 0){
		         j = str.indexOf("}");
		         terms.add(dfac.getVariable(str.substring(1,j)));
		         str = str.substring(j+1,str.length());
		      } else {
		    	  break;
		      }
		   }
		   if(!str.equals("")){
		      str = str.replace("\\\\", "");
		      terms.add(dfac.getConstantLiteral(str));
		   }
		   return terms;
		}
		
		//this function returns nested concats 
		//in case of more than two terms need to be concatted
		private Term getNestedConcat(String str){
		   ArrayList<Term> terms = new ArrayList<Term>();
		   terms = addToTermsList(str);
		   if(terms.size() == 1){
		      Variable v = (Variable) terms.get(0);
	          variableSet.add(v);
	          return v;
		   }

		   Function f = dfac.getFunctionConcat(terms.get(0),terms.get(1));
	           for(int j=2;j<terms.size();j++){
	              f = dfac.getFunctionConcat(f,terms.get(j));
	           }

		   return f;
		}

	/**
	 * This methods construct an atom from a triple 
	 * 
	 * For the input (subject, pred, object), the result is 
	 * <ul>
	 *  <li> object(subject), if pred == rdf:type and subject is grounded ; </li>
	 *  <li> predicate(subject, object), if pred != rdf:type and predicate is grounded ; </li>
	 *  <li> triple(subject, pred, object), otherwise (it is a higher order atom). </li>
	 * </ul>
	 */
		private Function makeAtom(Term subject, Term pred, Term object) {
		     Function atom = null;

		        if (isRDFType(pred)) {
			             if (object instanceof  Function) {
			                  if(QueryUtils.isGrounded(object)) {
			                      ValueConstant c = ((ValueConstant) ((Function) object).getTerm(0));  // it has to be a URI constant
			                      Predicate predicate = dfac.getClassPredicate(c.getValue());
			                      atom = dfac.getFunction(predicate, subject);
			                  } else {
			                       atom = dfac.getTripleAtom(subject, pred, object);
			                  }
			             }
			             else if (object instanceof  Variable){
			                  Term uriOfPred = dfac.getUriTemplate(pred);
			                  Term uriOfObject = dfac.getUriTemplate(object);
			                  atom = dfac.getTripleAtom(subject, uriOfPred,  uriOfObject);
			              }
			             else {
			                  throw new IllegalArgumentException("parser cannot handle object " + object);
			              }
			        } else if( ! QueryUtils.isGrounded(pred) ){
			             atom = dfac.getTripleAtom(subject, pred,  object);
			        } else {
	                			             //Predicate predicate = dfac.getPredicate(pred.toString(), 2); // the data type cannot be determined here!
	                			             Predicate predicate;
	                			             if(pred instanceof Function) {
	                							 ValueConstant pr = (ValueConstant) ((Function) pred).getTerm(0);
	                							 if (object instanceof Variable) {
	                								 predicate = dfac.getPredicate(pr.getValue(), 2);
	                							 } else {
	                								 if (object instanceof Function) {
	                									 if (((Function) object).getFunctionSymbol() instanceof URITemplatePredicate) {

	                										 predicate = dfac.getObjectPropertyPredicate(pr.getValue());
	                									 } else {
	                										 predicate = dfac.getDataPropertyPredicate(pr.getValue());
	                									 }
	                								 }
	                									 else {
	                										 throw new IllegalArgumentException("parser cannot handle object " + object);
	                									 }
	                							 }
	                						 }else {
	                			                  throw new IllegalArgumentException("predicate should be a URI Function");
	                			             }
	                			             atom = dfac.getFunction(predicate, subject, object);
	                			       }
	                			       return atom;
		  }


	private static boolean isRDFType(Term pred) {
	//		if (pred instanceof Constant && ((Constant) pred).getValue().equals(OBDAVocabulary.RDF_TYPE)) {
	//			return true;
	//		}
			if (pred instanceof Function && ((Function) pred).getTerm(0) instanceof Constant ) {
				String c= ((Constant) ((Function) pred).getTerm(0)).getValue();
				return c.equals(OBDAVocabulary.RDF_TYPE);
			}	
			return false;
		}

	/**
	 * This method creates unique Bnode
	 *
	 */
	private Function createBNode() {
	    Function f;
	    List<Term> emptyTermList = new LinkedList<Term>();
	    f = dfac.getBNodeTemplate(emptyTermList);
	    return f;
	}





	// $ANTLR start "parse"
	// TurtleOBDA.g:442:1: parse returns [List<Function> value] : ( directiveStatement )* t1= triplesStatement (t2= triplesStatement )* EOF ;
	public final List<Function> parse() throws RecognitionException {
		List<Function> value = null;


		List<Function> t1 =null;
		List<Function> t2 =null;

		try {
			// TurtleOBDA.g:443:3: ( ( directiveStatement )* t1= triplesStatement (t2= triplesStatement )* EOF )
			// TurtleOBDA.g:443:5: ( directiveStatement )* t1= triplesStatement (t2= triplesStatement )* EOF
			{
			// TurtleOBDA.g:443:5: ( directiveStatement )*
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( (LA1_0==AT) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// TurtleOBDA.g:443:5: directiveStatement
					{
					pushFollow(FOLLOW_directiveStatement_in_parse56);
					directiveStatement();
					state._fsp--;

					}
					break;

				default :
					break loop1;
				}
			}

			pushFollow(FOLLOW_triplesStatement_in_parse65);
			t1=triplesStatement();
			state._fsp--;


			      value =  t1;
			    
			// TurtleOBDA.g:447:7: (t2= triplesStatement )*
			loop2:
			while (true) {
				int alt2=2;
				int LA2_0 = input.LA(1);
				if ( (LA2_0==ANON||LA2_0==BLANK_NODE_LABEL||LA2_0==PREFIXED_NAME||(LA2_0 >= STRING_WITH_BRACKET && LA2_0 <= STRING_WITH_CURLY_BRACKET)) ) {
					alt2=1;
				}

				switch (alt2) {
				case 1 :
					// TurtleOBDA.g:447:8: t2= triplesStatement
					{
					pushFollow(FOLLOW_triplesStatement_in_parse78);
					t2=triplesStatement();
					state._fsp--;


					      List<Function> additionalTriples = t2;
					      if (additionalTriples != null) {
					        // If there are additional triple statements then just add to the existing body
					        List<Function> existingBody = value;
					        existingBody.addAll(additionalTriples);
					      }
					    
					}
					break;

				default :
					break loop2;
				}
			}

			match(input,EOF,FOLLOW_EOF_in_parse85); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "parse"



	// $ANTLR start "directiveStatement"
	// TurtleOBDA.g:457:1: directiveStatement : directive PERIOD ;
	public final void directiveStatement() throws RecognitionException {
		try {
			// TurtleOBDA.g:458:3: ( directive PERIOD )
			// TurtleOBDA.g:458:5: directive PERIOD
			{
			pushFollow(FOLLOW_directive_in_directiveStatement98);
			directive();
			state._fsp--;

			match(input,PERIOD,FOLLOW_PERIOD_in_directiveStatement100); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "directiveStatement"



	// $ANTLR start "triplesStatement"
	// TurtleOBDA.g:461:1: triplesStatement returns [List<Function> value] : triples ( WS )* PERIOD ;
	public final List<Function> triplesStatement() throws RecognitionException {
		List<Function> value = null;


		List<Function> triples1 =null;

		try {
			// TurtleOBDA.g:462:3: ( triples ( WS )* PERIOD )
			// TurtleOBDA.g:462:5: triples ( WS )* PERIOD
			{
			pushFollow(FOLLOW_triples_in_triplesStatement117);
			triples1=triples();
			state._fsp--;

			// TurtleOBDA.g:462:13: ( WS )*
			loop3:
			while (true) {
				int alt3=2;
				int LA3_0 = input.LA(1);
				if ( (LA3_0==WS) ) {
					alt3=1;
				}

				switch (alt3) {
				case 1 :
					// TurtleOBDA.g:462:13: WS
					{
					match(input,WS,FOLLOW_WS_in_triplesStatement119); 
					}
					break;

				default :
					break loop3;
				}
			}

			match(input,PERIOD,FOLLOW_PERIOD_in_triplesStatement122); 
			 value = triples1; 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "triplesStatement"



	// $ANTLR start "directive"
	// TurtleOBDA.g:465:1: directive : ( base | prefixID );
	public final void directive() throws RecognitionException {
		try {
			// TurtleOBDA.g:466:3: ( base | prefixID )
			int alt4=2;
			int LA4_0 = input.LA(1);
			if ( (LA4_0==AT) ) {
				int LA4_1 = input.LA(2);
				if ( (LA4_1==BASE) ) {
					alt4=1;
				}
				else if ( (LA4_1==PREFIX) ) {
					alt4=2;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 4, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 4, 0, input);
				throw nvae;
			}

			switch (alt4) {
				case 1 :
					// TurtleOBDA.g:466:5: base
					{
					pushFollow(FOLLOW_base_in_directive137);
					base();
					state._fsp--;

					}
					break;
				case 2 :
					// TurtleOBDA.g:467:5: prefixID
					{
					pushFollow(FOLLOW_prefixID_in_directive143);
					prefixID();
					state._fsp--;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "directive"



	// $ANTLR start "base"
	// TurtleOBDA.g:470:1: base : AT BASE uriref ;
	public final void base() throws RecognitionException {
		try {
			// TurtleOBDA.g:471:3: ( AT BASE uriref )
			// TurtleOBDA.g:471:5: AT BASE uriref
			{
			match(input,AT,FOLLOW_AT_in_base156); 
			match(input,BASE,FOLLOW_BASE_in_base158); 
			pushFollow(FOLLOW_uriref_in_base160);
			uriref();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "base"



	// $ANTLR start "prefixID"
	// TurtleOBDA.g:474:1: prefixID : AT PREFIX ( namespace | defaultNamespace ) uriref ;
	public final void prefixID() throws RecognitionException {
		ParserRuleReturnScope namespace2 =null;
		ParserRuleReturnScope defaultNamespace3 =null;
		String uriref4 =null;


		  String prefix = "";

		try {
			// TurtleOBDA.g:478:3: ( AT PREFIX ( namespace | defaultNamespace ) uriref )
			// TurtleOBDA.g:478:5: AT PREFIX ( namespace | defaultNamespace ) uriref
			{
			match(input,AT,FOLLOW_AT_in_prefixID178); 
			match(input,PREFIX,FOLLOW_PREFIX_in_prefixID180); 
			// TurtleOBDA.g:478:15: ( namespace | defaultNamespace )
			int alt5=2;
			int LA5_0 = input.LA(1);
			if ( (LA5_0==NAMESPACE) ) {
				alt5=1;
			}
			else if ( (LA5_0==COLON) ) {
				alt5=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 5, 0, input);
				throw nvae;
			}

			switch (alt5) {
				case 1 :
					// TurtleOBDA.g:478:16: namespace
					{
					pushFollow(FOLLOW_namespace_in_prefixID183);
					namespace2=namespace();
					state._fsp--;

					 prefix = (namespace2!=null?input.toString(namespace2.start,namespace2.stop):null); 
					}
					break;
				case 2 :
					// TurtleOBDA.g:478:58: defaultNamespace
					{
					pushFollow(FOLLOW_defaultNamespace_in_prefixID189);
					defaultNamespace3=defaultNamespace();
					state._fsp--;

					 prefix = (defaultNamespace3!=null?input.toString(defaultNamespace3.start,defaultNamespace3.stop):null); 
					}
					break;

			}

			pushFollow(FOLLOW_uriref_in_prefixID194);
			uriref4=uriref();
			state._fsp--;


			      String uriref = uriref4;
			      directives.put(prefix.substring(0, prefix.length()-1), uriref); // remove the end colon
			    
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "prefixID"



	// $ANTLR start "triples"
	// TurtleOBDA.g:484:1: triples returns [List<Function> value] : subject predicateObjectList ;
	public final List<Function> triples() throws RecognitionException {
		List<Function> value = null;


		Term subject5 =null;
		List<Function> predicateObjectList6 =null;

		try {
			// TurtleOBDA.g:485:3: ( subject predicateObjectList )
			// TurtleOBDA.g:485:5: subject predicateObjectList
			{
			pushFollow(FOLLOW_subject_in_triples213);
			subject5=subject();
			state._fsp--;

			 currentSubject = subject5; 
			pushFollow(FOLLOW_predicateObjectList_in_triples217);
			predicateObjectList6=predicateObjectList();
			state._fsp--;


			      value = predicateObjectList6;
			    
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "triples"



	// $ANTLR start "predicateObjectList"
	// TurtleOBDA.g:490:1: predicateObjectList returns [List<Function> value] : v1= verb l1= objectList ( SEMI v2= verb l2= objectList )* ;
	public final List<Function> predicateObjectList() throws RecognitionException {
		List<Function> value = null;


		Term v1 =null;
		List<Term> l1 =null;
		Term v2 =null;
		List<Term> l2 =null;


		   value = new LinkedList<Function>();

		try {
			// TurtleOBDA.g:494:3: (v1= verb l1= objectList ( SEMI v2= verb l2= objectList )* )
			// TurtleOBDA.g:494:5: v1= verb l1= objectList ( SEMI v2= verb l2= objectList )*
			{
			pushFollow(FOLLOW_verb_in_predicateObjectList243);
			v1=verb();
			state._fsp--;

			pushFollow(FOLLOW_objectList_in_predicateObjectList249);
			l1=objectList();
			state._fsp--;


			      for (Term object : l1) {
			        Function atom = makeAtom(currentSubject, v1, object);
			        value.add(atom);
			      }
			    
			// TurtleOBDA.g:500:5: ( SEMI v2= verb l2= objectList )*
			loop6:
			while (true) {
				int alt6=2;
				int LA6_0 = input.LA(1);
				if ( (LA6_0==SEMI) ) {
					alt6=1;
				}

				switch (alt6) {
				case 1 :
					// TurtleOBDA.g:500:6: SEMI v2= verb l2= objectList
					{
					match(input,SEMI,FOLLOW_SEMI_in_predicateObjectList258); 
					pushFollow(FOLLOW_verb_in_predicateObjectList262);
					v2=verb();
					state._fsp--;

					pushFollow(FOLLOW_objectList_in_predicateObjectList266);
					l2=objectList();
					state._fsp--;


					      for (Term object : l2) {
					        Function atom = makeAtom(currentSubject, v2, object);
					        value.add(atom);
					      }
					    
					}
					break;

				default :
					break loop6;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "predicateObjectList"



	// $ANTLR start "verb"
	// TurtleOBDA.g:509:1: verb returns [Term value] : ( predicate | 'a' );
	public final Term verb() throws RecognitionException {
		Term value = null;


		Term predicate7 =null;

		try {
			// TurtleOBDA.g:510:3: ( predicate | 'a' )
			int alt7=2;
			int LA7_0 = input.LA(1);
			if ( (LA7_0==PREFIXED_NAME||LA7_0==STRING_WITH_BRACKET) ) {
				alt7=1;
			}
			else if ( (LA7_0==78) ) {
				alt7=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 7, 0, input);
				throw nvae;
			}

			switch (alt7) {
				case 1 :
					// TurtleOBDA.g:510:5: predicate
					{
					pushFollow(FOLLOW_predicate_in_verb290);
					predicate7=predicate();
					state._fsp--;

					 value = predicate7; 
					}
					break;
				case 2 :
					// TurtleOBDA.g:511:5: 'a'
					{
					match(input,78,FOLLOW_78_in_verb298); 

					  Term constant = dfac.getConstantLiteral(OBDAVocabulary.RDF_TYPE);
					  value = dfac.getUriTemplate(constant);
					  
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "verb"



	// $ANTLR start "objectList"
	// TurtleOBDA.g:517:1: objectList returns [List<Term> value] : o1= object ( COMMA o2= object )* ;
	public final List<Term> objectList() throws RecognitionException {
		List<Term> value = null;


		Term o1 =null;
		Term o2 =null;


		  value = new ArrayList<Term>();

		try {
			// TurtleOBDA.g:521:3: (o1= object ( COMMA o2= object )* )
			// TurtleOBDA.g:521:5: o1= object ( COMMA o2= object )*
			{
			pushFollow(FOLLOW_object_in_objectList324);
			o1=object();
			state._fsp--;

			 value.add(o1); 
			// TurtleOBDA.g:521:42: ( COMMA o2= object )*
			loop8:
			while (true) {
				int alt8=2;
				int LA8_0 = input.LA(1);
				if ( (LA8_0==COMMA) ) {
					alt8=1;
				}

				switch (alt8) {
				case 1 :
					// TurtleOBDA.g:521:43: COMMA o2= object
					{
					match(input,COMMA,FOLLOW_COMMA_in_objectList329); 
					pushFollow(FOLLOW_object_in_objectList333);
					o2=object();
					state._fsp--;

					 value.add(o2); 
					}
					break;

				default :
					break loop8;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "objectList"



	// $ANTLR start "subject"
	// TurtleOBDA.g:524:1: subject returns [Term value] : ( resource | variable | blank );
	public final Term subject() throws RecognitionException {
		Term value = null;


		Term resource8 =null;
		Variable variable9 =null;
		Term blank10 =null;

		try {
			// TurtleOBDA.g:525:3: ( resource | variable | blank )
			int alt9=3;
			switch ( input.LA(1) ) {
			case PREFIXED_NAME:
			case STRING_WITH_BRACKET:
				{
				alt9=1;
				}
				break;
			case STRING_WITH_CURLY_BRACKET:
				{
				alt9=2;
				}
				break;
			case ANON:
			case BLANK_NODE_LABEL:
				{
				alt9=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 9, 0, input);
				throw nvae;
			}
			switch (alt9) {
				case 1 :
					// TurtleOBDA.g:525:5: resource
					{
					pushFollow(FOLLOW_resource_in_subject355);
					resource8=resource();
					state._fsp--;

					 value = resource8; 
					}
					break;
				case 2 :
					// TurtleOBDA.g:526:5: variable
					{
					pushFollow(FOLLOW_variable_in_subject363);
					variable9=variable();
					state._fsp--;

					 value = variable9; 
					}
					break;
				case 3 :
					// TurtleOBDA.g:527:5: blank
					{
					pushFollow(FOLLOW_blank_in_subject371);
					blank10=blank();
					state._fsp--;

					 value = blank10; 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "subject"



	// $ANTLR start "predicate"
	// TurtleOBDA.g:531:1: predicate returns [Term value] : resource ;
	public final Term predicate() throws RecognitionException {
		Term value = null;


		Term resource11 =null;

		try {
			// TurtleOBDA.g:532:3: ( resource )
			// TurtleOBDA.g:532:5: resource
			{
			pushFollow(FOLLOW_resource_in_predicate392);
			resource11=resource();
			state._fsp--;


			  	value = resource11; 
			//      Term nl = resource11;
			//      if (nl instanceof URIConstant) {
			//        URIConstant c = (URIConstant) nl;
			//        value = c.getValue();
			//      } else {
			//        throw new RuntimeException("Unsupported predicate syntax: " + nl.toString());
			//      }
			    
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "predicate"



	// $ANTLR start "object"
	// TurtleOBDA.g:544:1: object returns [Term value] : ( resource | literal | typedLiteral | variable | blank );
	public final Term object() throws RecognitionException {
		Term value = null;


		Term resource12 =null;
		Term literal13 =null;
		Function typedLiteral14 =null;
		Variable variable15 =null;
		Term blank16 =null;

		try {
			// TurtleOBDA.g:545:3: ( resource | literal | typedLiteral | variable | blank )
			int alt10=5;
			switch ( input.LA(1) ) {
			case PREFIXED_NAME:
			case STRING_WITH_BRACKET:
				{
				alt10=1;
				}
				break;
			case DECIMAL:
			case DECIMAL_NEGATIVE:
			case DECIMAL_POSITIVE:
			case DOUBLE:
			case DOUBLE_NEGATIVE:
			case DOUBLE_POSITIVE:
			case FALSE:
			case INTEGER:
			case INTEGER_NEGATIVE:
			case INTEGER_POSITIVE:
			case STRING_WITH_QUOTE_DOUBLE:
			case TRUE:
				{
				alt10=2;
				}
				break;
			case STRING_WITH_CURLY_BRACKET:
				{
				int LA10_3 = input.LA(2);
				if ( (LA10_3==AT||LA10_3==REFERENCE) ) {
					alt10=3;
				}
				else if ( (LA10_3==COMMA||LA10_3==PERIOD||LA10_3==SEMI||LA10_3==WS) ) {
					alt10=4;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 10, 3, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case ANON:
			case BLANK_NODE_LABEL:
				{
				alt10=5;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 10, 0, input);
				throw nvae;
			}
			switch (alt10) {
				case 1 :
					// TurtleOBDA.g:545:5: resource
					{
					pushFollow(FOLLOW_resource_in_object411);
					resource12=resource();
					state._fsp--;

					 value = resource12; 
					}
					break;
				case 2 :
					// TurtleOBDA.g:546:5: literal
					{
					pushFollow(FOLLOW_literal_in_object419);
					literal13=literal();
					state._fsp--;

					 value = literal13; 
					}
					break;
				case 3 :
					// TurtleOBDA.g:547:5: typedLiteral
					{
					pushFollow(FOLLOW_typedLiteral_in_object428);
					typedLiteral14=typedLiteral();
					state._fsp--;

					 value = typedLiteral14; 
					}
					break;
				case 4 :
					// TurtleOBDA.g:548:5: variable
					{
					pushFollow(FOLLOW_variable_in_object436);
					variable15=variable();
					state._fsp--;

					 value = variable15; 
					}
					break;
				case 5 :
					// TurtleOBDA.g:549:5: blank
					{
					pushFollow(FOLLOW_blank_in_object444);
					blank16=blank();
					state._fsp--;

					 value = blank16; 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "object"



	// $ANTLR start "resource"
	// TurtleOBDA.g:552:1: resource returns [Term value] : ( uriref | qname );
	public final Term resource() throws RecognitionException {
		Term value = null;


		String uriref17 =null;
		String qname18 =null;

		try {
			// TurtleOBDA.g:553:4: ( uriref | qname )
			int alt11=2;
			int LA11_0 = input.LA(1);
			if ( (LA11_0==STRING_WITH_BRACKET) ) {
				alt11=1;
			}
			else if ( (LA11_0==PREFIXED_NAME) ) {
				alt11=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 11, 0, input);
				throw nvae;
			}

			switch (alt11) {
				case 1 :
					// TurtleOBDA.g:553:6: uriref
					{
					pushFollow(FOLLOW_uriref_in_resource465);
					uriref17=uriref();
					state._fsp--;

					 value = construct(uriref17); 
					}
					break;
				case 2 :
					// TurtleOBDA.g:554:6: qname
					{
					pushFollow(FOLLOW_qname_in_resource474);
					qname18=qname();
					state._fsp--;

					 value = construct(qname18); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "resource"



	// $ANTLR start "uriref"
	// TurtleOBDA.g:559:1: uriref returns [String value] : STRING_WITH_BRACKET ;
	public final String uriref() throws RecognitionException {
		String value = null;


		Token STRING_WITH_BRACKET19=null;

		try {
			// TurtleOBDA.g:560:3: ( STRING_WITH_BRACKET )
			// TurtleOBDA.g:560:5: STRING_WITH_BRACKET
			{
			STRING_WITH_BRACKET19=(Token)match(input,STRING_WITH_BRACKET,FOLLOW_STRING_WITH_BRACKET_in_uriref499); 
			 value = removeBrackets((STRING_WITH_BRACKET19!=null?STRING_WITH_BRACKET19.getText():null)); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "uriref"



	// $ANTLR start "qname"
	// TurtleOBDA.g:563:1: qname returns [String value] : PREFIXED_NAME ;
	public final String qname() throws RecognitionException {
		String value = null;


		Token PREFIXED_NAME20=null;

		try {
			// TurtleOBDA.g:564:3: ( PREFIXED_NAME )
			// TurtleOBDA.g:564:5: PREFIXED_NAME
			{
			PREFIXED_NAME20=(Token)match(input,PREFIXED_NAME,FOLLOW_PREFIXED_NAME_in_qname518); 

			      String[] tokens = (PREFIXED_NAME20!=null?PREFIXED_NAME20.getText():null).split(":", 2);
			      String uri = directives.get(tokens[0]);  // the first token is the prefix
			      value = uri + tokens[1];  // the second token is the local name
			    
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "qname"



	// $ANTLR start "blank"
	// TurtleOBDA.g:571:1: blank returns [Term value] : ( BLANK_NODE_LABEL | ANON );
	public final Term blank() throws RecognitionException {
		Term value = null;


		Token BLANK_NODE_LABEL21=null;

		try {
			// TurtleOBDA.g:572:3: ( BLANK_NODE_LABEL | ANON )
			int alt12=2;
			int LA12_0 = input.LA(1);
			if ( (LA12_0==BLANK_NODE_LABEL) ) {
				alt12=1;
			}
			else if ( (LA12_0==ANON) ) {
				alt12=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 12, 0, input);
				throw nvae;
			}

			switch (alt12) {
				case 1 :
					// TurtleOBDA.g:572:5: BLANK_NODE_LABEL
					{
					BLANK_NODE_LABEL21=(Token)match(input,BLANK_NODE_LABEL,FOLLOW_BLANK_NODE_LABEL_in_blank537); 
					 value = constructBNode((BLANK_NODE_LABEL21!=null?BLANK_NODE_LABEL21.getText():null)); 
					}
					break;
				case 2 :
					// TurtleOBDA.g:573:5: ANON
					{
					match(input,ANON,FOLLOW_ANON_in_blank545); 
					 value = createBNode(); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "blank"



	// $ANTLR start "variable"
	// TurtleOBDA.g:576:1: variable returns [Variable value] : STRING_WITH_CURLY_BRACKET ;
	public final Variable variable() throws RecognitionException {
		Variable value = null;


		Token STRING_WITH_CURLY_BRACKET22=null;

		try {
			// TurtleOBDA.g:577:3: ( STRING_WITH_CURLY_BRACKET )
			// TurtleOBDA.g:577:5: STRING_WITH_CURLY_BRACKET
			{
			STRING_WITH_CURLY_BRACKET22=(Token)match(input,STRING_WITH_CURLY_BRACKET,FOLLOW_STRING_WITH_CURLY_BRACKET_in_variable564); 

			      value = dfac.getVariable(removeBrackets((STRING_WITH_CURLY_BRACKET22!=null?STRING_WITH_CURLY_BRACKET22.getText():null)));
			      variableSet.add(value);
			    
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "variable"



	// $ANTLR start "function"
	// TurtleOBDA.g:583:1: function returns [Function value] : resource LPAREN terms RPAREN ;
	public final Function function() throws RecognitionException {
		Function value = null;


		Term resource23 =null;
		Vector<Term> terms24 =null;

		try {
			// TurtleOBDA.g:584:3: ( resource LPAREN terms RPAREN )
			// TurtleOBDA.g:584:5: resource LPAREN terms RPAREN
			{
			pushFollow(FOLLOW_resource_in_function585);
			resource23=resource();
			state._fsp--;

			match(input,LPAREN,FOLLOW_LPAREN_in_function587); 
			pushFollow(FOLLOW_terms_in_function589);
			terms24=terms();
			state._fsp--;

			match(input,RPAREN,FOLLOW_RPAREN_in_function591); 

			      String functionName = resource23.toString();
			      int arity = terms24.size();
			      Predicate functionSymbol = dfac.getPredicate(functionName, arity);
			      value = dfac.getFunction(functionSymbol, terms24);
			    
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "function"



	// $ANTLR start "typedLiteral"
	// TurtleOBDA.g:592:1: typedLiteral returns [Function value] : ( variable AT language | variable REFERENCE resource );
	public final Function typedLiteral() throws RecognitionException {
		Function value = null;


		Variable variable25 =null;
		Term language26 =null;
		Variable variable27 =null;
		Term resource28 =null;

		try {
			// TurtleOBDA.g:593:3: ( variable AT language | variable REFERENCE resource )
			int alt13=2;
			int LA13_0 = input.LA(1);
			if ( (LA13_0==STRING_WITH_CURLY_BRACKET) ) {
				int LA13_1 = input.LA(2);
				if ( (LA13_1==AT) ) {
					alt13=1;
				}
				else if ( (LA13_1==REFERENCE) ) {
					alt13=2;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 13, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 13, 0, input);
				throw nvae;
			}

			switch (alt13) {
				case 1 :
					// TurtleOBDA.g:593:5: variable AT language
					{
					pushFollow(FOLLOW_variable_in_typedLiteral610);
					variable25=variable();
					state._fsp--;

					match(input,AT,FOLLOW_AT_in_typedLiteral612); 
					pushFollow(FOLLOW_language_in_typedLiteral614);
					language26=language();
					state._fsp--;


					      Variable var = variable25;
					      Term lang = language26;   
					      value = dfac.getTypedTerm(var, lang);

					    
					}
					break;
				case 2 :
					// TurtleOBDA.g:599:5: variable REFERENCE resource
					{
					pushFollow(FOLLOW_variable_in_typedLiteral622);
					variable27=variable();
					state._fsp--;

					match(input,REFERENCE,FOLLOW_REFERENCE_in_typedLiteral624); 
					pushFollow(FOLLOW_resource_in_typedLiteral626);
					resource28=resource();
					state._fsp--;


					      Variable var = variable27;
					      //String functionName = resource28.toString();
					      // resource28 must be a URIConstant
					    String functionName = null;
					    if (resource28 instanceof Function){
					       functionName = ((ValueConstant) ((Function)resource28).getTerm(0)).getValue();
					    } else {
					        throw new IllegalArgumentException("resource28 should be an URI");
					    }
					    Predicate.COL_TYPE type = dtfac.getDatatype(functionName);
					    if (type == null)  
					 	  throw new RuntimeException("ERROR. A mapping involves an unsupported datatype. \nOffending datatype:" + functionName);
					    
					      value = dfac.getTypedTerm(var, type);

						
					     
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "typedLiteral"



	// $ANTLR start "language"
	// TurtleOBDA.g:619:1: language returns [Term value] : ( languageTag | variable );
	public final Term language() throws RecognitionException {
		Term value = null;


		ParserRuleReturnScope languageTag29 =null;
		Variable variable30 =null;

		try {
			// TurtleOBDA.g:620:3: ( languageTag | variable )
			int alt14=2;
			int LA14_0 = input.LA(1);
			if ( (LA14_0==VARNAME) ) {
				alt14=1;
			}
			else if ( (LA14_0==STRING_WITH_CURLY_BRACKET) ) {
				alt14=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 14, 0, input);
				throw nvae;
			}

			switch (alt14) {
				case 1 :
					// TurtleOBDA.g:620:5: languageTag
					{
					pushFollow(FOLLOW_languageTag_in_language645);
					languageTag29=languageTag();
					state._fsp--;


					    	value = dfac.getConstantLiteral((languageTag29!=null?input.toString(languageTag29.start,languageTag29.stop):null).toLowerCase(), COL_TYPE.STRING);
					    
					}
					break;
				case 2 :
					// TurtleOBDA.g:623:5: variable
					{
					pushFollow(FOLLOW_variable_in_language653);
					variable30=variable();
					state._fsp--;


					    	value = variable30;
					    
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "language"



	// $ANTLR start "terms"
	// TurtleOBDA.g:628:1: terms returns [Vector<Term> value] : t1= term ( COMMA t2= term )* ;
	public final Vector<Term> terms() throws RecognitionException {
		Vector<Term> value = null;


		Term t1 =null;
		Term t2 =null;


		  value = new Vector<Term>();

		try {
			// TurtleOBDA.g:632:3: (t1= term ( COMMA t2= term )* )
			// TurtleOBDA.g:632:5: t1= term ( COMMA t2= term )*
			{
			pushFollow(FOLLOW_term_in_terms679);
			t1=term();
			state._fsp--;

			 value.add(t1); 
			// TurtleOBDA.g:632:40: ( COMMA t2= term )*
			loop15:
			while (true) {
				int alt15=2;
				int LA15_0 = input.LA(1);
				if ( (LA15_0==COMMA) ) {
					alt15=1;
				}

				switch (alt15) {
				case 1 :
					// TurtleOBDA.g:632:41: COMMA t2= term
					{
					match(input,COMMA,FOLLOW_COMMA_in_terms684); 
					pushFollow(FOLLOW_term_in_terms688);
					t2=term();
					state._fsp--;

					 value.add(t2); 
					}
					break;

				default :
					break loop15;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "terms"



	// $ANTLR start "term"
	// TurtleOBDA.g:635:1: term returns [Term value] : ( function | variable | literal );
	public final Term term() throws RecognitionException {
		Term value = null;


		Function function31 =null;
		Variable variable32 =null;
		Term literal33 =null;

		try {
			// TurtleOBDA.g:636:3: ( function | variable | literal )
			int alt16=3;
			switch ( input.LA(1) ) {
			case PREFIXED_NAME:
			case STRING_WITH_BRACKET:
				{
				alt16=1;
				}
				break;
			case STRING_WITH_CURLY_BRACKET:
				{
				alt16=2;
				}
				break;
			case DECIMAL:
			case DECIMAL_NEGATIVE:
			case DECIMAL_POSITIVE:
			case DOUBLE:
			case DOUBLE_NEGATIVE:
			case DOUBLE_POSITIVE:
			case FALSE:
			case INTEGER:
			case INTEGER_NEGATIVE:
			case INTEGER_POSITIVE:
			case STRING_WITH_QUOTE_DOUBLE:
			case TRUE:
				{
				alt16=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 16, 0, input);
				throw nvae;
			}
			switch (alt16) {
				case 1 :
					// TurtleOBDA.g:636:5: function
					{
					pushFollow(FOLLOW_function_in_term709);
					function31=function();
					state._fsp--;

					 value = function31; 
					}
					break;
				case 2 :
					// TurtleOBDA.g:637:5: variable
					{
					pushFollow(FOLLOW_variable_in_term717);
					variable32=variable();
					state._fsp--;

					 value = variable32; 
					}
					break;
				case 3 :
					// TurtleOBDA.g:638:5: literal
					{
					pushFollow(FOLLOW_literal_in_term725);
					literal33=literal();
					state._fsp--;

					 value = literal33; 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "term"



	// $ANTLR start "literal"
	// TurtleOBDA.g:647:1: literal returns [Term value] : ( stringLiteral ( AT language )? | dataTypeString | numericLiteral | booleanLiteral );
	public final Term literal() throws RecognitionException {
		Term value = null;


		Term language34 =null;
		Term stringLiteral35 =null;
		Term dataTypeString36 =null;
		Term numericLiteral37 =null;
		Term booleanLiteral38 =null;

		try {
			// TurtleOBDA.g:648:3: ( stringLiteral ( AT language )? | dataTypeString | numericLiteral | booleanLiteral )
			int alt18=4;
			switch ( input.LA(1) ) {
			case STRING_WITH_QUOTE_DOUBLE:
				{
				int LA18_1 = input.LA(2);
				if ( (LA18_1==AT||LA18_1==COMMA||LA18_1==PERIOD||LA18_1==RPAREN||LA18_1==SEMI||LA18_1==WS) ) {
					alt18=1;
				}
				else if ( (LA18_1==REFERENCE) ) {
					alt18=2;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 18, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case DECIMAL:
			case DECIMAL_NEGATIVE:
			case DECIMAL_POSITIVE:
			case DOUBLE:
			case DOUBLE_NEGATIVE:
			case DOUBLE_POSITIVE:
			case INTEGER:
			case INTEGER_NEGATIVE:
			case INTEGER_POSITIVE:
				{
				alt18=3;
				}
				break;
			case FALSE:
			case TRUE:
				{
				alt18=4;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 18, 0, input);
				throw nvae;
			}
			switch (alt18) {
				case 1 :
					// TurtleOBDA.g:648:5: stringLiteral ( AT language )?
					{
					pushFollow(FOLLOW_stringLiteral_in_literal745);
					stringLiteral35=stringLiteral();
					state._fsp--;

					// TurtleOBDA.g:648:19: ( AT language )?
					int alt17=2;
					int LA17_0 = input.LA(1);
					if ( (LA17_0==AT) ) {
						alt17=1;
					}
					switch (alt17) {
						case 1 :
							// TurtleOBDA.g:648:20: AT language
							{
							match(input,AT,FOLLOW_AT_in_literal748); 
							pushFollow(FOLLOW_language_in_literal750);
							language34=language();
							state._fsp--;

							}
							break;

					}


					       Term lang = language34;
					       Term literal = stringLiteral35;
					       if (literal instanceof Function){
					          Function f = (Function)stringLiteral35;
					          if (lang != null){
					             value = dfac.getTypedTerm(f,lang);
					          }else{
					             value = dfac.getTypedTerm(f, COL_TYPE.LITERAL);
					          }       
					       }else{

					       //if variable we cannot assign a datatype yet
					       if (literal instanceof Variable)
					       {
					            value = dfac.getTypedTerm(literal, COL_TYPE.LITERAL);
					       }
					       else{
					          ValueConstant constant = (ValueConstant)stringLiteral35;
					          if (lang != null) {
						     value = dfac.getTypedTerm(constant, lang);
					          } else {
					      	     value = dfac.getTypedTerm(constant, COL_TYPE.LITERAL);
					          }
					       }
					       }
					    
					}
					break;
				case 2 :
					// TurtleOBDA.g:675:5: dataTypeString
					{
					pushFollow(FOLLOW_dataTypeString_in_literal760);
					dataTypeString36=dataTypeString();
					state._fsp--;

					 value = dataTypeString36; 
					}
					break;
				case 3 :
					// TurtleOBDA.g:676:5: numericLiteral
					{
					pushFollow(FOLLOW_numericLiteral_in_literal768);
					numericLiteral37=numericLiteral();
					state._fsp--;

					 value = numericLiteral37; 
					}
					break;
				case 4 :
					// TurtleOBDA.g:677:5: booleanLiteral
					{
					pushFollow(FOLLOW_booleanLiteral_in_literal776);
					booleanLiteral38=booleanLiteral();
					state._fsp--;

					 value = booleanLiteral38; 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "literal"



	// $ANTLR start "stringLiteral"
	// TurtleOBDA.g:680:1: stringLiteral returns [Term value] : STRING_WITH_QUOTE_DOUBLE ;
	public final Term stringLiteral() throws RecognitionException {
		Term value = null;


		Token STRING_WITH_QUOTE_DOUBLE39=null;

		try {
			// TurtleOBDA.g:681:3: ( STRING_WITH_QUOTE_DOUBLE )
			// TurtleOBDA.g:681:5: STRING_WITH_QUOTE_DOUBLE
			{
			STRING_WITH_QUOTE_DOUBLE39=(Token)match(input,STRING_WITH_QUOTE_DOUBLE,FOLLOW_STRING_WITH_QUOTE_DOUBLE_in_stringLiteral795); 

			      String str = (STRING_WITH_QUOTE_DOUBLE39!=null?STRING_WITH_QUOTE_DOUBLE39.getText():null);
			      if (str.contains("{")){
			      	value = getNestedConcat(str);
			      }else{
			      	value = dfac.getConstantLiteral(str.substring(1, str.length()-1), COL_TYPE.LITERAL); // without the double quotes
			      }
			    
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "stringLiteral"



	// $ANTLR start "dataTypeString"
	// TurtleOBDA.g:691:1: dataTypeString returns [Term value] : stringLiteral REFERENCE resource ;
	public final Term dataTypeString() throws RecognitionException {
		Term value = null;


		Term stringLiteral40 =null;
		Term resource41 =null;

		try {
			// TurtleOBDA.g:692:3: ( stringLiteral REFERENCE resource )
			// TurtleOBDA.g:692:6: stringLiteral REFERENCE resource
			{
			pushFollow(FOLLOW_stringLiteral_in_dataTypeString815);
			stringLiteral40=stringLiteral();
			state._fsp--;

			match(input,REFERENCE,FOLLOW_REFERENCE_in_dataTypeString817); 
			pushFollow(FOLLOW_resource_in_dataTypeString819);
			resource41=resource();
			state._fsp--;


			      Term stringValue = stringLiteral40;

			          if (resource41 instanceof Function){
			          	    String functionName = ( (ValueConstant) ((Function)resource41).getTerm(0) ).getValue();

			                    Predicate.COL_TYPE type = dtfac.getDatatype(functionName);
			                    if (type == null) {
			                      throw new RuntimeException("Unsupported datatype: " + functionName);
			                    }
			                    value = dfac.getTypedTerm(stringValue, type);
			                    }
			           else {
			          value = dfac.getTypedTerm(stringValue, COL_TYPE.LITERAL);
			          }

			  
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "dataTypeString"



	// $ANTLR start "numericLiteral"
	// TurtleOBDA.g:710:1: numericLiteral returns [Term value] : ( numericUnsigned | numericPositive | numericNegative );
	public final Term numericLiteral() throws RecognitionException {
		Term value = null;


		Term numericUnsigned42 =null;
		Term numericPositive43 =null;
		Term numericNegative44 =null;

		try {
			// TurtleOBDA.g:711:3: ( numericUnsigned | numericPositive | numericNegative )
			int alt19=3;
			switch ( input.LA(1) ) {
			case DECIMAL:
			case DOUBLE:
			case INTEGER:
				{
				alt19=1;
				}
				break;
			case DECIMAL_POSITIVE:
			case DOUBLE_POSITIVE:
			case INTEGER_POSITIVE:
				{
				alt19=2;
				}
				break;
			case DECIMAL_NEGATIVE:
			case DOUBLE_NEGATIVE:
			case INTEGER_NEGATIVE:
				{
				alt19=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 19, 0, input);
				throw nvae;
			}
			switch (alt19) {
				case 1 :
					// TurtleOBDA.g:711:5: numericUnsigned
					{
					pushFollow(FOLLOW_numericUnsigned_in_numericLiteral835);
					numericUnsigned42=numericUnsigned();
					state._fsp--;

					 value = numericUnsigned42; 
					}
					break;
				case 2 :
					// TurtleOBDA.g:712:5: numericPositive
					{
					pushFollow(FOLLOW_numericPositive_in_numericLiteral843);
					numericPositive43=numericPositive();
					state._fsp--;

					 value = numericPositive43; 
					}
					break;
				case 3 :
					// TurtleOBDA.g:713:5: numericNegative
					{
					pushFollow(FOLLOW_numericNegative_in_numericLiteral851);
					numericNegative44=numericNegative();
					state._fsp--;

					 value = numericNegative44; 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "numericLiteral"



	// $ANTLR start "relativeURI"
	// TurtleOBDA.g:718:1: relativeURI : STRING_URI ;
	public final void relativeURI() throws RecognitionException {
		try {
			// TurtleOBDA.g:719:3: ( STRING_URI )
			// TurtleOBDA.g:719:5: STRING_URI
			{
			match(input,STRING_URI,FOLLOW_STRING_URI_in_relativeURI869); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "relativeURI"


	public static class namespace_return extends ParserRuleReturnScope {
	};


	// $ANTLR start "namespace"
	// TurtleOBDA.g:722:1: namespace : NAMESPACE ;
	public final TurtleOBDAParser.namespace_return namespace() throws RecognitionException {
		TurtleOBDAParser.namespace_return retval = new TurtleOBDAParser.namespace_return();
		retval.start = input.LT(1);

		try {
			// TurtleOBDA.g:723:3: ( NAMESPACE )
			// TurtleOBDA.g:723:5: NAMESPACE
			{
			match(input,NAMESPACE,FOLLOW_NAMESPACE_in_namespace882); 
			}

			retval.stop = input.LT(-1);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "namespace"


	public static class defaultNamespace_return extends ParserRuleReturnScope {
	};


	// $ANTLR start "defaultNamespace"
	// TurtleOBDA.g:726:1: defaultNamespace : COLON ;
	public final TurtleOBDAParser.defaultNamespace_return defaultNamespace() throws RecognitionException {
		TurtleOBDAParser.defaultNamespace_return retval = new TurtleOBDAParser.defaultNamespace_return();
		retval.start = input.LT(1);

		try {
			// TurtleOBDA.g:727:3: ( COLON )
			// TurtleOBDA.g:727:5: COLON
			{
			match(input,COLON,FOLLOW_COLON_in_defaultNamespace897); 
			}

			retval.stop = input.LT(-1);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "defaultNamespace"



	// $ANTLR start "name"
	// TurtleOBDA.g:730:1: name : VARNAME ;
	public final void name() throws RecognitionException {
		try {
			// TurtleOBDA.g:731:3: ( VARNAME )
			// TurtleOBDA.g:731:5: VARNAME
			{
			match(input,VARNAME,FOLLOW_VARNAME_in_name910); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "name"


	public static class languageTag_return extends ParserRuleReturnScope {
	};


	// $ANTLR start "languageTag"
	// TurtleOBDA.g:734:1: languageTag : VARNAME ;
	public final TurtleOBDAParser.languageTag_return languageTag() throws RecognitionException {
		TurtleOBDAParser.languageTag_return retval = new TurtleOBDAParser.languageTag_return();
		retval.start = input.LT(1);

		try {
			// TurtleOBDA.g:735:3: ( VARNAME )
			// TurtleOBDA.g:735:5: VARNAME
			{
			match(input,VARNAME,FOLLOW_VARNAME_in_languageTag923); 
			}

			retval.stop = input.LT(-1);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "languageTag"



	// $ANTLR start "booleanLiteral"
	// TurtleOBDA.g:738:1: booleanLiteral returns [Term value] : ( TRUE | FALSE );
	public final Term booleanLiteral() throws RecognitionException {
		Term value = null;


		Token TRUE45=null;
		Token FALSE46=null;

		try {
			// TurtleOBDA.g:739:3: ( TRUE | FALSE )
			int alt20=2;
			int LA20_0 = input.LA(1);
			if ( (LA20_0==TRUE) ) {
				alt20=1;
			}
			else if ( (LA20_0==FALSE) ) {
				alt20=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 20, 0, input);
				throw nvae;
			}

			switch (alt20) {
				case 1 :
					// TurtleOBDA.g:739:5: TRUE
					{
					TRUE45=(Token)match(input,TRUE,FOLLOW_TRUE_in_booleanLiteral940); 

					  ValueConstant trueConstant = dfac.getConstantLiteral((TRUE45!=null?TRUE45.getText():null), COL_TYPE.LITERAL);
					  value = dfac.getTypedTerm(trueConstant, COL_TYPE.BOOLEAN); 
					}
					break;
				case 2 :
					// TurtleOBDA.g:742:5: FALSE
					{
					FALSE46=(Token)match(input,FALSE,FOLLOW_FALSE_in_booleanLiteral949); 

					  ValueConstant falseConstant = dfac.getConstantLiteral((FALSE46!=null?FALSE46.getText():null), COL_TYPE.LITERAL);
					  value = dfac.getTypedTerm(falseConstant, COL_TYPE.BOOLEAN);
					  
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "booleanLiteral"



	// $ANTLR start "numericUnsigned"
	// TurtleOBDA.g:748:1: numericUnsigned returns [Term value] : ( INTEGER | DOUBLE | DECIMAL );
	public final Term numericUnsigned() throws RecognitionException {
		Term value = null;


		Token INTEGER47=null;
		Token DOUBLE48=null;
		Token DECIMAL49=null;

		try {
			// TurtleOBDA.g:749:3: ( INTEGER | DOUBLE | DECIMAL )
			int alt21=3;
			switch ( input.LA(1) ) {
			case INTEGER:
				{
				alt21=1;
				}
				break;
			case DOUBLE:
				{
				alt21=2;
				}
				break;
			case DECIMAL:
				{
				alt21=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 21, 0, input);
				throw nvae;
			}
			switch (alt21) {
				case 1 :
					// TurtleOBDA.g:749:5: INTEGER
					{
					INTEGER47=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_numericUnsigned968); 

					  ValueConstant integerConstant = dfac.getConstantLiteral((INTEGER47!=null?INTEGER47.getText():null), COL_TYPE.LITERAL);
					  value = dfac.getTypedTerm(integerConstant, COL_TYPE.INTEGER);
					  
					}
					break;
				case 2 :
					// TurtleOBDA.g:753:5: DOUBLE
					{
					DOUBLE48=(Token)match(input,DOUBLE,FOLLOW_DOUBLE_in_numericUnsigned976); 

					  ValueConstant doubleConstant = dfac.getConstantLiteral((DOUBLE48!=null?DOUBLE48.getText():null), COL_TYPE.LITERAL);
					  value = dfac.getTypedTerm(doubleConstant, COL_TYPE.DOUBLE);
					  
					}
					break;
				case 3 :
					// TurtleOBDA.g:757:5: DECIMAL
					{
					DECIMAL49=(Token)match(input,DECIMAL,FOLLOW_DECIMAL_in_numericUnsigned985); 

					  ValueConstant decimalConstant = dfac.getConstantLiteral((DECIMAL49!=null?DECIMAL49.getText():null), COL_TYPE.LITERAL);
					  value = dfac.getTypedTerm(decimalConstant, COL_TYPE.DECIMAL);
					   
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "numericUnsigned"



	// $ANTLR start "numericPositive"
	// TurtleOBDA.g:763:1: numericPositive returns [Term value] : ( INTEGER_POSITIVE | DOUBLE_POSITIVE | DECIMAL_POSITIVE );
	public final Term numericPositive() throws RecognitionException {
		Term value = null;


		Token INTEGER_POSITIVE50=null;
		Token DOUBLE_POSITIVE51=null;
		Token DECIMAL_POSITIVE52=null;

		try {
			// TurtleOBDA.g:764:3: ( INTEGER_POSITIVE | DOUBLE_POSITIVE | DECIMAL_POSITIVE )
			int alt22=3;
			switch ( input.LA(1) ) {
			case INTEGER_POSITIVE:
				{
				alt22=1;
				}
				break;
			case DOUBLE_POSITIVE:
				{
				alt22=2;
				}
				break;
			case DECIMAL_POSITIVE:
				{
				alt22=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 22, 0, input);
				throw nvae;
			}
			switch (alt22) {
				case 1 :
					// TurtleOBDA.g:764:5: INTEGER_POSITIVE
					{
					INTEGER_POSITIVE50=(Token)match(input,INTEGER_POSITIVE,FOLLOW_INTEGER_POSITIVE_in_numericPositive1004); 

					   ValueConstant integerConstant = dfac.getConstantLiteral((INTEGER_POSITIVE50!=null?INTEGER_POSITIVE50.getText():null), COL_TYPE.LITERAL);
					   value = dfac.getTypedTerm(integerConstant, COL_TYPE.INTEGER);
					  
					}
					break;
				case 2 :
					// TurtleOBDA.g:768:5: DOUBLE_POSITIVE
					{
					DOUBLE_POSITIVE51=(Token)match(input,DOUBLE_POSITIVE,FOLLOW_DOUBLE_POSITIVE_in_numericPositive1012); 

					  ValueConstant doubleConstant = dfac.getConstantLiteral((DOUBLE_POSITIVE51!=null?DOUBLE_POSITIVE51.getText():null), COL_TYPE.LITERAL);
					  value = dfac.getTypedTerm(doubleConstant, COL_TYPE.DOUBLE);
					  
					}
					break;
				case 3 :
					// TurtleOBDA.g:772:5: DECIMAL_POSITIVE
					{
					DECIMAL_POSITIVE52=(Token)match(input,DECIMAL_POSITIVE,FOLLOW_DECIMAL_POSITIVE_in_numericPositive1021); 

					  ValueConstant decimalConstant = dfac.getConstantLiteral((DECIMAL_POSITIVE52!=null?DECIMAL_POSITIVE52.getText():null), COL_TYPE.LITERAL);
					  value = dfac.getTypedTerm(decimalConstant, COL_TYPE.DECIMAL);
					   
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "numericPositive"



	// $ANTLR start "numericNegative"
	// TurtleOBDA.g:778:1: numericNegative returns [Term value] : ( INTEGER_NEGATIVE | DOUBLE_NEGATIVE | DECIMAL_NEGATIVE );
	public final Term numericNegative() throws RecognitionException {
		Term value = null;


		Token INTEGER_NEGATIVE53=null;
		Token DOUBLE_NEGATIVE54=null;
		Token DECIMAL_NEGATIVE55=null;

		try {
			// TurtleOBDA.g:779:3: ( INTEGER_NEGATIVE | DOUBLE_NEGATIVE | DECIMAL_NEGATIVE )
			int alt23=3;
			switch ( input.LA(1) ) {
			case INTEGER_NEGATIVE:
				{
				alt23=1;
				}
				break;
			case DOUBLE_NEGATIVE:
				{
				alt23=2;
				}
				break;
			case DECIMAL_NEGATIVE:
				{
				alt23=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 23, 0, input);
				throw nvae;
			}
			switch (alt23) {
				case 1 :
					// TurtleOBDA.g:779:5: INTEGER_NEGATIVE
					{
					INTEGER_NEGATIVE53=(Token)match(input,INTEGER_NEGATIVE,FOLLOW_INTEGER_NEGATIVE_in_numericNegative1040); 

					  ValueConstant integerConstant = dfac.getConstantLiteral((INTEGER_NEGATIVE53!=null?INTEGER_NEGATIVE53.getText():null), COL_TYPE.LITERAL);
					  value = dfac.getTypedTerm(integerConstant, COL_TYPE.INTEGER);
					  
					}
					break;
				case 2 :
					// TurtleOBDA.g:783:5: DOUBLE_NEGATIVE
					{
					DOUBLE_NEGATIVE54=(Token)match(input,DOUBLE_NEGATIVE,FOLLOW_DOUBLE_NEGATIVE_in_numericNegative1048); 

					   ValueConstant doubleConstant = dfac.getConstantLiteral((DOUBLE_NEGATIVE54!=null?DOUBLE_NEGATIVE54.getText():null), COL_TYPE.LITERAL);
					   value = dfac.getTypedTerm(doubleConstant, COL_TYPE.DOUBLE);
					  
					}
					break;
				case 3 :
					// TurtleOBDA.g:787:5: DECIMAL_NEGATIVE
					{
					DECIMAL_NEGATIVE55=(Token)match(input,DECIMAL_NEGATIVE,FOLLOW_DECIMAL_NEGATIVE_in_numericNegative1057); 

					  ValueConstant decimalConstant = dfac.getConstantLiteral((DECIMAL_NEGATIVE55!=null?DECIMAL_NEGATIVE55.getText():null), COL_TYPE.LITERAL);
					  value = dfac.getTypedTerm(decimalConstant, COL_TYPE.DECIMAL);
					  
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "numericNegative"

	// Delegated rules



	public static final BitSet FOLLOW_directiveStatement_in_parse56 = new BitSet(new long[]{0x0080000000002480L,0x0000000000000030L});
	public static final BitSet FOLLOW_triplesStatement_in_parse65 = new BitSet(new long[]{0x0080000000002080L,0x0000000000000030L});
	public static final BitSet FOLLOW_triplesStatement_in_parse78 = new BitSet(new long[]{0x0080000000002080L,0x0000000000000030L});
	public static final BitSet FOLLOW_EOF_in_parse85 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_directive_in_directiveStatement98 = new BitSet(new long[]{0x0010000000000000L});
	public static final BitSet FOLLOW_PERIOD_in_directiveStatement100 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_triples_in_triplesStatement117 = new BitSet(new long[]{0x0010000000000000L,0x0000000000002000L});
	public static final BitSet FOLLOW_WS_in_triplesStatement119 = new BitSet(new long[]{0x0010000000000000L,0x0000000000002000L});
	public static final BitSet FOLLOW_PERIOD_in_triplesStatement122 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_base_in_directive137 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_prefixID_in_directive143 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_AT_in_base156 = new BitSet(new long[]{0x0000000000001000L});
	public static final BitSet FOLLOW_BASE_in_base158 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
	public static final BitSet FOLLOW_uriref_in_base160 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_AT_in_prefixID178 = new BitSet(new long[]{0x0040000000000000L});
	public static final BitSet FOLLOW_PREFIX_in_prefixID180 = new BitSet(new long[]{0x0000400000020000L});
	public static final BitSet FOLLOW_namespace_in_prefixID183 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
	public static final BitSet FOLLOW_defaultNamespace_in_prefixID189 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
	public static final BitSet FOLLOW_uriref_in_prefixID194 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_subject_in_triples213 = new BitSet(new long[]{0x0080000000000000L,0x0000000000004010L});
	public static final BitSet FOLLOW_predicateObjectList_in_triples217 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_verb_in_predicateObjectList243 = new BitSet(new long[]{0x008000E087382080L,0x00000000000002B0L});
	public static final BitSet FOLLOW_objectList_in_predicateObjectList249 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000002L});
	public static final BitSet FOLLOW_SEMI_in_predicateObjectList258 = new BitSet(new long[]{0x0080000000000000L,0x0000000000004010L});
	public static final BitSet FOLLOW_verb_in_predicateObjectList262 = new BitSet(new long[]{0x008000E087382080L,0x00000000000002B0L});
	public static final BitSet FOLLOW_objectList_in_predicateObjectList266 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000002L});
	public static final BitSet FOLLOW_predicate_in_verb290 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_78_in_verb298 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_object_in_objectList324 = new BitSet(new long[]{0x0000000000040002L});
	public static final BitSet FOLLOW_COMMA_in_objectList329 = new BitSet(new long[]{0x008000E087382080L,0x00000000000002B0L});
	public static final BitSet FOLLOW_object_in_objectList333 = new BitSet(new long[]{0x0000000000040002L});
	public static final BitSet FOLLOW_resource_in_subject355 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_variable_in_subject363 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_blank_in_subject371 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_resource_in_predicate392 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_resource_in_object411 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_literal_in_object419 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_typedLiteral_in_object428 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_variable_in_object436 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_blank_in_object444 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_uriref_in_resource465 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_qname_in_resource474 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_STRING_WITH_BRACKET_in_uriref499 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PREFIXED_NAME_in_qname518 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_BLANK_NODE_LABEL_in_blank537 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ANON_in_blank545 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_STRING_WITH_CURLY_BRACKET_in_variable564 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_resource_in_function585 = new BitSet(new long[]{0x0000040000000000L});
	public static final BitSet FOLLOW_LPAREN_in_function587 = new BitSet(new long[]{0x008000E087380000L,0x00000000000002B0L});
	public static final BitSet FOLLOW_terms_in_function589 = new BitSet(new long[]{0x2000000000000000L});
	public static final BitSet FOLLOW_RPAREN_in_function591 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_variable_in_typedLiteral610 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_AT_in_typedLiteral612 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001020L});
	public static final BitSet FOLLOW_language_in_typedLiteral614 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_variable_in_typedLiteral622 = new BitSet(new long[]{0x1000000000000000L});
	public static final BitSet FOLLOW_REFERENCE_in_typedLiteral624 = new BitSet(new long[]{0x0080000000000000L,0x0000000000000010L});
	public static final BitSet FOLLOW_resource_in_typedLiteral626 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_languageTag_in_language645 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_variable_in_language653 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_term_in_terms679 = new BitSet(new long[]{0x0000000000040002L});
	public static final BitSet FOLLOW_COMMA_in_terms684 = new BitSet(new long[]{0x008000E087380000L,0x00000000000002B0L});
	public static final BitSet FOLLOW_term_in_terms688 = new BitSet(new long[]{0x0000000000040002L});
	public static final BitSet FOLLOW_function_in_term709 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_variable_in_term717 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_literal_in_term725 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_stringLiteral_in_literal745 = new BitSet(new long[]{0x0000000000000402L});
	public static final BitSet FOLLOW_AT_in_literal748 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001020L});
	public static final BitSet FOLLOW_language_in_literal750 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_dataTypeString_in_literal760 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_numericLiteral_in_literal768 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_booleanLiteral_in_literal776 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_STRING_WITH_QUOTE_DOUBLE_in_stringLiteral795 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_stringLiteral_in_dataTypeString815 = new BitSet(new long[]{0x1000000000000000L});
	public static final BitSet FOLLOW_REFERENCE_in_dataTypeString817 = new BitSet(new long[]{0x0080000000000000L,0x0000000000000010L});
	public static final BitSet FOLLOW_resource_in_dataTypeString819 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_numericUnsigned_in_numericLiteral835 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_numericPositive_in_numericLiteral843 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_numericNegative_in_numericLiteral851 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_STRING_URI_in_relativeURI869 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NAMESPACE_in_namespace882 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_COLON_in_defaultNamespace897 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_VARNAME_in_name910 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_VARNAME_in_languageTag923 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_TRUE_in_booleanLiteral940 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_FALSE_in_booleanLiteral949 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_INTEGER_in_numericUnsigned968 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_DOUBLE_in_numericUnsigned976 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_DECIMAL_in_numericUnsigned985 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_INTEGER_POSITIVE_in_numericPositive1004 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_DOUBLE_POSITIVE_in_numericPositive1012 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_DECIMAL_POSITIVE_in_numericPositive1021 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_INTEGER_NEGATIVE_in_numericNegative1040 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_DOUBLE_NEGATIVE_in_numericNegative1048 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_DECIMAL_NEGATIVE_in_numericNegative1057 = new BitSet(new long[]{0x0000000000000002L});
}
