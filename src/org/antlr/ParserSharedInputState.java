package org.antlr;

/* ANTLR Translator Generator
 * Project led by Terence Parr at http://www.jGuru.com
 * Software rights: http://www.antlr.org/RIGHTS.html
 *
 * $Id: //depot/code/org.antlr/release/antlr-2.7.0/antlr/ParserSharedInputState.java#1 $
 */

/** This object contains the data associated with an
 *  input stream of tokens.  Multiple parsers
 *  share a single ParserSharedInputState to parse
 *  the same stream of tokens.
 */
public class ParserSharedInputState {
    /** Where to get token objects */
    protected TokenBuffer input;

    /** Are we guessing (guessing>0)? */
    public int guessing = 0;

    /** What file (if known) caused the problem? */
    protected String filename;
}
