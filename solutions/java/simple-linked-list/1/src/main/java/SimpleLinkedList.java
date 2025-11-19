import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.NoSuchElementException;

class SimpleLinkedList<T> {
    class Node {
        private T data;
        private Node next;

        public Node(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Node getNext() {
            if (next == null) {
                System.out.println(data);
            }
            return next;
        }

        public void setNext(Node node) {
            this.next = node;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    SimpleLinkedList() {}

    SimpleLinkedList(T[] values) {
        Arrays.stream(values).forEach(this::push);
    }

    void push(T value) {
        Node node = new Node(value);
        if (head == null) {
            head = node;
            tail = node;
        }

        tail.setNext(node);
        tail = node;
        size++;
    }

    T pop() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        T data = tail.getData();
        size--;

        if (head.equals(tail)) {
            head = null;
            tail = null;
            return data;
        }

        tail = getPrev(tail);
        tail.setNext(null);

        return data;
    }

    private Node getPrev(Node node) {
        if (head.equals(node)) return null;

        Node current = head;

        while (!current.getNext().equals(node)) {
            current = current.getNext();
        }
        return current;
    }

    void reverse() {
        @SuppressWarnings("unchecked")
        Object[] arr = asArray((Class<T>) Object.class);
        head = null;
        Arrays.stream(arr)
                .map(o -> (T)o)
                .forEach(this::push);
    }

    T[] asArray(Class<T> clazz) {
        @SuppressWarnings("unchecked")
        T[] arr = (T[]) Array.newInstance(clazz, size);
        Node curr = head;

        for (int i = size - 1; i >= 0; i--) {
            arr[i] = curr.getData();
            curr = curr.getNext();
        }

        return arr;
    }

    int size() {
        return size;
    }
}
