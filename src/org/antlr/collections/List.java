package org.antlr.collections;

/* ANTLR Translator Generator
 * Project led by Terence Parr at http://www.jGuru.com
 * Software rights: http://www.antlr.org/RIGHTS.html
 *
 * $Id: //depot/code/org.antlr/release/antlr-2.7.0/antlr/collections/List.java#1 $
 */

import java.util.Enumeration;
import java.util.NoSuchElementException;

/**A simple List interface that describes operations
 * on a list.
 */
public interface List {
	void add(Object o); // can insert at head or append.
	void append(Object o);
	Object elementAt(int index) throws NoSuchElementException;
	Enumeration elements();
	boolean includes(Object o);
	int length();
}
