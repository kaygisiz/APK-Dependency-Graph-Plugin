package org.antlr;

/* ANTLR Translator Generator
 * Project led by Terence Parr at http://www.jGuru.com
 * Software rights: http://www.antlr.org/RIGHTS.html
 *
 * $Id: //depot/code/org.antlr/release/antlr-2.7.0/antlr/CharFormatter.java#1 $
 */

/** Interface used by BitSet to format elements of the set when
  * converting to string
  */
public interface CharFormatter {


	String escapeChar(int c, boolean forCharLiteral);
	String escapeString(String s);
	String literalChar(int c);
	String literalString(String s);
}
