package org.antlr.collections.impl;

/* ANTLR Translator Generator
 * Project led by Terence Parr at http://www.jGuru.com
 * Software rights: http://www.antlr.org/RIGHTS.html
 *
 * $Id: //depot/code/org.antlr/release/antlr-2.7.0/antlr/collections/impl/ASTEnumerator.java#1 $
 */

import org.antlr.collections.impl.Vector;
import org.antlr.collections.ASTEnumeration;
import org.antlr.collections.AST;
import java.util.NoSuchElementException;

public class ASTEnumerator implements org.antlr.collections.ASTEnumeration {
	/** The list of root nodes for subtrees that match */
	VectorEnumerator nodes;
	int i = 0;


public ASTEnumerator(Vector v) {
		nodes = new VectorEnumerator(v);
}
public boolean hasMoreNodes() {
	synchronized (nodes) {
		return i <= nodes.vector.lastElement;
	}
}
public org.antlr.collections.AST nextNode() {
	synchronized (nodes) {
		if (i <= nodes.vector.lastElement) {
			return (AST)nodes.vector.data[i++];
		}
		throw new NoSuchElementException("ASTEnumerator");
	}
}
}
