package org.antlr;

/* ANTLR Translator Generator
 * Project led by Terence Parr at http://www.jGuru.com
 * Software rights: http://www.antlr.org/RIGHTS.html
 *
 * $Id: //depot/code/org.antlr/release/antlr-2.7.0/antlr/GrammarSymbol.java#1 $
 */

/**A GrammarSymbol is a generic symbol that can be
 * added to the symbol table for a grammar.
 */
abstract class GrammarSymbol {
	protected String id;

	public GrammarSymbol() {}
	public GrammarSymbol(String s) { id = s; }
	public String getId() { return id; }
	public void setId(String s) { id = s; }
}
