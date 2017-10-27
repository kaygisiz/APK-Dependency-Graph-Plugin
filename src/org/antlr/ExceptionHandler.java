package org.antlr;

/* ANTLR Translator Generator
 * Project led by Terence Parr at http://www.jGuru.com
 * Software rights: http://www.antlr.org/RIGHTS.html
 *
 * $Id: //depot/code/org.antlr/release/antlr-2.7.0/antlr/ExceptionHandler.java#1 $
 */

class ExceptionHandler {
	// Type of the ANTLR exception class to catch and the variable decl
	protected Token exceptionTypeAndName;
	// The action to be executed when the exception is caught
	protected String action;


	public ExceptionHandler(Token exceptionTypeAndName_,
				String action_) {
	    exceptionTypeAndName = exceptionTypeAndName_;
	    action = action_;
	}
}
