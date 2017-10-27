package org.antlr;

/* ANTLR Translator Generator
 * Project led by Terence Parr at http://www.jGuru.com
 * Software rights: http://www.antlr.org/RIGHTS.html
 *
 * $Id: //depot/code/org.antlr/release/antlr-2.7.0/antlr/CommonToken.java#1 $
 */

public class CommonToken extends Token {
    // most tokens will want line and text information
    int line;
    String text = null;
    
    public CommonToken() {}

    public CommonToken(int t, String txt) {
	type = t;
	setText(txt);
    }

    public CommonToken(String s)	{ text = s; }

    public int  getLine()		{ return line; }

    public String getText()		{ return text; }

    public void setLine(int l)		{ line = l; }

    public void setText(String s)	{ text = s; }

    public String toString() {
	return "[\""+getText()+"\",<"+type+">,line="+line+"]";
    }
}
