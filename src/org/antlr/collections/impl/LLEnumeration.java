package org.antlr.collections.impl;

/* ANTLR Translator Generator
 * Project led by Terence Parr at http://www.jGuru.com
 * Software rights: http://www.antlr.org/RIGHTS.html
 *
 * $Id: //depot/code/org.antlr/release/antlr-2.7.0/antlr/collections/impl/LLEnumeration.java#1 $
 */

import org.antlr.collections.List;
import org.antlr.collections.Stack;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import org.antlr.collections.impl.LLCell;

/**An enumeration of a LList.  Maintains a cursor through the list.
 * bad things would happen if the list changed via another thread
 * while we were walking this list.
 */
final class LLEnumeration implements Enumeration {
	LLCell cursor;
	LList list;


	/**Create an enumeration attached to a LList*/
	public LLEnumeration(LList l) {list = l; cursor=list.head;}
	/** Return true/false depending on whether there are more
	 * elements to enumerate.
	 */
	public boolean hasMoreElements() {
        return cursor != null;
	}
	/**Get the next element in the enumeration.  Destructive in that
	 * the returned element is removed from the enumeration.  This
	 * does not affect the list itself.
	 * @return the next object in the enumeration.
	 */
	public Object nextElement() {
		if ( !hasMoreElements() ) throw new NoSuchElementException();
		LLCell p = cursor;
		cursor = cursor.next;
		return p.data;
	}
}
