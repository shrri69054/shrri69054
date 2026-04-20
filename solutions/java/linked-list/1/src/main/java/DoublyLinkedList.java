class DoublyLinkedList<T> {

	private static class Node<T> {
		Node<T> previous;
		T value;
		Node<T> next;
	
		Node (T value) {
			this.previous = null;
			this.value = value;
			this.next = null;
		}
	}

	Node<T> head = null;
	Node<T> tail = null;

	public void push(T value){
		Node<T> newNode = new Node<>(value);
		if (head == null) {
			head = newNode;
		} else {
			tail.next = newNode;
			newNode.previous = tail;
		}
		tail = newNode;
	}

	public T pop() {
		if (tail == null) {
			return null;
		}

		T value = tail.value;
		if (head == tail) {
			head = null;
			tail = null;
		} else {
			tail = tail.previous;
			tail.next = null;
		}
		return value;
	}

	public void unshift(T value){
		Node<T> newNode = new Node<>(value);
		if (head == null) {
			tail = newNode;
		} else {
			head.previous = newNode;
			newNode.next = head;
		}
		head = newNode;
	}

	public T shift(){
		if (head == null) {
			return null;
		}

		T value = head.value;
		if (head == tail) {
			head = null;
			tail = null;
		} else {
			head = head.next;
			head.previous = null;
		}
		return value;
	}
}