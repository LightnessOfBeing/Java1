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
     * Capacity of HashTable
     */

    private int capacity;

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

    public HashMap(){

        array = new MyLinkedList[INITIAL_SIZE];
        capacity = INITIAL_SIZE;

    }

    /**
     * Constructor that takes capacity as a parameter.
     * @param capacity capacity
     */

    public HashMap(int capacity){

        array = new MyLinkedList[capacity];
        this.capacity = capacity;

    }

    /**
     * @return number of elements in HashTable.
     */

    public int size(){
        return size;
    }

    /**
     * @param key key
     * @return True if HashTable contains an element with the given key, otherwise False.
     */

    public boolean contains(String key) {

        int index = Math.abs(key.hashCode()) % capacity;
        MyLinkedList list = array[index];

        return list != null && list.getHead() != null && list.find(key) != null;

    }

    /**
     * @param key key
     * @param value value
     * @return The previous value of the String that was associated with the given key, null in case there was no such String.
     */

    public String put(String key, String value){

        rehash();

        int index = Math.abs(key.hashCode()) % capacity;
        if (array[index] == null){
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

    public String get(String key){

        int index = Math.abs(key.hashCode()) % capacity;
        if(array[index] == null) {
            return null;
        }

        return array[index].find(key);
    }

    /**
     * Removes the String with the given key.
     * @param key key
     * @return String that was associated with the given key, null in case there was no such String.
     */

    public String remove(String key){

        if (!contains(key)) {
            return null;
        }

        int index = Math.abs(key.hashCode()) % capacity;

        size--;

        if (array[index] == null) {
            return null;
        }

        return array[index].remove(key);
    }

    /**
     * Clears the HashTable.
     */

    public void clear(){

        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
    }

    /**
     * Rehashes all the elements in HashTable, if threshold has been passed.
     * @return True if reHash occurred, otherwise False.
     */

    public boolean rehash(){

        if ((double) size/capacity < reHashRatio) {
            return false;
        }

        int oldCapacity = capacity;
        capacity *= 2;
        MyLinkedList[] newArray = new MyLinkedList[capacity];

        for (int i = 0; i < oldCapacity; i++){
            if (array[i] != null) {
                MyLinkedList.Node curNode = array[i].getHead();

                while (curNode != null) {
                    int index = Math.abs(curNode.getKey().hashCode()) % capacity;

                    if (newArray[index] == null) {
                        newArray[index] = new MyLinkedList(curNode);
                    }
                    else {
                        array[index].add(curNode.getKey(), curNode.getValue());
                    }
                    curNode = curNode.getNext();
                }
            }
        }

        array = newArray;

        return true;
    }

    public int getCapacity(){
        return capacity;
    }

}
