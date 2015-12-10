package linkedList;
/*
 * Jack Proudfoot
 * November 29, 2015
 * 
 * An implementation of the LinkedList data structure. Implements the stack, queue, 
 * and iterable interfaces.
 */

import java.util.Iterator;


public class LinkedList<E> implements Stack<E>, Queue<E>, Iterable<E>{
	private ListNode<E> head;
	private ListNode<E> tail;
	private int size;
	
	public LinkedList() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}
	
	public LinkedList(ListNode<E> data) {
		this.head = data;
		this.tail = data;
		this.size = 1;
	}
	
	public LinkedList(LinkedList<E> original) {
		this.size = original.size();
		
		this.head = new ListNode<E>(original.head);
		
		for (ListNode<E> i = original.head; i != null; i = i.getNextPointer()) {
			add(i.getObject());
		}
		
		
	}
	
	/**
	 * Add an object to the end of the list.
	 * @param data Object to add
	 */
	public void add (E data) {
		if (size == 0) addFirst(data);
		else addLast(data);
	}
	
	/**
	 * Adds the object to the specified index in the linked list
	 * @param index
	 * @param data
	 * @return boolean Returns true if the add successful
	 */
	public boolean add (int index, E data) {
		if (index == 0) {
			addFirst(data);
		}
		else if (index == size) {
			addLast(data);
		}
		else {
			ListNode<E> newNode = new ListNode<E>(data);
			ListNode<E> holderNode = head;
			for (int i = 0; i < index; i++) {
				holderNode = head.getNextPointer();
			}
			newNode.setNextPointer(holderNode.getNextPointer());
			holderNode.setNextPointer(newNode);
			
			size++;
		}

		return true;
	}
	
	/**
	 * Adds the object to the head of the LinkedList
	 * @param data Object to add
	 */
	public void addFirst(E data) {
		ListNode<E> newNode = new ListNode<E>(data);
		newNode.setNextPointer(head);
		head = newNode;
			
		if (size == 0) {
			tail = head;
		}
		
		size++;
	}
	
	/**
	 * Adds the object to the tail of the LinkedList
	 * @param data object to add
	 */
	public void addLast (E data) {
		ListNode<E> newNode = new ListNode<E>(data);
		
		if (size > 0) tail.setNextPointer(newNode);	
		tail = newNode;
		
		size++;
	}
	
	/**
	 * Removes the ListNode at the head pointer
	 * @return E object encapsulated in first ListNode
	 */
	public E removeFirst () {
		ListNode<E> nodeToBeRemoved;
		nodeToBeRemoved = head;
		head = nodeToBeRemoved.getNextPointer();
		size--;
		return nodeToBeRemoved.getObject();
	}
	
	/**
	 * Removes the ListNode at the tail pointer
	 * @return object encapsulated in the listnode
	 */
	public E removeLast () {
		return remove(size-1);
	}
	
	/**
	 * Removes the first index of the object in the LinkedList
	 * @param data Object to be removed
	 * @return boolean true if the remove was successful
	 */
	public boolean remove (E data) {
		
		if (head == null) throw new IndexOutOfBoundsException("There are no nodes in this LinkedList. Cannot remove object: " + data);
		
		int index = indexOf(data);
		
		if (index == -1) { //Returns true if the object is not contained in the LinkedList
			return true;
		}
		else {
			if (remove(index) != null) return true;
		}
		
		
		return false;
	}
	
	/**
	 * Removes the object at the specified index.
	 * @param index index at which to remove the object
	 * @return data contained at the index.
	 */
	public E remove (int index) {
		if (size == 0) throw new NullPointerException("There are no objects contained within the listnode to be removed");
		else if (index > size) throw new IndexOutOfBoundsException("You cannot remove the object contained at index " + index +" because the largest index is" + (size-1) + ".");
		
		else if (index == 0) {
			return removeFirst();
		}
		else {
			ListNode<E> nodeToBeRemoved;
			ListNode<E> tempNode = getNode(index-1);
			nodeToBeRemoved = tempNode.getNextPointer();
			tempNode.setNextPointer(nodeToBeRemoved.getNextPointer());
			
			if (index == size) {
				tail = getNode(size-1);
			}
			size--;
			
			return nodeToBeRemoved.getObject();
		}
	}
	
	/**
	 * Clears the LinkedList
	 */
	public void clear () {
		head = null;
		tail = null;
		size = 0;
	}
	
	/**
	 * Gets the object contained at the index within the LinkedList
	 * @param index index at which to get the object
	 * @return object encapsulated in the list node at the index
	 */
	public E get(int index) {
		if (index > size) throw new IndexOutOfBoundsException("You cannot get the object contained at index " + index +" because the largest index is" + (size-1) + ".");
		else return getNode(index).getObject();
	}
	
	/**
	 * Sets the ListNode at the index to be data 
	 * @param index which ListNode to set
	 * @param data object which to set
	 * @return E object previously contained in the ListNode at the index
	 */
	public E set (int index, E data) {
		if (index > size) throw new IndexOutOfBoundsException("You cannot set the object contained at index " + index +" because the largest index is" + (size-1) + ".");
		else {
		
			ListNode<E> tempNode = head;
		
			for (int i = 0; i < index; i++) {
				tempNode = tempNode.getNextPointer();
			}
		
			E tempData = tempNode.getObject();
		
			tempNode.setObject(data);
		
			return tempData;
		}
	}
	
	/**
	 * Returns the ListNode contained at the index
	 * @param index which ListNode to get
	 * @return ListNode<E> ListNode contained at the index
	 */
	private ListNode<E> getNode (int index) {
		if (index > size) throw new IndexOutOfBoundsException("You cannot get the ListNode contained at index " + index +" because the largest index is" + (size-1) + ".");
		
		ListNode<E> currNode = head;
		
		for (int x = 0; x < index; x++) {
			currNode = currNode.getNextPointer();
		}
		
		return currNode;
	}

	/**
	 * Returns whether or not the LinkedList contains the data
	 * @param data object to check
	 * @return boolean returns true if data is contained in the LinkedList
	 */
	public boolean contains (E data) {
		if (indexOf(data) != -1) return true;
		else return false;
	}
	
	/**
	 * Returns the index of the first instance of the object
	 * @param data object to retrieve first instance
	 * @return int index
	 */
	public int indexOf (E data) {
		int index = 0;
		for (ListNode<E> i= head; i != null; i = i.getNextPointer()) {
			if (i.getObject().equals(data)) return index; 
			else index++;
		}
		
		return -1;
	}
	
	/**
	 * Returns the Number of ListNodes contained in the LinkedList
	 * @return int Number of ListNodes
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * Adds the Object item to the stack.
	 * @param Object data to add to stack
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void push(Object item) {
		add(0, (E) item);
		
	}

	/**
	 * Removes the next object contained in the stack
	 * @return E next object
	 */
	@Override
	public E pop() {
		return removeFirst();
	}
	
	/**
	 * Returns the next object contained in the stack
	 * @return E next object
	 */
	@Override
	public E peek() {
		return head.getObject();
	}
	
	/**
	 * Adds the object to the queue
	 * @param Object item to be added to the queue
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void offer(Object item) {
		addLast((E) item);		
	}

	/**
	 * Removes the next object in the queue
	 * @return E next object
	 */
	@Override
	public E poll() {
		return removeLast();
	}

	/**
	 * Returns true if the LinkedList is empty
	 * @return boolean
	 */
	@Override
	public boolean isEmpty() {
		if (size > 0) return false;
		else return true;
	}
	
	/**
	 * Returns the String representation of the LinkedList
	 * @return String string representation of the LinkedList
	 */
	public String toString () {
		if (isEmpty()) return "[]";
		
		String str = "[";
		
		for (ListNode<E> i = head; i != null; i = i.getNextPointer()) {
			str += i + ", ";
		}
		
		str = str.substring(0, (str.length()-2)) + "]";
		
		return str;
		
	}

	/**
	 * Returns a new LinkedListIterator
	 * @return Iterator<E> new LinkedListIterator;
	 */
	@Override
	public Iterator<E> iterator() {
		return new LinkedListIterator<E>(head);
	}
	
}
