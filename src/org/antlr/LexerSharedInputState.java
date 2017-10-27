package org.antlr;

/* ANTLR Translator Generator
 * Project led by Terence Parr at http://www.jGuru.com
 * Software rights: http://www.antlr.org/RIGHTS.html
 *
 * $Id: //depot/code/org.antlr/release/antlr-2.7.0/antlr/LexerSharedInputState.java#1 $
 */

import java.io.Reader;
import java.io.InputStream;

/** This object contains the data associated with an
 *  input stream of characters.  Multiple lexers
 *  share a single LexerSharedInputState to lex
 *  the same input stream.
 */
public class LexerSharedInputState {
    protected int column;
    protected int line=1;
    protected InputBuffer input;

    /** What file (if known) caused the problem? */
    protected String filename;

    public int guessing = 0;

    public LexerSharedInputState(InputBuffer inbuf) {
	input = inbuf;
    }

    public LexerSharedInputState(InputStream in) {
	this(new ByteBuffer(in));
    }

    public LexerSharedInputState(Reader in) {
	this(new CharBuffer(in));
    }
}
