package ru.academic.university.mit.vishnyakov;

import org.jetbrains.annotations.Nullable;

import java.util.NoSuchElementException;

/**
 * Implementation of stack data structure.
 * @param <T> type of storing objects.
 */

public class Stack<T> {

    /**
     * Size of stack.
     */

    private int n;

    /**
     * Top of stack.
     */

    @Nullable
    private Node first;

    /**
     * Class that represents nodes.
     */

    private class Node {
        private T T;
        @Nullable
        private Node next;
    }

    /**
     * Constructor that receives nothing.
     */

    public Stack() {
        n = 0;
        first = null;
    }

    /**
     * Returns true if this stack is empty.
     * @return true if this stack is empty; false otherwise
     */

    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * Returns the number of elements in the stack.
     * @return the number of elements in the stack
     */

    public int size() {
        return n;
    }

    /**
     * Adds the element to the stack.
     * @param T element to add.
     */

    public void push(T T) {
        Node old = first;
        first = new Node();
        first.T = T;
        first.next = old;
        n++;
    }

    /**
     * Removes and returns element from the top of stack.
     * @return element from the top of stack.
     * @throws NoSuchElementException if this stack is empty.
     */

    public T pop() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        assert first != null;
        T e = first.T;
        first = first.next;
        n--;
        return e;
    }

    /**
     * Returns element from the top of stack.
     * @return element from the top of stack.
     * @throws NoSuchElementException if this stack is empty.
     */

    public T top() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        assert first != null;
        return first.T;
    }

    public boolean empty() {
        return size() == 0;
    }
}
