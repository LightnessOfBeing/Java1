package ru.spbau.mit.vishnyakov;

public class Main {

    public static void main(String[] args) {
        HashMap ma = new HashMap();

        ma.put("first", "aa3a");
        ma.put("b", "aaa1");
        ma.put("c", "aaa2");
        ma.put("d", "aaa3");
        System.out.println(ma.size());
        ma.remove("b");
        System.out.println(ma.contains("b"));

    }
}
