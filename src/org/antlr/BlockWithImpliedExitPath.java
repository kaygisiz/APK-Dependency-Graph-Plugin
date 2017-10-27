package org.antlr;

/* ANTLR Translator Generator
 * Project led by Terence Parr at http://www.jGuru.com
 * Software rights: http://www.antlr.org/RIGHTS.html
 *
 * $Id: //depot/code/org.antlr/release/antlr-2.7.0/antlr/BlockWithImpliedExitPath.java#1 $
 */

abstract class BlockWithImpliedExitPath extends AlternativeBlock {
	protected int exitLookaheadDepth;	// lookahead needed to handle optional path
	/** lookahead to bypass block; set
	 * by deterministic().  1..k of Lookahead
	 */
	protected Lookahead[] exitCache = new Lookahead[grammar.maxk+1];


	public BlockWithImpliedExitPath(Grammar g) {
		super(g);
	}
public BlockWithImpliedExitPath(Grammar g, int line) {
	super(g, line, false);
}
}
