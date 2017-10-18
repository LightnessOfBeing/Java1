package ru.spbau.mit.vishnyakov;

import java.util.Objects;

/**
 * @author Kirill Vishnyakov
 * This is an implementation of LinkedList data structure.
 * It has a nested static class Node, in order to represent nodes of Linked List.
 */

class MyLinkedList {

    /**
     * Nested class that represents node of a Linked list.
     */

    static class Node{

        /**
         * Key and value that are being stored in Node.
         */

        private final String key;
        private String value;

        /**
         * Reference on the next node.
         */

        private Node next;


        /**
         * Constructor which receives key-value pair.
         */

        Node(String k, String val){

            key = k;
            value = val;
            next = null;

        }

        public Node getNext(){
            return next;
        }

        public String getKey(){
            return key;
        }

        public String getValue(){
            return value;
        }

    }

    /**
     * Pointer to the head of LinkedList.
     */

    private Node head;

    /**
     * Constructor of LinkedList, that takes instance of class Node as a parameter.
     * @param node Node.
     */

    MyLinkedList(Node node){
        head = node;
    }

    /**
     * Default constructor.
     */

    MyLinkedList(){
        head = null;
    }

    /**
     * Method that adds newNode in LinkedList.
     * In case if List contains some value with key == newNode.getKey(), we simply replace old value.
     * Otherwise we create a new node in the end of LinkedList.
     * @param key - given key
     * @param value - given value
     * @return String which represented previous value, with the key == newNode.getKey().
     */

    public String add(String key, String value){

        String was = find(key);
        Node node = head;
        if (was == null) {

            if (head == null) {
                head = new Node(key, value);
                return null;
            }

            while (node.next != null) {
                node = node.next;
            }

            node.next = new Node(key, value);

            return null;
        }

        while (!node.key.equals(key)) {
            node = node.next;
        }

        node.value = value;

        return was;

    }

    public Node getHead(){
        return head;
    }

    /**
     * Method that finds value associated with the given key.
     * @param key - given key
     * @return null if key were already associated with some value, otherwise value associated with the given key.
     */

    public String find(String key){
        Node node = head;

        while (node != null && !node.key.equals(key)) {
            node = node.next;
        }

        String result = null;

        if (node != null) {
            result = node.value;
        }

        return result;
    }

    /**
     * Removes node that has a given key.
     * @param key - given key
     * @return previous value that wsa associated with the given key.
     */

    public String remove(String key){

        if (find(key) == null) {
            return null;
        }

        String was;

        if (Objects.equals(head.key, key)) {
            was = head.value;
            head = head.next;
            return was;
        }

        Node node = head;

        while (!Objects.equals(node.next.key, key)) {
            node = node.next;
        }

        was = node.next.value;
        node.next = node.next.next;

        return was;
    }

}
