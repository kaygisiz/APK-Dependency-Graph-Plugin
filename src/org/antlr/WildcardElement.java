package org.antlr;

/* ANTLR Translator Generator
 * Project led by Terence Parr at http://www.jGuru.com
 * Software rights: http://www.antlr.org/RIGHTS.html
 *
 * $Id: //depot/code/org.antlr/release/antlr-2.7.0/antlr/WildcardElement.java#1 $
 */

class WildcardElement extends GrammarAtom {
	protected String label;

	public WildcardElement(Grammar g, Token t, int autoGenType) {
		super(g, t, autoGenType);
		line = t.getLine();
	}
	public void generate() {
		grammar.generator.gen(this);
	}
	public String getLabel() {
		return label;
	}
	public Lookahead look(int k) {
		return grammar.theLLkAnalyzer.look(k, this);
	}
	public void setLabel(String label_) { 
		label = label_; 
	}
	public String toString() {
		String s = " ";
		if ( label!=null ) s += label+":";
		return s + ".";
	}
}
