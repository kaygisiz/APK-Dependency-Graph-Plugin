package org.antlr;

/* ANTLR Translator Generator
 * Project led by Terence Parr at http://www.jGuru.com
 * Software rights: http://www.antlr.org/RIGHTS.html
 *
 * $Id: //depot/code/org.antlr/release/antlr-2.7.0/antlr/TokenManager.java#1 $
 */

import java.util.Hashtable;
import java.util.Enumeration;
import org.antlr.collections.impl.Vector;

/** Interface that describes the set of defined tokens */
interface TokenManager {
	Object clone();
	/** define a token symbol */
    void define(TokenSymbol ts);
	/** Get the name of the token manager */
    String getName();
	/** Get a token string by index */
    String getTokenStringAt(int idx);
	/** Get the TokenSymbol for a string */
    TokenSymbol getTokenSymbol(String sym);
	TokenSymbol getTokenSymbolAt(int idx);
	/** Get an enumerator over the symbol table */
    Enumeration getTokenSymbolElements();
	Enumeration getTokenSymbolKeys();
	/** Get the token vocabulary (read-only).
	 * @return A Vector of Strings indexed by token type */
    Vector getVocabulary();
	/** Is this token manager read-only? */
    boolean isReadOnly();
	void mapToTokenSymbol(String name, TokenSymbol sym);
	/** Get the highest token type in use */
    int maxTokenType();
	/** Get the next unused token type */
    int nextTokenType();
	void setName(String n);
	void setReadOnly(boolean ro);
	/** Is a token symbol defined? */
    boolean tokenDefined(String symbol);
}
