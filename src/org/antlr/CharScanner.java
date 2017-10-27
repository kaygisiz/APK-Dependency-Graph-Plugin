package org.antlr;

/* ANTLR Translator Generator
 * Project led by Terence Parr at http://www.jGuru.com
 * Software rights: http://www.antlr.org/RIGHTS.html
 *
 * $Id: //depot/code/org.antlr/release/antlr-2.7.0/antlr/CharScanner.java#1 $
 */

import java.util.Hashtable;
import org.antlr.collections.impl.BitSet;
import java.io.IOException;
public abstract class CharScanner implements TokenStream {
    static final char NO_CHAR = 0;
    public static final char EOF_CHAR = (char) - 1;
    protected ANTLRStringBuffer text; // text of current token

    protected boolean saveConsumedInput = true; // does consume() save characters?
    protected Class tokenObjectClass; // what kind of tokens to create?
    protected boolean caseSensitive = true;
    protected boolean caseSensitiveLiterals = true;
    protected Hashtable literals; // set by subclass

    protected Token _returnToken = null; // used to return tokens w/o using return val.

    // Hash string used so we don't new one every time to check literals table
    protected ANTLRHashString hashString;

    protected LexerSharedInputState inputState;

    /** Used during filter mode to indicate that path is desired.
     *  A subsequent scan error will report an error as usual if
     *  acceptPath=true;
     */
    protected boolean commitToPath = false;

    public CharScanner() {
	text = new ANTLRStringBuffer();
	hashString = new ANTLRHashString(this);
	setTokenObjectClass("antlr.CommonToken");
    }

    public CharScanner(InputBuffer cb) { // SAS: use generic buffer
	this();
	inputState = new LexerSharedInputState(cb);
    }

    public CharScanner(LexerSharedInputState sharedState) {
	this();
	inputState = sharedState;
    }

    public void append(char c) {
	if ( saveConsumedInput ) {
	    text.append(c);
	}	
    }

    public void append(String s) {
	if ( saveConsumedInput ) {
	    text.append(s);
	}	
    }

    public void commit() {
	inputState.input.commit();
    }

    public void consume() throws CharStreamException {
	if (inputState.guessing == 0) {
	    if (caseSensitive) {
		append(LA(1));
	    } else {
		// use input.LA(), not LA(), to get original case
		// CharScanner.LA() would toLower it.
		append(inputState.input.LA(1));
	    }
	}
	inputState.input.consume();
    }

    /** Consume chars until one matches the given char */
    public void consumeUntil(int c) throws CharStreamException {
	while (LA(1) != EOF_CHAR && LA(1) != c)
	    {
		consume();
	    }
    }

    /** Consume chars until one matches the given set */
    public void consumeUntil(BitSet set) throws CharStreamException {
	while (LA(1) != EOF_CHAR && !set.member(LA(1))) {
	    consume();
	}
    }
    public boolean getCaseSensitive() { return caseSensitive; }
    public final boolean getCaseSensitiveLiterals() { return caseSensitiveLiterals; }
    public int getColumn() {
	return inputState.column;
    }
    public boolean getCommitToPath() { return commitToPath; }
    public String getFilename() {return inputState.filename;}
    public InputBuffer getInputBuffer() {
	return inputState.input;
    }
    public LexerSharedInputState getInputState() {
	return inputState;
    }
    public void setInputState(LexerSharedInputState state) {
	inputState = state;
    }
    public int getLine() { return inputState.line; }
    // return a copy of the current text buffer
    public String getText() {
	return text.toString();
    }
    public Token getTokenObject() {
	return _returnToken;
    }
    public char LA(int i) throws CharStreamException {
	if (caseSensitive) {
	    return inputState.input.LA(i);
	} else {
	    return toLower(inputState.input.LA(i));
	}
    }
    protected Token makeToken(int t) {
	try {
	    Token tok = (Token)tokenObjectClass.newInstance();
	    tok.setType(t);
	    // tok.setText(getText()); done in generated lexer now
	    tok.setLine(inputState.line);
	    return tok;
	}
	catch (InstantiationException ie) {
	    panic("can't instantiate token: "+tokenObjectClass);
	}
	catch (IllegalAccessException iae) {
	    panic("Token class is not accessible"+tokenObjectClass);
	}
	return Token.badToken;
    }
    public int mark() {
	return inputState.input.mark();
    }
    public void match(char c) throws MismatchedCharException, CharStreamException {
	if ( LA(1) != c ) {
	    throw new MismatchedCharException(LA(1), c, false, this);
	}
	consume();
    }
    public void match(BitSet b) throws MismatchedCharException, CharStreamException {
	if ( !b.member(LA(1)) ) {
	    throw new MismatchedCharException(LA(1), b, false, this);
	} else {
	    consume();
	}
    }
    public void match(String s) throws MismatchedCharException, CharStreamException {
	int len = s.length();
	for (int i=0; i<len; i++) {
	    if ( LA(1) != s.charAt(i) ) {
		throw new MismatchedCharException(LA(1), s.charAt(i), false, this);
	    }
	    consume();
	}
    }
    public void matchNot(char c) throws MismatchedCharException, CharStreamException {
	if ( LA(1) == c ) {
	    throw new MismatchedCharException(LA(1), c, true, this);
	}
	consume();
    }
    public void matchRange(char c1, char c2) throws MismatchedCharException, CharStreamException {
	if ( LA(1) < c1 || LA(1) > c2 ) throw new MismatchedCharException(LA(1), c1, c2, false, this);
	consume();
    }
    public void newline() { inputState.line++; }
    public void panic() {
	System.err.println("CharScanner: panic");
	System.exit(1);
    }
    public void panic(String s) {
	System.err.println("CharScanner; panic: "+s);
	System.exit(1);
    }

