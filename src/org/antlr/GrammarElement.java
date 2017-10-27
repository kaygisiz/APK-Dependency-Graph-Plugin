package org.antlr;

/* ANTLR Translator Generator
 * Project led by Terence Parr at http://www.jGuru.com
 * Software rights: http://www.antlr.org/RIGHTS.html
 *
 * $Id: //depot/code/org.antlr/release/antlr-2.7.0/antlr/GrammarElement.java#1 $
 */

/**A GrammarElement is a generic node in our
 * data structure that holds a grammar in memory.
 * This data structure can be used for static
 * analysis or for dynamic analysis (during parsing).
 * Every node must know which grammar owns it, how
 * to generate code, and how to do analysis.
 */
abstract class GrammarElement {
	public static final int AUTO_GEN_NONE = 1;
	public static final int AUTO_GEN_CARET = 2;
	public static final int AUTO_GEN_BANG = 3;

	/*
	 * Note that Java does static argument type matching to
	 * determine which function to execute on the receiver.
	 * Here, that implies that we cannot simply say
	 * grammar.generator.gen(this) in GrammarElement or
	 * only CodeGenerator.gen(GrammarElement ge) would
	 * ever be called.
	 */
	protected Grammar grammar;
	protected int line;


	public GrammarElement(Grammar g) {
		grammar = g;
	}
	public void generate() {
    }
	public int getLine() {
		return line;
	}
	public Lookahead look(int k) { return null; }
	public abstract String toString();
}
