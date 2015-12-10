package linkedList;

public interface Queue<E> {
	
	void offer(E item);
	
	E poll();
	
	E peek();
	
	boolean isEmpty();
}
