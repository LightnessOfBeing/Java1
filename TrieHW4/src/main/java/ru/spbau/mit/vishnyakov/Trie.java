package ru.spbau.mit.vishnyakov;

import java.io.*;
import java.util.*;


/*
 * Class that implements trie.
 */

public class Trie implements Serializable {
    private static class TrieNode implements Serializable {

        /**
         * Character that lies in current node.
         */

        private final Character character;

        /**
         * Map from current node to all children nodes.
         */

        private Map<Character, TrieNode> mapChild;

        /**
         * Flag that signals, if there is some word that ends in a current node.
         */

        private boolean isWord;

        /**
         * Denotes number of words that go through the current node.
         */

        private int howManyWords;

        /**
         * Constructor that receives a character.
         */

        public TrieNode(Character c) {
            character = c;
            mapChild = null;
            isWord = false;
            howManyWords = 0;
        }

        public boolean isWord() {
            return isWord;
        }

        public void setWord() {
            isWord = true;
        }

        public void unsetWord() {
            isWord = false;
        }

        public void incrementHowManyWords() {
            howManyWords++;
        }

        public void decrementHowManyWords() {
            howManyWords--;
        }

        public int getHowManyWords() {
            return howManyWords;
        }

        public TrieNode get(char c) {
            if (mapChild == null) {
                return null;
            }
            return mapChild.get(c);
        }

        public TrieNode goDown(char c) {
            if (mapChild == null || mapChild.get(c) == null) {
                return null;
            }
            return mapChild.get(c);
        }

        /**
         * Method that goes down and if necessary creates node with current symbol.
         * Also, if necessary, increments counter of number of words that goes through current node.
         * @param c character that we either want to go through or create.
         * @return node that lies deeper than the current node and its symbol is equal to c.
         */
        public TrieNode getOrCreateChild(char c) {
            if (mapChild == null) {
                mapChild = new HashMap<>();
            }
            this.incrementHowManyWords();
            TrieNode cur = mapChild.get(c);

            if (cur == null) {
                cur = new TrieNode(c);
                mapChild.put(c, cur);
            }

            return cur;
        }

        public Map<Character, TrieNode> getMap() {
            return mapChild;
        }
    }

    /**
     * Root of a trie.
     */

    private TrieNode root;

    /**
     * Constructor, symbol of root is '0'.
     */

    public Trie() {
        root = new TrieNode((char) 0);
    }

    /**
     * Method which returns sizel.
     * @return size of a trie.
     */

    public int size() {
        return root.howManyWords;
    }

    /**
     * Adds string to a trie via iterative traversal from top to bottom.
     * @param element - string that we want to add to a trie.
     * @return true, if trie contains a given string, otherwise false.
     */
    public boolean add(String element) {
        if (element == null) {
            throw new IllegalArgumentException("Please pass the correct string");
        }
        if (contains(element)) {
            return false;
        }
        TrieNode node = root;
        for (char c : element.toCharArray()) {
            node = node.getOrCreateChild(c);
        }
        boolean contains = true;
        if (!node.isWord()) {
            contains = false;
            node.setWord();
        }
        return !contains;
    }

    /**
     * Checks whether trie contains a string.
     * We simply go from root to bottom and then check if the last node isWord variable is true..
     * @param element that we want to check.
     * @return true if trie contains given string, otherwise false.
     */

    public boolean contains(String element) {
        TrieNode node = root;
        for (char c : element.toCharArray()) {
            node = node.get(c);
            if (node == null) {
                return false;
            }
        }
        return node.isWord();
    }

    /**
     * Calculates number of strings that starts with given prefix.
     * It goes down from root to some other node.
     * If after some move node is equal to null we return 0.
     * Otherwise we reach last letter of a prefix and we have to sum over all its children + isWord == true.
     * @param prefix that we want to check.
     * @return number of strings that starts with given prefix.
     */

    public int howManyStartsWithPrefix(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            node = node.goDown(c);
            if (node == null) {
                return 0;
            }
        }
        int cnt = node.getHowManyWords();
        if (node.isWord()) {
            cnt++;
        }
        return cnt;
    }

    /**
     * Removes string from a trie.
     * Decreases size of a trie, goes down from root,
     * if in one moment through our node goes only one word, we have to delete this node,
     * since this word is the target word, since we've check if our trie contains this word
     * in the beginning.
     * @param e we want to remove
     * @return true if string was in trie, otherwise false.
     */
    public boolean remove(String e) {
        Character last = e.charAt(e.length() - 1);
        String element = e.substring(0, e.length() - 1);
        if (!contains(e)) {
            return false;
        }
        TrieNode node = root;
        for (char c : element.toCharArray()) {
            node.decrementHowManyWords();
            node = node.goDown(c);
        }
        node.decrementHowManyWords();
        TrieNode lastNode = node.goDown(last);
        if (lastNode.mapChild == null || lastNode.mapChild.size() == 0) {
            node.getMap().remove(last);
        }
        else {
            lastNode.unsetWord();
        }
        return true;
    }

    /**
     * Serializes our trie.
     * We start from the root and serialize node by node via dfs.
     * @param out stream where we want to write.
     */
    public void serialize(OutputStream out) throws IOException{
        try {
            ObjectOutputStream outStream = new ObjectOutputStream(out);
            Stack<TrieNode> st = new Stack<>();
            st.push(root);
            while (!st.empty()) {
                TrieNode cur = st.peek();
                st.pop();
                outStream.writeObject(cur);
                if (cur != null && cur.getMap() != null) {
                    for (Character a : cur.getMap().keySet()) {
                        st.push(cur.getMap().get(a));
                    }
                }
            }

        } catch(IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Deserializes our trie via dfs.
     * @param in stream from where we want to read.
     */
    public void deserialize(InputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream inStream = new ObjectInputStream(in);
        root = (TrieNode) inStream.readObject();
        Stack<TrieNode> st = new Stack<>();
        st.push(root);
        while (!st.empty()) {
            TrieNode cur = st.peek();
            st.pop();
            if (cur != null && cur.getMap() != null) {
                List<Character> l = new ArrayList<Character>(cur.getMap().keySet());
                Collections.reverse(l);
                for (Character a : l) {
                    TrieNode node = (TrieNode) inStream.readObject();
                    st.push(node);
                }
            }
        }
    }
}
