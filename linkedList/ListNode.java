package linkedList;
/*
 * Jack Proudfoot
 * November 29, 2015
 * 
 * An object used to encapsulate data for storage in a LinkedList.
 */

public class ListNode <E>{
	private E object;
	private ListNode<E> next;
	
	ListNode() {
		this.object = null;
		this.next = null;
	}
	
	ListNode(E object) {
		this.object = object;
		next = null;
	}
	
	ListNode(E object, ListNode<E> pointer) {
		this.object = object;
		this.next = pointer;
	}
	
	ListNode(ListNode<E> node) {
		this.object = node.getObject();
		this.next = null;
	}
	
	public String toString () {
		return object.toString();
	}
	
	public void setNextPointer(ListNode<E> pointer) {
		next = pointer;
	}
	
	public ListNode<E> getNextPointer() {
		return this.next;
	}
	
	public void setObject(E data) {
		object = data;
	}
	
	public E getObject() {
		return this.object;
	}
}
