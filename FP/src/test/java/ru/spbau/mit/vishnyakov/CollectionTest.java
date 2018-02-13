package ru.spbau.mit.vishnyakov;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CollectionTest {
    @Test
    public void map() throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        Function1<Integer, Integer> function = (x) -> x + 1;
        for (int i = 0; i < 10; i++) {
            list.add(i + 1);
        }
        ArrayList<Integer> mapped = Collection.map(function, list);
        for (int i = 0; i < 10; i++) {
            assertEquals(i + 2, mapped.get(i).intValue());
        }
    }

    @Test
    public void filter() throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        Predicate<Integer> p = (x) -> x > 8;
        for (int i = 0; i < 10; i++) {
            list.add(i + 1);
        }
        ArrayList<Integer> mapped = Collection.filter(p, list);
        assertEquals(2, mapped.size());
    }

    @Test
    public void takeWhile() throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        Predicate<Integer> p = (x) -> x < 3;
        for (int i = 0; i < 10; i++) {
            list.add(i + 1);
        }
        ArrayList<Integer> mapped = Collection.takeWhile(p, list);
        assertEquals(2, mapped.size());
    }

    @Test
    public void takeUnless() throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        Predicate<Integer> p = (x) -> x > 2;
        for (int i = 0; i < 10; i++) {
            list.add(i + 1);
        }
        ArrayList<Integer> mapped = Collection.takeUnless(p, list);
        assertEquals(2, mapped.size());
    }

    @Test
    public void foldl() throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        Function2<Integer, Integer, Integer> function = (x, y) -> x + y;
        for (int i = 1; i <= 10; i++) {
            list.add(i);
        }
        Integer acc = Collection.foldl(function, 0, list);
        assertEquals(55, acc.intValue());
    }

    @Test
    public void foldr() throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        Function2<Integer, Integer, Integer> function = (x, y) -> x + y;
        for (int i = 1; i <= 10; i++) {
            list.add(i);
        }
        Integer acc = Collection.foldr(function, 0, list);
        assertEquals(55, acc.intValue());
    }

}