    /** Parser error-reporting function can be overridden in subclass */
    public void reportError(RecognitionException ex) {
	System.err.println(ex);
    }

    /** Parser error-reporting function can be overridden in subclass */
    public void reportError(String s) {
	if ( getFilename()==null ) {
	    System.err.println("error: " + s);
	}
	else {
	    System.err.println(getFilename()+": error: " + s);
	}
    }

    /** Parser warning-reporting function can be overridden in subclass */
    public void reportWarning(String s) {
	if ( getFilename()==null ) {
	    System.err.println("warning: "+s);
	}
	else {
	    System.err.println(getFilename()+": warning: " + s);
	}
    }

    public void resetText() {
	text.setLength(0);
    }

    public void rewind(int pos) {
	inputState.input.rewind(pos);
    }

    public void setCaseSensitive(boolean t) { caseSensitive = t; }

    public void setCommitToPath(boolean commit) { commitToPath = commit; }

    public void setFilename(String f) {inputState.filename=f;}

    public void setLine(int line) { inputState.line = line; }

    public void setText(String s) {
	resetText();
	text.append(s);
    }

    public void setTokenObjectClass(String cl) {
	try {
	    tokenObjectClass = Class.forName(cl);
	}
	catch (ClassNotFoundException ce) {
	    panic("ClassNotFoundException: "+cl);
	}
    }

    // Test the token text against the literals table
    // Override this method to perform a different literals test
    public int testLiteralsTable(int ttype) {
	hashString.setBuffer(text.getBuffer(), text.length());
	Integer literalsIndex = (Integer)literals.get(hashString);
	if (literalsIndex != null) {
	    ttype = literalsIndex.intValue();
	}
	return ttype;
    }

    /** Test the text passed in against the literals table
     * Override this method to perform a different literals test
     * This is used primarily when you want to test a portion of
     * a token.
     */
    public int testLiteralsTable(String text, int ttype) {
	ANTLRHashString s = new ANTLRHashString(text, this);
	Integer literalsIndex = (Integer)literals.get(s);
	if (literalsIndex != null) {
	    ttype = literalsIndex.intValue();
	}
	return ttype;
    }

    // Override this method to get more specific case handling
    public char toLower(char c) {
	return Character.toLowerCase(c);
    }

    public void traceIn(String rname) throws CharStreamException {
	System.out.println("enter lexer "+rname+"; c==" + LA(1));
    }

    public void traceOut(String rname) throws CharStreamException {
	System.out.println("exit lexer "+rname+"; c==" + LA(1));
    }

    /** This method is called by YourLexer.nextToken() when the lexer has
     *  hit EOF condition.  EOF is NOT a character.
     *  This method is not called if EOF is reached during
     *  syntactic predicate evaluation or during evaluation
     *  of normal lexical rules, which presumably would be
     *  an IOException.  This traps the "normal" EOF condition.
     *
     *  uponEOF() is called after the complete evaluation of
     *  the previous token and only if your parser asks
     *  for another token beyond that last non-EOF token.
     *
     *  You might want to throw token or char stream exceptions
     *  like: "Heh, premature eof" or a retry stream exception
     *  ("I found the end of this file, go back to referencing file").
     */
    public void uponEOF() throws TokenStreamException, CharStreamException {
    }
}
