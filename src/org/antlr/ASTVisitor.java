package org.antlr;

/* ANTLR Translator Generator
 * Project led by Terence Parr at http://www.jGuru.com
 * Software rights: http://www.antlr.org/RIGHTS.html
 *
 * $Id: //depot/code/org.antlr/release/antlr-2.7.0/antlr/ASTVisitor.java#1 $
 */

import org.antlr.collections.AST;

public interface ASTVisitor{
    void visit(AST node);
}
