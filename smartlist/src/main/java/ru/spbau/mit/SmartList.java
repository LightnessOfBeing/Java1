package ru.spbau.mit;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Class that implements SmartList behaviour.
 * @param <E> type of stored elements.
 */

@SuppressWarnings("unchecked")
public class SmartList<E> extends AbstractList<E> {

    /**
     * Size of a Smart List.
     */

    private int size;

    /**
     * Reference to stored elements.
     */

    @Nullable
    private Object reference;

    /**
     * Default constructor.
     */

    public SmartList() {
    }

    /**
     * Constructor that receives collection.
     * @param collection which elements will be stored in SmartList.
     */

    public SmartList(@NotNull Collection<E> collection) {
        addAll(collection);
    }

    /**
     * Adds element at the end of the list.
     * @param e is element to add
     * @return true if addition was successful, otherwise false.
     */

    @Override
    public boolean add(E e) {
        if (size == 0) {
            reference = e;
        }
        else if (size < 5) {
            if (size == 1) {
                Object[] array = new Object[5];
                array[0] = reference;
                reference = array;
            }
            ((Object[]) reference)[size] = e;
        }
        else {
            if (size == 5) {
                ArrayList<Object> list = new ArrayList<>();
                Object[] was = (Object[]) reference;
                Collections.addAll(list, was);
                reference = list;
            }
            ((ArrayList) reference).add(e);
        }
        size++;
        return true;
    }

    /**
     * Adds element on given position and shifts element to the right.
     * @param i index where we want to insert our element.
     * @param e is an element we want to insert.
     */

    @Override
    public void add(int i, E e) {
        if (i < 0 || i > size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (i == size) {
            add(e);
            return;
        }
        if (size < 5) {
            if (size == 1) {
                Object[] array = new Object[5];
                if (i == 0) {
                    array[0] = reference;
                }
                reference = array;
            }
            Object[] array = (Object[]) reference;
            System.arraycopy(array, i, array, i + 1, size - i);
            array[i] = e;
        }
        else {
            if (size == 5) {
                Object[] oldArray = (Object[]) reference;
                ArrayList<Object> array = new ArrayList<>();
                array.addAll(Arrays.asList(oldArray).subList(0, size));
                reference = array;
            }
            ((ArrayList)reference).add(i, e);
        }
        size++;
    }

    /**
     * Returns the element from the given position.
     * May throw OutOfBoundException.
     * @param i is an index of wanted element.
     * @return element from given position.
     */

    @Nullable
    @Override
    public E get(int i) {
        if (i < 0 || i >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (size == 1) {
            return (E) reference;
        }
        if (size <= 5) {
            return (E) ((Object[]) reference)[i];
        }
        return (E) (((ArrayList) reference).get(i));
    }

    /**
     * Assigns the value to the given position in array.
     * @param index where we want to set.
     * @param e element which will be the new value.
     * @return previous value at this index.
     */

    @Nullable
    @Override
    public E set(int index, E e) {
        if (index >= size || index < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        E old;
        if (size == 1) {
            old = (E) reference;
            reference = e;
        }
        else if (size > 1 && size <= 5) {
            old = (E) ((Object [])reference)[index];
            ((Object [])reference)[index] = e;
        }
        else {
            old = (E) ((ArrayList) reference).set(index, e);
            ((ArrayList) reference).set(index, e);
        }
        return old;
    }

    /**
     * Removes the elemet at the given index.
     * @param index where we want delete our element.
     * @return Element from deleted position.
     */

    @Nullable
    @Override
    public E remove(int index) {
        if (index >= size || index < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        E was = null;
        if (size == 1) {
            was = (E) reference;
            reference = null;
        }
        else if (size == 2) {
            was = (E)((Object[]) reference)[index];
            E cur = (E)((Object[]) reference)[index ^ 1];
            reference = cur;
        }
        else if (size > 5) {
            was = (E) ((ArrayList)reference).remove(index);
            if (size == 6) {
                reference = ((ArrayList) reference).toArray();
            }
        }
        size--;
        return was;
    }

    /**
     * Returns size of a list.
     * @return size of a list.
     */

    @Override
    public int size() {
        return size;
    }

    /**
     * Clears the SmartList.
     */

    @Override
    public void clear() {
        reference = null;
        size = 0;
    }
}