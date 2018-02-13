package ru.spbau.mit;

import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Firstly here go some test were written by me and then default tests.
 */

public class SmartListTest {

    SmartList<Integer> list;

    @Before
    public void init() {
        list = new SmartList<>();
    }

    @Test
    public void createFromCollection() {
        Collection<Integer> collection = new ArrayList<>();
        collection.add(1);
        collection.add(2);
        collection.add(3);
        SmartList<Integer> list2 = new SmartList<>(collection);
    }

    @Test
    public void addElementsThenRemoveAll() {

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(4);
        assertEquals(5, list.size());

        list.remove(0);
        list.remove(0);
        list.remove(0);
        list.remove(0);
        list.remove(0);
        assertEquals(0, list.size());
    }

    @Test
    public void singleAddWithShifting() {
        list.add(0, 0);
        assertEquals(Collections.singletonList(0), list);
    }

    @Test
    public void addWithShifting() {
        for(int i = 0; i < 5; i++) {
            list.add(i + 1);
        }

        list.add(2, 0);

        assertEquals(Arrays.asList(1, 2, 0, 3, 4, 5), list);
    }

    @Test
    public void addWithMultipleShifting() {
        for(int i = 0; i < 10; i++) {
            list.add(i + 1);
        }

        list.add(2, 0);
        list.add(5, -1);
        list.add(8, 100);

        assertEquals(Arrays.asList(1, 2, 0, 3, 4, -1, 5, 6 , 100, 7, 8, 9, 10), list);
    }

    @Test
    public void getWithShifting() {
        for(int i = 0; i < 10; i++) {
            list.add(i + 1);
        }

        list.add(5, -1);
        assertEquals(-1, list.get(5).intValue());
        assertEquals(6, list.get(6).intValue());
        assertEquals(5, list.get(4).intValue());
    }

    @Test
    public void clearTest() {
        for (int i = 0; i < 10; i++) {
            list.add(i + 1);
        }
        assertEquals(10 , list.size());
        list.clear();
        assertEquals(0, list.size());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIndexOutOfBoundsException() {
        Integer i = list.get(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIndexOutOfBoundsExceptionWithAdd() {
        for (int i = 0; i < 10; i++) {
            list.add(i + 1);
        }
        Integer i = list.get(100);
    }

    @Test
    public void testSimple() {
        List<Integer> list = newList();

        assertEquals(Collections.<Integer>emptyList(), list);

        list.add(1);
        assertEquals(Collections.singletonList(1), list);

        list.add(2);
        assertEquals(Arrays.asList(1, 2), list);
    }

    @Test
    public void testGetSet() {
        List<Object> list = newList();

        list.add(1);

        assertEquals(1, list.get(0));
        assertEquals(1, list.set(0, 2));
        assertEquals(2, list.get(0));
        assertEquals(2, list.set(0, 1));

        list.add(2);

        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));

        assertEquals(1, list.set(0, 2));

        assertEquals(Arrays.asList(2, 2), list);
    }

    @Test
    public void testRemove() throws Exception {
        List<Object> list = newList();

        list.add(1);
        list.remove(0);
        assertEquals(Collections.emptyList(), list);

        list.add(2);
        list.remove((Object) 2);
        assertEquals(Collections.emptyList(), list);

        list.add(1);
        list.add(2);
        assertEquals(Arrays.asList(1, 2), list);

        list.remove(0);
        assertEquals(Collections.singletonList(2), list);

        list.remove(0);
        assertEquals(Collections.emptyList(), list);
    }

    @Test
    public void testIteratorRemove() throws Exception {
        List<Object> list = newList();
        assertFalse(list.iterator().hasNext());

        list.add(1);

        Iterator<Object> iterator = list.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());

        iterator.remove();
        assertFalse(iterator.hasNext());
        assertEquals(Collections.emptyList(), list);

        list.addAll(Arrays.asList(1, 2));

        iterator = list.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());

        iterator.remove();
        assertTrue(iterator.hasNext());
        assertEquals(Collections.singletonList(2), list);
        assertEquals(2, iterator.next());

        iterator.remove();
        assertFalse(iterator.hasNext());
        assertEquals(Collections.emptyList(), list);
    }


    @Test
    public void testCollectionConstructor() throws Exception {
        assertEquals(Collections.emptyList(), newList(Collections.emptyList()));
        assertEquals(
                Collections.singletonList(1),
                newList(Collections.singletonList(1)));

        assertEquals(
                Arrays.asList(1, 2),
                newList(Arrays.asList(1, 2)));
    }

    @Test
    public void testAddManyElementsThenRemove() throws Exception {
        List<Object> list = newList();
        for (int i = 0; i < 7; i++) {
            list.add(i + 1);
        }

        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7), list);

        for (int i = 0; i < 7; i++) {
            list.remove(list.size() - 1);
            assertEquals(6 - i, list.size());
        }

        assertEquals(Collections.emptyList(), list);
    }

    private static <T> List<T> newList() {
        try {
            //noinspection unchecked
            return (List<T>) getListClass().getConstructor().newInstance();
        } catch (@NotNull InstantiationException | ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> List<T> newList(Collection<T> collection) {
        try {
            return (List<T>) getListClass().getConstructor(Collection.class).newInstance(collection);
        } catch (@NotNull InstantiationException | ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static Class<?> getListClass() throws ClassNotFoundException {
        return Class.forName("ru.spbau.mit.SmartList");
    }
}