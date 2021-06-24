package com.mojahed.turing.logic.util;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.util.NoSuchElementException;

/**
 * @author Ali Mojahed on 6/21/2021
 * @project turing-machine
 **/

@NoArgsConstructor
@Log4j2
public class DoublyLikedList<T> implements Serializable {

    private Node current;
    private Node head;
    private Node tail;
    private int size = 0;

    public void addFirst(T data) {
        Node newNode = new Node(data, head, null);
        if (head != null) {
            head.prev = newNode;

            if (head == current) {
                current.prev = newNode;
                current = newNode;
            }
        } else {
            current = newNode;
        }
        head = newNode;

        if (tail == null)
            tail = newNode;

        size++;
    }

    public void addLast(T data) {
        Node newNode = new Node(data, null, tail);
        if (tail != null) {
            tail.next = newNode;
        }
        tail = newNode;
        if (head == null) {
            head = current = newNode;

        }
        size++;
    }

    public void addAfterCurrent(T data) {
        if (isEmpty()) {
            addFirst(data);
            return;
        }
        if (current == tail) {
            addLast(data);
            return;
        }

        Node node = new Node(data, current.next, current);

        if (current.hasNext()) {
            current.next.prev = node;
        }
        current.next = node;

        size++;
    }

    public void addBeforeCurrent(T data) {
        if (isEmpty() || current == head) {
            addFirst(data);
            return;
        }

        Node node = new Node(data, current, current.prev);
        if (current.hasPrevious()) {
            current.prev.next = node;
        }
        current.prev = node;

    }

    public T deleteFirst() {
        if (isEmpty())
            throw new NoSuchElementException("Doubly linked list is already empty");

        T temp = head.data;

        if (current == head) {
            head = current = head.next;
            head.prev = current.prev = null;

        } else {
            head = head.next;
            head.prev = null;
        }

        size--;

        return temp;
    }

    public T deleteLast() {
        if (isEmpty())
            throw new NoSuchElementException("Doubly linked list is already empty");

        T temp = head.data;
        if (current == tail) {
            tail = current = tail.prev;
            tail.next = current.next = null;

        } else {
            tail = tail.prev;
            tail.next = null;
        }

        size--;

        return temp;

    }

    public T deleteCurrent() {
        if (isEmpty())
            throw new NoSuchElementException("Doubly linked list is already empty");

        if (current == head) {
            return deleteFirst();
        }

        if (current == tail) {
            return deleteLast();
        }

        T data = current.data;

        current.prev.next = current.next;
        current.next.prev = current.prev;
        current = current.prev;

        return data;
    }

    public boolean hasNext() {
        if (current != null) {
            return current.hasNext();
        }

        return false;
    }

    public boolean hasPrevious() {
        if (current != null) {
            return current.hasPrevious();
        }

        return false;
    }

    public T getCurrent() {
        if (isEmpty()) {
            throw new NoSuchElementException("Doubly linked list is already empty");
        }
        return current.data;
    }

    public void goNext() {
        if (isEmpty() || !hasNext()) {
            throw new NoSuchElementException("Doubly linked list is already empty");
        }

        current = current.next;

    }

    public void goPrevious() {
        if (isEmpty() || !hasPrevious()) {
            throw new NoSuchElementException("Doubly linked list is already empty");

        }

        current = current.prev;

    }

    public void goToFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Doubly linked list is already empty");
        }
        current = head;

    }

    public void goToLast() {
        if (isEmpty())
            throw new NoSuchElementException("Doubly linked list is already empty");

        current = tail;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Node temp = head;
        stringBuilder.append("[");
        if (!isEmpty()) {
            do {
                if (temp == current) {
                    stringBuilder.append("-> ");
                }

                stringBuilder.append(temp.data);
                temp = temp.next;

                if (!(temp == null || !temp.hasNext())) {
                    stringBuilder.append(", ");
                }

            } while (!(temp == null || !temp.hasNext()));

            stringBuilder.append(", ");
            if (tail == current) {
                stringBuilder.append("-> ");
            }
            stringBuilder.append(tail.data);
        }

        stringBuilder.append("]");

        return stringBuilder.toString();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    class Node implements Serializable{
        T data;
        Node next;
        Node prev;

        public Node(T data, Node next, Node prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }

        private boolean hasNext() {
            return next != null;
        }

        private boolean hasPrevious() {
            return prev != null;
        }

        @Override
        public String toString() {
            return "data=" + data ;
        }
    }

}
