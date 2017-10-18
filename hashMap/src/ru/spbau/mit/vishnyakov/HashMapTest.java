package ru.spbau.mit.vishnyakov;

import static org.junit.Assert.*;

public class HashMapTest {

    @org.junit.Test
    public void sizeZeroElementsAdded() throws Exception {

        HashMap ma = new HashMap();

        assertEquals(0, ma.size());

    }

    @org.junit.Test
    public void sizeAddedSomeElements() throws Exception {

        HashMap ma = new HashMap();
        ma.put("1", "a");
        ma.put("2", "a");
        ma.put("3", "a");

        assertEquals(3, ma.size());
    }

    @org.junit.Test
    public void sizeTwoElementsSameKey() throws Exception {

        HashMap ma = new HashMap();

        ma.put("1", "a");
        ma.put("2", "a");
        ma.put("3", "a");
        ma.put("1", "b");
        ma.put("1", "b");

        assertEquals(3, ma.size());

    }

    @org.junit.Test
    public void sizeRemove() throws Exception {

        HashMap ma = new HashMap();

        ma.put("1", "a");
        ma.put("2", "a");
        ma.put("3", "a");
        ma.put("1", "b");

        ma.remove("1");

        assertEquals(2, ma.size());

        ma.remove("1");

        assertEquals(2, ma.size());

        ma.remove("2");
        ma.remove("3");

        assertEquals(0, ma.size());

        ma.remove("5");

        assertEquals(0, ma.size());
    }

    @org.junit.Test
    public void containsNoElementsAdded() throws Exception {

        HashMap ma = new HashMap();

        assertEquals(false, ma.contains("1"));

    }

    @org.junit.Test
    public void containsAdded() throws Exception {

        HashMap ma = new HashMap();

        ma.put("1", "a");

        assertEquals(true, ma.contains("1"));

        ma.put("1", "b");

        assertEquals(true, ma.contains("1"));
        assertEquals(false, ma.contains("2"));

    }

    @org.junit.Test
    public void containsRemove() throws Exception {

        HashMap ma = new HashMap();

        ma.put("1", "a");

        ma.put("1", "b");

        ma.remove("1");
        assertEquals(false, ma.contains("1"));

        ma.put("1", "a");
        assertEquals(true, ma.contains("1"));
    }

    @org.junit.Test
    public void putNoPreviousValue() throws Exception {

        HashMap ma = new HashMap();

        assertEquals(null, ma.put("1", "a"));
    }

    @org.junit.Test
    public void putHasPreviousValue() throws Exception {

        HashMap ma = new HashMap();

        ma.put("1", "a");

        assertEquals(true, ma.contains("1"));
        assertEquals("a", ma.put("1", "b"));

    }

    @org.junit.Test
    public void putRemovePreviousValue() throws Exception {

        HashMap ma = new HashMap();

        ma.put("1", "b");
        ma.remove("1");

        assertEquals(null, ma.put("1", "c"));

    }

    @org.junit.Test
    public void getNoPreviousValue() throws Exception {

        HashMap ma = new HashMap();

        assertEquals(null, ma.get("1"));

    }

    @org.junit.Test
    public void getHasPreviousValue() throws Exception {

        HashMap ma = new HashMap();

        ma.put("1", "a");

        assertEquals("a", ma.get("1"));

        ma.put("1", "b");

        assertEquals("b", ma.get("1"));

    }

    @org.junit.Test
    public void getRemovePreviousValue() throws Exception {

        HashMap ma = new HashMap();

        ma.put("1", "b");
        ma.remove("1");

        assertEquals(null, ma.get("1"));

    }

    @org.junit.Test
    public void clearNoElements() throws Exception {

        HashMap ma = new HashMap();

        ma.clear();
        assertEquals(0, ma.size());
    }

    @org.junit.Test
    public void clearHasElements() throws Exception {

        HashMap ma = new HashMap();

        ma.put("1", "a");
        ma.put("2", "b");

        assertEquals(2, ma.size());

        ma.clear();
        assertEquals(0, ma.size());
    }

    @org.junit.Test
    public void rehashOnCapacity4() throws Exception {

        HashMap ma = new HashMap(4);

        ma.put("1", "a");
        ma.put("2", "b");

        int curCapacity = ma.getCapacity();

        ma.rehash();

        assertEquals(2 * curCapacity, ma.getCapacity());

        assertEquals(false, ma.rehash());
        assertEquals(2 * curCapacity, ma.getCapacity());
    }

}