package org.antlr;

/* ANTLR Translator Generator
 * Project led by Terence Parr at http://www.jGuru.com
 * Software rights: http://www.antlr.org/RIGHTS.html
 *
 * $Id: //depot/code/org.antlr/release/antlr-2.7.0/antlr/ActionElement.java#1 $
 */

class ActionElement extends AlternativeElement {
	protected String actionText;
	protected boolean isSemPred = false;


	public ActionElement(Grammar g, Token t) {
		super(g);
		actionText = t.getText();
		line = t.getLine();
	}
	public void generate() {
		grammar.generator.gen(this);
	}
	public Lookahead look(int k) {
		return grammar.theLLkAnalyzer.look(k, this);
	}
	public String toString() {
		return " "+actionText + (isSemPred?"?":"");
	}
}
