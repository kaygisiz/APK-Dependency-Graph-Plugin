package org.antlr.collections.impl;

/* ANTLR Translator Generator
 * Project led by Terence Parr at http://www.jGuru.com
 * Software rights: http://www.antlr.org/RIGHTS.html
 *
 * $Id: //depot/code/org.antlr/release/antlr-2.7.0/antlr/collections/impl/IntRange.java#1 $
 */

public class IntRange {
	int begin, end;


	public IntRange(int begin, int end) {
		this.begin = begin;
		this.end = end;
	}
	public String toString() {
		return begin+".."+end;
	}
}
