package ru.spbau.mit.vishnyakov;

/**
 * Implementation of Hash Table with separate chaining with linked lists.
 */

public class HashMap {

    /**
     * An array, each cell contains a LinkedList, where values will be added.
     */

    private MyLinkedList[] array;

    /**
     * Number of elements in the HashTable.
     */

    private int size = 0;

    /**
     * ReHash threshold.
     */

    private final double reHashRatio = 0.5;

    /**
     * Initial capacity for default constructor.
     */
    private final int INITIAL_SIZE = 256;

    /**
     * Default constructor.
     */

    public HashMap() {
        array = new MyLinkedList[INITIAL_SIZE];
    }

    /**
     * Constructor that takes capacity as a parameter.
     * @param capacity capacity
     */

    public HashMap(int capacity) {
        array = new MyLinkedList[capacity];
    }

    /**
     * @return number of elements in HashTable.
     */

    public int size() {
        return size;
    }

    /**
     * @param key key
     * @return True if HashTable contains an element with the given key, otherwise False.
     */

    public boolean contains(String key) {

        int index = Math.abs(key.hashCode()) % array.length;
        MyLinkedList list = array[index];

        return list != null && list.find(key) != null;

    }

    /**
     * @param key key
     * @param value value
     * @return The previous value of the String that was associated with the given key, null in case there was no such String.
     */

    public String put(String key, String value) {

        rehash();

        int index = Math.abs(key.hashCode()) % array.length;
        if (array[index] == null) {
           array[index] = new MyLinkedList();
        }

        String previousValue = array[index].add(key, value);
        if (previousValue == null) {
            size++;
        }

        return previousValue;
    }

    /**
     * @param key key
     * @return String which is associated with the given key, null in case there was no such String.
     */

    public String get(String key) {

        int index = Math.abs(key.hashCode()) % array.length;
        if (array[index] == null) {
            return null;
        }

        return array[index].find(key);
    }

    /**
     * Removes the String with the given key.
     * @param key key
     * @return String that was associated with the given key, null in case there was no such String.
     */

    public String remove(String key) {

        if (!contains(key)) {
            return null;
        }

        int index = Math.abs(key.hashCode()) % array.length;

        size--;

        if (array[index] == null) {
            return null;
        }

        return array[index].remove(key);
    }

    /**
     * Clears the HashTable.
     */

    public void clear() {

        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
    }

    /**
     * Rehashes all the elements in HashTable, if threshold has been passed.
     * @return True if reHash occurred, otherwise False.
     */

    public boolean rehash() {

        if ((double) size / array.length < reHashRatio) {
            return false;
        }

        MyLinkedList[] newArray = new MyLinkedList[array.length * 2];

        for (MyLinkedList anArray : array) {
            if (anArray != null) {
                anArray.rehash(newArray, array.length * 2);
            }
        }

        array = newArray;

        return true;
    }

}
