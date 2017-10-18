package ru.spbau.mit.vishnyakov;

import org.junit.Test;

import static org.junit.Assert.*;

public class BinaryTreeTest {

    @Test
    public void testAddSingleElement() throws Exception {
        BinaryTree<String> bt = new BinaryTree<>();
        assertEquals(true, bt.add("aaa"));
        assertEquals(true, bt.contains("aaa"));
    }

    @Test
    public void testAddMultipleElements() throws Exception {
        BinaryTree<String> bt = new BinaryTree<>();
        assertEquals(true, bt.add("aaa"));
        assertEquals(true, bt.add("bbb"));
        assertEquals(true, bt.add("ccc"));
        assertEquals(true, bt.contains("aaa"));
        assertEquals(true, bt.contains("bbb"));
        assertEquals(true, bt.contains("ccc"));
        assertEquals(false, bt.contains("ddd"));
    }

    @Test
    public void testAddMultipleElementsAddedTwice() throws Exception {
        BinaryTree<String> bt = new BinaryTree<>();
        assertEquals(true, bt.add("aaa"));
        assertEquals(false, bt.add("aaa"));
        assertEquals(true, bt.add("bbb"));
        assertEquals(false, bt.add("bbb"));
        assertEquals(true, bt.add("ccc"));
        assertEquals(false, bt.add("ccc"));
        assertEquals(true, bt.contains("aaa"));
        assertEquals(true, bt.contains("bbb"));
        assertEquals(true, bt.contains("ccc"));
    }

    @Test
    public void testContainsEmptyTree() throws Exception {
        BinaryTree<String> bt = new BinaryTree<>();
        assertEquals(false, bt.contains("aaa"));
    }

    @Test
    public void testContainsNonEmptyTree() throws Exception {
        BinaryTree<String> bt = new BinaryTree<>();
        bt.add("aaa");
        bt.add("bbb");
        bt.add("ccc");
        assertEquals(true, bt.contains("aaa"));
        assertEquals(true, bt.contains("bbb"));
        assertEquals(true, bt.contains("ccc"));
        assertEquals(false, bt.contains("ddd"));
    }

    @Test
    public void testContainsSameElementAddedTwice() throws Exception {
        BinaryTree<String> bt = new BinaryTree<>();
        bt.add("aaa");
        bt.add("aaa");
        bt.add("bbb");
        bt.add("bbb");
        bt.add("ccc");
        bt.add("ccc");
        assertEquals(true, bt.contains("aaa"));
        assertEquals(true, bt.contains("bbb"));
        assertEquals(true, bt.contains("ccc"));
        assertEquals(false, bt.contains("ddd"));
    }

    @Test
    public void sizeNoElementsAdded() throws Exception {
        BinaryTree<Integer> bt = new BinaryTree<>();
        assertEquals(0, bt.size());
    }

    @Test
    public void sizeSameElementAddedTwice() throws Exception {
        BinaryTree<Integer> bt = new BinaryTree<>();
        bt.add(1);
        bt.add(1);
        assertEquals(1, bt.size());
    }

    @Test
    public void sizeNonEmptyTree() throws Exception {
        BinaryTree<Integer> bt = new BinaryTree<>();
        for (int i = 0; i <= 100; i++) {
            bt.add(i);
        }
        assertEquals(101, bt.size());
    }
}