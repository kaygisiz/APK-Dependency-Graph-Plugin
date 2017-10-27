package org.antlr.collections.impl;

/* ANTLR Translator Generator
 * Project led by Terence Parr at http://www.jGuru.com
 * Software rights: http://www.antlr.org/RIGHTS.html
 *
 * $Id: //depot/code/org.antlr/release/antlr-2.7.0/antlr/collections/impl/VectorEnumerator.java#1 $
 */

import java.util.Enumeration;
import java.util.NoSuchElementException;
import org.antlr.collections.Enumerator;

// based on java.lang.Vector; returns any null indices between non-null ones.
class VectorEnumerator implements Enumeration {
	Vector vector;
	int i;


	VectorEnumerator(Vector v) {
		vector = v;
		i = 0;
	}
	public boolean hasMoreElements() {
		synchronized (vector) {
			return i <= vector.lastElement;
		}
	}
	public Object nextElement() {
		synchronized (vector) {
			if (i <= vector.lastElement) {
				return vector.data[i++];
			}
			throw new NoSuchElementException("VectorEnumerator");
		}
	}
}
