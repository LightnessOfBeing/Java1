package ru.spbau.mit.vishnyakov;

import org.junit.Test;

import static org.junit.Assert.*;

public class PredicateTest {
    @Test
    public void alwaysTrue() throws Exception {
        Predicate<Boolean> p = Predicate.alwaysTrue();
        assertEquals(true, p.apply(true));
        assertEquals(true, p.apply(false));
    }

    @Test
    public void alwaysFalse() throws Exception {
        Predicate<Boolean> p = Predicate.alwaysFalse();
        assertEquals(false, p.apply(true));
        assertEquals(false, p.apply(false));
    }

    @Test
    public void not() throws Exception {
        Predicate<Integer> p = x -> (x > 0);
        assertEquals(true, p.apply(1));
        assertEquals(false, p.apply(0));
        assertEquals(false, p.apply(-1));
    }

    @Test
    public void and() throws Exception {
        Predicate<Integer> p1 = x -> (x > 0);
        Predicate<Integer> p2 = x -> (x > 5);
        Predicate<Integer> p3 = p1.and(p2);
        assertEquals(true, p3.apply(100));
        assertEquals(false, p3.apply(0));
        assertEquals(false, p3.apply(-1));
        assertEquals(false, p3.apply(5));
    }

    @Test
    public void or() throws Exception {
        Predicate<Integer> p1 = x -> (x > 0);
        Predicate<Integer> p2 = x -> (x > 5);
        Predicate<Integer> p3 = p1.or(p2);
        assertEquals(true, p3.apply(100));
        assertEquals(false, p3.apply(0));
        assertEquals(false, p3.apply(-1));
        assertEquals(true, p3.apply(5));
    }
}