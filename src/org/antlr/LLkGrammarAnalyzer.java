package org.antlr;

/* ANTLR Translator Generator
 * Project led by Terence Parr at http://www.jGuru.com
 * Software rights: http://www.antlr.org/RIGHTS.html
 *
 * $Id: //depot/code/org.antlr/release/antlr-2.7.0/antlr/LLkGrammarAnalyzer.java#1 $
 */

public interface LLkGrammarAnalyzer extends GrammarAnalyzer {


	boolean deterministic(AlternativeBlock blk);
	boolean deterministic(OneOrMoreBlock blk);
	boolean deterministic(ZeroOrMoreBlock blk);
	Lookahead FOLLOW(int k, RuleEndElement end);
	Lookahead look(int k, ActionElement action);
	Lookahead look(int k, AlternativeBlock blk);
	Lookahead look(int k, BlockEndElement end);
	Lookahead look(int k, CharLiteralElement atom);
	Lookahead look(int k, CharRangeElement end);
	Lookahead look(int k, GrammarAtom atom);
	Lookahead look(int k, OneOrMoreBlock blk);
	Lookahead look(int k, RuleBlock blk);
	Lookahead look(int k, RuleEndElement end);
	Lookahead look(int k, RuleRefElement rr);
	Lookahead look(int k, StringLiteralElement atom);
	Lookahead look(int k, SynPredBlock blk);
	Lookahead look(int k, TokenRangeElement end);
	Lookahead look(int k, TreeElement end);
	Lookahead look(int k, WildcardElement wc);
	Lookahead look(int k, ZeroOrMoreBlock blk);
	Lookahead look(int k, String rule);
	void setGrammar(Grammar g);
	boolean subruleCanBeInverted(AlternativeBlock blk, boolean forLexer);
}
