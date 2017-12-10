package ru.spbau.mit.vishnyakov;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Class that represents BinaryTree.
 * @param <T> type stored elements.
 */

public class BinaryTree<T extends Comparable<T>> {

    /**
     * Class that represents node of a BinaryTree.
     * @param <T> type of elements.
     */

    private static class Node<T extends Comparable<T>> implements Comparable<Node<T>> {

        /**
         * Data of a node.
         */

        @Nullable
        private T data;

        /**
         * Left child.
         */

        @Nullable
        private Node<T> left;

        /**
         * Right child.
         */

        @Nullable
        private Node<T> right;

        /**
         * Constructor.
         * @param data data supposed to be stored in a node.
         */

        public Node(T data) {
            this.data = data;
            left = null;
            right = null;
        }

        @Nullable
        public Node<T> getLeft() {
            return left;
        }

        @Nullable
        public Node<T> getRight() {
            return right;
        }

        public void setLeft(@Nullable Node<T> node) {
            left = node;
        }

        public void setRight(@Nullable Node<T> node) {
            right = node;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        @Override
        public int compareTo(@NotNull Node<T> node) {
            return data.compareTo(node.data);
        }
    }

    /**
     * Size of a BinaryTree.
     */

    private int size;

    /**
     * Root of a BinaryTree.
     */

    @Nullable
    private final Node<T> root;

    /**
     * Constructor.
     */

    public BinaryTree() {
        size = 0;
        root = new Node<>(null);
    }

    /**
     * Method that adds elements in BinaryTree.
     * Goes from root till it reaches null elements, creates new node and changes child of parent.
     * @param data data that supposed to be stored in a node.
     * @return true if element have been added, false if element have been already added in a BinaryTree.
     */

    public boolean add(@NotNull T data) {

        assert root != null;
        if (root.getData() == null) {
            root.setData(data);
            size++;
            return true;
        }

        Node<T> curNode = root;
        Node<T> parent = root;
        Node<T> node = new Node<>(data);

        while (curNode != null) {
            parent = curNode;
            if (curNode.compareTo(node) > 0) {
                curNode = curNode.getLeft();
            }
            else if (curNode.compareTo(node) < 0) {
                curNode = curNode.getRight();
            }
            else {
                return false;
            }
        }

        size++;

        if (parent.compareTo(node) < 0) {
            parent.setRight(node);
        }
        else{
            parent.setLeft(node);
        }

        return true;
    }

    /**
     * Method that checks if BinaryTree contains given element.
     * @param data that supposed to be stored in node.
     * @return true if element is stored in BinaryTree, otherwise false.
     */

    public boolean contains(@NotNull T data) {
        Node<T> node = new Node(data);
        Node<T> curNode = root;

        assert root != null;
        if (root.getData() == null) {
            return false;
        }

        while (curNode != null) {
            if(curNode.compareTo(node) > 0) {
                curNode = curNode.getLeft();
            }
            else if (curNode.compareTo(node) < 0) {
                curNode = curNode.getRight();
            }
            else {
                return true;
            }
        }

        return false;
    }

    public int size() {
        return size;
    }
}
