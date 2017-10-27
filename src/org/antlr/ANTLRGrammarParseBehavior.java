package org.antlr;

/* ANTLR Translator Generator
 * Project led by Terence Parr at http://www.jGuru.com
 * Software rights: http://www.antlr.org/RIGHTS.html
 *
 * $Id: //depot/code/org.antlr/release/antlr-2.7.0/antlr/ANTLRGrammarParseBehavior.java#1 $
 */

import org.antlr.collections.impl.BitSet;

public interface ANTLRGrammarParseBehavior {
	void abortGrammar();
	void beginAlt(boolean doAST_);
	void beginChildList();
	// Exception handling
	void beginExceptionGroup();
	void beginExceptionSpec(Token label);
	void beginSubRule(Token label, int line, boolean not);
	// Trees
	void beginTree(int line)  throws SemanticException ;
	void defineRuleName(Token r, String access, boolean ruleAST, String docComment) throws SemanticException;
	void defineToken(Token tokname, Token tokliteral);
	void endAlt();
	void endChildList();
	void endExceptionGroup();
	void endExceptionSpec();
	void endGrammar();
	void endOptions();
	void endRule(String r);
	void endSubRule();
	void endTree();
	void hasError();
	void noASTSubRule();
	void oneOrMoreSubRule();
	void optionalSubRule();
	void refAction(Token action);
	void refArgAction(Token action);
	void refCharLiteral(Token lit, Token label, boolean inverted, int autoGenType, boolean lastInRule);
	void refCharRange(Token t1, Token t2, Token label, int autoGenType, boolean lastInRule);
	void refElementOption(Token option, Token value);
	void refTokensSpecElementOption(Token tok, Token option, Token value);
	void refExceptionHandler(Token exTypeAndName, String action);
	void refHeaderAction(Token name, Token act);
	void refInitAction(Token action);
	void refMemberAction(Token act);
	void refPreambleAction(Token act);
	void refReturnAction(Token returnAction);
	void refRule(Token idAssign, Token r, Token label, Token arg, int autoGenType);
	void refSemPred(Token pred);
	void refStringLiteral(Token lit, Token label, int autoGenType, boolean lastInRule);
	void refToken(Token assignId, Token t, Token label, Token args,
				  boolean inverted, int autoGenType, boolean lastInRule);
	void refTokenRange(Token t1, Token t2, Token label, int autoGenType, boolean lastInRule);
	// Tree specifiers
	void refTreeSpecifier(Token treeSpec);
	void refWildcard(Token t, Token label, int autoGenType);
	void setArgOfRuleRef(Token argaction);
	void setCharVocabulary(BitSet b);
	// Options
	void setFileOption(Token key, Token value, String filename);
	void setGrammarOption(Token key, Token value);
	void setRuleOption(Token key, Token value);
	void setSubruleOption(Token key, Token value);
	void startLexer(String file, Token name, String superClass, String doc);
	// Flow control for grammars
	void startParser(String file, Token name, String superClass, String doc);
	void startTreeWalker(String file, Token name, String superClass, String doc);
	void synPred();
	void zeroOrMoreSubRule();
}
