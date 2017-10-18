package ru.spbau.mit.vishnyakov;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class TrieTest {

    @Test
    public void sizeEmptyTrie() throws Exception {
        Trie t = new Trie();

        assertEquals(0, t.size());
    }

    @Test
    public void sizeNonEmptyTrie() throws Exception {
        Trie t = new Trie();

        t.add("a");
        assertEquals(1, t.size());
    }

    @Test
    public void sizeAddMultipleWords() throws Exception {
        Trie t = new Trie();

        t.add("a");
        t.add("b");
        t.add("c");
        assertEquals(3, t.size());
    }

    @Test
    public void sizeAddMultipleWordsWithSamePrefix() throws Exception {
        Trie t = new Trie();

        t.add("aaabc");
        t.add("aaabb");
        t.add("aaa");
        t.add("d");
        assertEquals(4, t.size());
    }

    @Test
    public void sizeDoubleAdd() throws Exception {
        Trie t = new Trie();

        t.add("aaa");
        t.add("aaa");
        assertEquals(1, t.size());
    }

    @Test
    public void sizeAddThenRemove() throws Exception {
        Trie t = new Trie();

        t.add("aaa");
        t.remove("aaa");

        assertEquals(0, t.size());
    }

    @Test
    public void sizeAddThenRemoveComplex() throws Exception {
        Trie t = new Trie();

        t.add("a");
        t.add("b");
        t.add("c");
        t.add("aaab");
        t.add("aaaaabc");
        t.add("aaaaabb");

        assertEquals(6, t.size());

        t.remove("aaab");
        assertEquals(5, t.size());

        t.remove("a");
        assertEquals(4, t.size());

        t.remove("aaaaabc");
        assertEquals(3, t.size());
    }

    @Test
    public void sizeAddThenRemoveAll() throws Exception {
        Trie t = new Trie();

        t.add("a");
        t.add("b");
        t.add("c");
        t.add("aaab");
        t.add("aaaaabc");
        t.add("aaaaabb");

        t.remove("a");
        t.remove("b");
        t.remove("c");
        t.remove("aaab");
        t.remove("aaaaabc");
        t.remove("aaaaabb");

        assertEquals(0, t.size());
    }

    @Test
    public void addOneWord() throws Exception {
        Trie t = new Trie();

        t.add("a");
        assertEquals(true, t.contains("a"));
    }

    @Test
    public void addTwoWords() throws Exception {
        Trie t = new Trie();

        t.add("a");
        assertEquals(true, t.contains("a"));

        t.add("b");
        assertEquals(true, t.contains("b"));
    }

    @Test
    public void addSameWordTwice() throws Exception {
        Trie t = new Trie();

        t.add("a");
        assertEquals(true, t.contains("a"));

        t.add("a");
        assertEquals(true, t.contains("a"));
    }

    @Test
    public void addMultipleWordsWithSamePrefix() throws Exception {
        Trie t = new Trie();

        t.add("addd");
        assertEquals(true, t.contains("addd"));

        t.add("add1a");
        assertEquals(true, t.contains("add1a"));

        t.add("add1a312");
        assertEquals(true, t.contains("add1a312"));

        t.add("abba");
        assertEquals(true, t.contains("abba"));
    }

    @Test
    public void addThenRemove() throws Exception {
        Trie t = new Trie();

        t.add("a");
        assertEquals(true, t.contains("a"));

        t.remove("a");
        assertEquals(false, t.contains("a"));
    }

    @Test
    public void addThenRemoveWithSamePrefix() throws Exception {
        Trie t = new Trie();

        t.add("aaabc");
        t.add("aaabb");
        t.add("aaa");
        t.add("d");

        t.remove("aaa");

        assertEquals(false, t.contains("aaa"));
        assertEquals(true, t.contains("aaabc"));
        assertEquals(true, t.contains("aaabb"));
        assertEquals(true, t.contains("d"));

        t.remove("aaabb");
        assertEquals(false, t.contains("aaabb"));
        assertEquals(true, t.contains("aaabc"));
        assertEquals(true, t.contains("d"));
        assertEquals(false, t.contains("a"));
    }

    @Test
    public void addThenDoubleRemove() throws Exception {
        Trie t = new Trie();

        t.add("aaa");
        t.remove("aaa");
        t.remove("aaa");

        assertEquals(false, t.contains("aaa"));
    }

    @Test
    public void containsEmptyTrie() throws Exception {
        Trie t = new Trie();

        assertEquals(false, t.contains("a"));
    }

    @Test
    public void containsNonEmptyTrie() throws Exception {
        Trie t = new Trie();
        t.add("a");

        assertEquals(true, t.contains("a"));
    }

    @Test
    public void containsMultipleWords() throws Exception {
        Trie t = new Trie();
        t.add("a");
        t.add("b");
        t.add("c");

        assertEquals(true, t.contains("a"));
        assertEquals(true, t.contains("b"));
        assertEquals(true, t.contains("c"));
    }

    @Test
    public void containsMultipleWordsWithSamePrefix() throws Exception {
        Trie t = new Trie();

        t.add("a");
        t.add("b");
        t.add("c");
        t.add("aaab");
        t.add("aaaaabc");
        t.add("aaaaabb");

        assertEquals(true, t.contains("a"));
        assertEquals(true, t.contains("b"));
        assertEquals(true, t.contains("c"));
        assertEquals(true, t.contains("aaab"));
        assertEquals(true, t.contains("aaaaabc"));
        assertEquals(true, t.contains("aaaaabb"));
    }

    @Test
    public void containsMultipleWordsWithSamePrefixAndRemove() throws Exception {
        Trie t = new Trie();

        t.add("a");
        t.add("b");
        t.add("c");
        t.add("aaab");
        t.add("aaaaabc");
        t.add("aaaaabb");

        t.remove("a");
        assertEquals(false, t.contains("a"));
        assertEquals(true, t.contains("b"));
        assertEquals(true, t.contains("c"));
        assertEquals(true, t.contains("aaab"));
        assertEquals(true, t.contains("aaaaabc"));
        assertEquals(true, t.contains("aaaaabb"));

        t.remove("aaab");
        t.add("a");
        assertEquals(true, t.contains("a"));
        assertEquals(true, t.contains("b"));
        assertEquals(true, t.contains("c"));
        assertEquals(false, t.contains("aaab"));
        assertEquals(true, t.contains("aaaaabc"));
        assertEquals(true, t.contains("aaaaabb"));
    }

    @Test
    public void containsAddThenRemoveAll() throws Exception {
        Trie t = new Trie();

        t.add("a");
        t.add("b");
        t.add("c");
        t.add("aaab");
        t.add("aaaaabc");
        t.add("aaaaabb");

        t.remove("a");
        t.remove("b");
        t.remove("c");
        t.remove("aaab");
        t.remove("aaaaabc");
        t.remove("aaaaabb");

        assertEquals(false, t.contains("a"));
        assertEquals(false, t.contains("b"));
        assertEquals(false, t.contains("c"));
        assertEquals(false, t.contains("aaab"));
        assertEquals(false, t.contains("aaaaabc"));
        assertEquals(false, t.contains("aaaaabb"));
    }


    @Test
    public void howManyStartsWithPrefixEmptyTrie() throws Exception {
        Trie t = new Trie();
        assertEquals(0, t.howManyStartsWithPrefix("a"));
    }

    @Test
    public void howManyStartsWithPrefixNonEmptyTrie() throws Exception {
        Trie t = new Trie();
        t.add("a");
        assertEquals(1, t.howManyStartsWithPrefix("a"));
    }

    @Test
    public void howManyStartsWithPrefixMultipleAdd() throws Exception {
        Trie t = new Trie();
        t.add("a");
        t.add("b");
        t.add("c");
        assertEquals(1, t.howManyStartsWithPrefix("a"));
        assertEquals(1, t.howManyStartsWithPrefix("b"));
        assertEquals(1, t.howManyStartsWithPrefix("c"));
    }

    @Test
    public void howManyStartsWithPrefixMultipleAddWithSamePrefix() throws Exception {
        Trie t = new Trie();
        t.add("a");
        t.add("aaaadd");
        t.add("aaaabc");
        t.add("aaaad");
        t.add("abb");

        t.add("b");
        t.add("bb");
        t.add("c");

        assertEquals(5, t.howManyStartsWithPrefix("a"));
        assertEquals(3, t.howManyStartsWithPrefix("aaaa"));
        assertEquals(1, t.howManyStartsWithPrefix("aaaadd"));
        assertEquals(1, t.howManyStartsWithPrefix("ab"));
        assertEquals(1, t.howManyStartsWithPrefix("bb"));
        assertEquals(2, t.howManyStartsWithPrefix("b"));
    }

    @Test
    public void howManyStartsWithPrefixDoubleAdd() throws Exception {
        Trie t = new Trie();
        t.add("a");
        t.add("a");

        assertEquals(1, t.howManyStartsWithPrefix("a"));
    }

    @Test
    public void howManyStartsWithPrefixMultipleAddThenRemove() throws Exception {
        Trie t = new Trie();
        t.add("a");
        t.add("aaaadd");
        t.add("aaaabc");
        t.add("aaaad");
        t.add("abb");

        t.add("b");
        t.add("bb");

        t.remove("a");
        assertEquals(4, t.howManyStartsWithPrefix("a"));
        assertEquals(3, t.howManyStartsWithPrefix("aaaa"));

        assertEquals(1, t.howManyStartsWithPrefix("aaaab"));

        t.remove("aaaabc");
        assertEquals(2, t.howManyStartsWithPrefix("aaaa"));

        t.remove("aaaad");
        t.remove("aaaadd");
        assertEquals(0, t.howManyStartsWithPrefix("aaaa"));

        assertEquals(2, t.howManyStartsWithPrefix("b"));

        assertEquals(0, t.howManyStartsWithPrefix("bc"));
    }

    @Test
    public void removeNonExistentKey() throws Exception {
        Trie t = new Trie();
        t.add("a");
        assertEquals(false, t.remove("b"));
    }

    @Test
    public void removeExistentKey() throws Exception {
        Trie t = new Trie();
        t.add("a");
        assertEquals(true, t.remove("a"));
        assertEquals(0, t.size());
    }

    @Test
    public void removeKeyWithSamePrefix() throws Exception {
        Trie t = new Trie();
        t.add("aaaa");
        t.add("aaab");
        t.add("aaaabc");
        t.add("aaa");
        t.add("aaabccfd");
        assertEquals(true, t.remove("aaaa"));

        assertEquals(4, t.size());
        assertEquals(false, t.remove("aaaa"));

        assertEquals(true, t.remove("aaa"));
        assertEquals(3, t.size());

        assertEquals(false, t.remove("a"));
        assertEquals(3, t.size());

        assertEquals(true, t.remove("aaab"));
        assertEquals(true, t.remove("aaaabc"));
        assertEquals(true, t.remove("aaabccfd"));

        assertEquals(0, t.size());
    }

    @Test
    public void serialize() throws IOException {
        Trie t = new Trie();
        t.add("a");
        t.add("ab");
        t.add("aaaabcd");
        t.add("aaacdv");
        t.add("dd");
        t.add("d");
        OutputStream out = new FileOutputStream("out.txt");
        t.serialize(out);
    }

    @Test
    public void deserialize() throws IOException, ClassNotFoundException {
        Trie t = new Trie();
        InputStream in = new FileInputStream("out.txt");
        t.deserialize(in);
        assertEquals(true, t.contains("a"));
        assertEquals(true, t.contains("ab"));
        assertEquals(true, t.contains("aaacdv"));
        assertEquals(true, t.contains("aaaabcd"));
        assertEquals(true, t.contains("dd"));
        assertEquals(true, t.contains("d"));
    }
}