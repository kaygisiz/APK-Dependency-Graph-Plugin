package org.antlr;

/* ANTLR Translator Generator
 * Project led by Terence Parr at http://www.jGuru.com
 * Software rights: http://www.antlr.org/RIGHTS.html
 *
 * $Id: //depot/code/org.antlr/release/antlr-2.7.0/antlr/ASTNULLType.java#1 $
 */

import org.antlr.collections.AST;
import org.antlr.collections.ASTEnumeration;
import org.antlr.Token;

/** There is only one instance of this class **/
public class ASTNULLType implements AST {
    public void addChild(AST c) {
    }
    public boolean equals(AST t) {return false;}
    public boolean equalsList(AST t) {return false;}
    public boolean equalsListPartial(AST t) {return false;}
    public boolean equalsTree(AST t) {return false;}
    public boolean equalsTreePartial(AST t) {return false;}
    public ASTEnumeration findAll(AST tree) {return null;}
    public ASTEnumeration findAllPartial(AST subtree) {
	return null;
    }
    public AST getFirstChild() { return this; }
    public AST getNextSibling() { return this; }
    public String getText() { return "<ASTNULL>"; }
    public int getType() { return Token.NULL_TREE_LOOKAHEAD; }
    public void initialize(int t, String txt) {}
    public void initialize(AST t) {}
    public void initialize(Token t) {}
    public void setFirstChild(AST c) {
    }
    public void setNextSibling(AST n) {
    }
    public void setText(String text) {
    }
    public void setType(int ttype) {
    }
    public String toString() {return getText();}
    public String toStringList() {return getText();}
    public String toStringTree() {return getText();}
}
