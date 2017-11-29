package ru.academic.university.mit.vishnyakov;

import org.junit.Test;

import static org.junit.Assert.*;

public class StackTest {
    @Test
    public void isEmptyNoElements() throws Exception {
        Stack<Double> stack = new Stack<>();
        assertTrue(stack.empty());
    }

    @Test
    public void isEmptyAddedElements() throws Exception {
        Stack<Double> stack = new Stack<>();
        stack.push(3.0);
        stack.push(4.0);
        assertFalse(stack.empty());
    }

    @Test
    public void sizeEmpty() throws Exception {
        Stack<Double> stack = new Stack<>();
        assertEquals(0, stack.size());
    }

    @Test
    public void sizeNotEmpty() throws Exception {
        Stack<Double> stack = new Stack<>();
        stack.push(4.0);
        stack.push(2.0);
        assertEquals(2, stack.size());
    }

    @Test
    public void push() throws Exception {
        Stack<Double> stack = new Stack<>();
        stack.push(4.0);
        stack.push(2.0);
        stack.push(4.0);
        assertEquals(3, stack.size());
    }

    @Test
    public void pop() throws Exception {
        Stack<Integer> stack = new Stack<>();
        stack.push(4);
        stack.push(2);
        stack.push(4);

        assertEquals(4, stack.pop().intValue());
        assertEquals(2, stack.pop().intValue());
        assertEquals(4, stack.pop().intValue());
    }

    @Test
    public void top() throws Exception {
        Stack<Integer> stack = new Stack<>();
        stack.push(4);
        stack.push(2);
        assertEquals(2, stack.top().intValue());
        stack.push(4);
        assertEquals(4, stack.top().intValue());
        stack.pop();
        assertEquals(2, stack.top().intValue());
    }
}