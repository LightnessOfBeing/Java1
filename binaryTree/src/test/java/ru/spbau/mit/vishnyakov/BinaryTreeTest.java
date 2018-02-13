package ru.spbau.mit.vishnyakov;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BinaryTreeTest {

    private BinaryTree<String> btString;
    private BinaryTree<Integer> btInteger;

    @Before
    public void init() {
        btString = new BinaryTree<>();
        btInteger = new BinaryTree<>();
    }

    @Test
    public void testAddSingleElement() throws Exception {
        assertTrue(btString.add("aaa"));
        assertTrue(btString.contains("aaa"));
    }

    @Test
    public void testAddMultipleElements() throws Exception {
        assertTrue(btString.add("aaa"));
        assertTrue(btString.add("bbb"));
        assertTrue(btString.add("ccc"));
        assertTrue(btString.contains("aaa"));
        assertTrue(btString.contains("bbb"));
        assertTrue(btString.contains("ccc"));
        assertFalse(btString.contains("ddd"));
    }

    @Test
    public void testAddMultipleElementsAddedTwice() throws Exception {
        assertTrue(btString.add("aaa"));
        assertFalse(btString.add("aaa"));
        assertTrue(btString.add("bbb"));
        assertFalse(btString.add("bbb"));
        assertTrue(btString.add("ccc"));
        assertFalse(btString.add("ccc"));
        assertTrue(btString.contains("aaa"));
        assertTrue(btString.contains("bbb"));
        assertTrue(btString.contains("ccc"));
    }

    @Test
    public void testContainsEmptyTree() throws Exception {
        assertFalse(btString.contains("aaa"));
    }

    @Test
    public void testContainsNonEmptyTree() throws Exception {
        btString.add("aaa");
        btString.add("bbb");
        btString.add("ccc");
        assertTrue(btString.contains("aaa"));
        assertTrue(btString.contains("bbb"));
        assertTrue(btString.contains("ccc"));
        assertFalse(btString.contains("ddd"));
    }

    @Test
    public void testContainsSameElementAddedTwice() throws Exception {
        btString.add("aaa");
        btString.add("aaa");
        btString.add("bbb");
        btString.add("bbb");
        btString.add("ccc");
        btString.add("ccc");
        assertTrue(btString.contains("aaa"));
        assertTrue(btString.contains("bbb"));
        assertTrue(btString.contains("ccc"));
        assertFalse(btString.contains("ddd"));
    }

    @Test
    public void sizeNoElementsAdded() throws Exception {
        assertEquals(0, btInteger.size());
    }

    @Test
    public void sizeSameElementAddedTwice() throws Exception {
        btInteger.add(1);
        btInteger.add(1);
        assertEquals(1, btInteger.size());
    }

    @Test
    public void sizeNonEmptyTree() throws Exception {
        for (int i = 0; i <= 100; i++) {
            btInteger.add(i);
        }
        assertEquals(101, btInteger.size());
    }
}