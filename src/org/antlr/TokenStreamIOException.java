package org.antlr;

/* ANTLR Translator Generator
 * Project led by Terence Parr at http://www.jGuru.com
 * Software rights: http://www.antlr.org/RIGHTS.html
 *
 * $Id: //depot/code/org.antlr/release/antlr-2.7.0/antlr/TokenStreamIOException.java#1 $
 */

import java.io.IOException;

/**
 * Wraps an IOException in a TokenStreamException
 */
public class TokenStreamIOException extends TokenStreamException {
	public IOException io;
/**
 * TokenStreamIOException constructor comment.
 * @param s java.lang.String
 */
public TokenStreamIOException(IOException io) {
	super(io.getMessage());
	this.io = io;
}
}
