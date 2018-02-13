package ru.academic.university.mit.vishnyakov;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StackTest {

    Stack<Double> stackDouble;
    Stack<Integer> stackInteger;

    @Before
    public void init() {
        stackDouble = new Stack<>();
        stackInteger = new Stack<>();
    }

    @Test
    public void isEmptyNoElements() throws Exception {
        assertTrue(stackDouble.empty());
    }

    @Test
    public void isEmptyAddedElements() throws Exception {
        stackDouble.push(3.0);
        stackDouble.push(4.0);
        assertFalse(stackDouble.empty());
    }

    @Test
    public void sizeEmpty() throws Exception {
        assertEquals(0, stackDouble.size());
    }

    @Test
    public void sizeNotEmpty() throws Exception {
        stackDouble.push(4.0);
        stackDouble.push(2.0);
        assertEquals(2, stackDouble.size());
    }

    @Test
    public void push() throws Exception {
        stackDouble.push(4.0);
        stackDouble.push(2.0);
        stackDouble.push(4.0);
        assertEquals(3, stackDouble.size());
    }

    @Test
    public void pop() throws Exception {
        stackInteger.push(4);
        stackInteger.push(2);
        stackInteger.push(4);

        assertEquals(4, stackInteger.pop().intValue());
        assertEquals(2, stackInteger.pop().intValue());
        assertEquals(4, stackInteger.pop().intValue());
    }

    @Test
    public void top() throws Exception {
        stackInteger.push(4);
        stackInteger.push(2);
        assertEquals(2, stackInteger.top().intValue());
        stackInteger.push(4);
        assertEquals(4, stackInteger.top().intValue());
        stackInteger.pop();
        assertEquals(2, stackInteger.top().intValue());
    }
}