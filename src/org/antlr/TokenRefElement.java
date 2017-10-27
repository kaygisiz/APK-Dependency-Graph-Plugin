package org.antlr;

/* ANTLR Translator Generator
 * Project led by Terence Parr at http://www.jGuru.com
 * Software rights: http://www.antlr.org/RIGHTS.html
 *
 * $Id: //depot/code/org.antlr/release/antlr-2.7.0/antlr/TokenRefElement.java#1 $
 */

class TokenRefElement extends GrammarAtom {

	public TokenRefElement(Grammar g,
						   Token t,
						   boolean inverted,
						   int autoGenType)
	{
		super(g, t, autoGenType);
		not = inverted;
		TokenSymbol ts = grammar.tokenManager.getTokenSymbol(atomText);
		if (ts == null) {
			g.tool.error("Undefined token symbol: " +
						 atomText, grammar.getFilename(), t.getLine());
		} else {
			tokenType = ts.getTokenType();
			// set the AST node type to whatever was set in tokens {...}
			// section (if anything);
			// Lafter, after this is created, the element option can set this.
			setASTNodeType(ts.getASTNodeType());
		}
		line = t.getLine();
	}

	public void generate() {
		grammar.generator.gen(this);
	}

	public Lookahead look(int k) {
		return grammar.theLLkAnalyzer.look(k, this);
	}
}
