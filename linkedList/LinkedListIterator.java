package linkedList;
/*
 * Jack Proudfoot
 * November 29, 2015
 * 
 * An iterator for my LinkedList class. Contains all of the necessary methods for the Iterator
 * interface.
 */

import java.util.Iterator;
import java.util.NoSuchElementException;


public class LinkedListIterator<E> implements Iterator<E>{
	private ListNode<E> listNode;
	
	public LinkedListIterator (ListNode<E> node) {
		listNode = node;
	}
	
	/**
	 * Returns true if there is another item contained in the LinkedList
	 * @return boolean
	 */
	@Override
	public boolean hasNext() {
		if (listNode != null) return true;
		else return false;
	}
	
	/**
	 * Returns the next item contained in the LinkedList
	 * @return E next item in LinkedList
	 */
	@Override
	public E next() {
		if (hasNext()) {
			E data = listNode.getObject();
			listNode = listNode.getNextPointer();
			return data;
		}
		else {
			throw new NoSuchElementException("You are trying to iterate over the next item in the LinkedList however there is not an element there.");
		}
		
	}

}
