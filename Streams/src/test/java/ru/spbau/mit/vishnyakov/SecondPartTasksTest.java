package ru.spbau.mit.vishnyakov;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;

public class SecondPartTasksTest {

    @Test
    public void testFindQuotes() throws IOException {
        List<String> expected = Arrays.asList("Kappa", "4HeadKappaEleGiggle", "Kappa", "I like to spam Kappa in chat.", "Have you ever seen a Golden Kappa?");
        List<String> files = Arrays.asList("src/test/resources/first.txt", "src/test/resources/second.txt", "src/test/resources/third.txt");
        assertEquals(expected, SecondPartTasks.findQuotes(files, "Kappa"));
    }

    @Test
    public void testPiDividedBy4() {
        assertEquals(Math.PI / 4, SecondPartTasks.piDividedBy4(), 0.01);
    }

    @Test
    public void testFindPrinter() {
        Map<String, List<String>> map = new HashMap<>();
        assertEquals(null, SecondPartTasks.findPrinter(map));

        map.put("Leo Tolstoy", Arrays.asList("Chidhood", "Boyhood", "Youth", "The Cossacks", "War and Peace", "Anna Karenina", "Resurrection", "A Confession"));
        map.put("Aleksandr Pushkin", Arrays.asList("The Captain's Daughter", "Dubrovsky", "Eugene Onegin", "The Belkin Tales"));
        map.put("Kino", Arrays.asList("Gruppa Krovi", "Posledniy Geroi", "Zvezda Po Imeni Solnce", "Pachka Sigaret", "Hochu Peremen"));
        map.put("Kendrick Lamar", Arrays.asList("Humble", "Feel", "Loyalty", "Mortal Man", "Compton", "The Art Of Peer Pressure"));
        map.put("NWA", Arrays.asList("Straight Outta Compton", "Appetite For Destruction", "If it Ain't Ruff"));
        assertEquals("Leo Tolstoy", SecondPartTasks.findPrinter(map));
    }

    @Test
    public void testCalculateGlobalOrder() {
        List<Map<String, Integer>> list = new ArrayList<>();
        Map<String, Integer> first = ImmutableMap.of("Pepsi", 100, "Coca-Cola", 239, "Sprite", 80);
        Map<String, Integer> second = ImmutableMap.of("Pepsi", 1, "Coca-Cola", 71, "Sprite", 2, "Fanta", 90);
        Map<String, Integer> expected = ImmutableMap.of("Pepsi", 101, "Coca-Cola", 310, "Sprite", 82, "Fanta", 90);
        list.add(first);
        list.add(second);
        assertEquals(expected, SecondPartTasks.calculateGlobalOrder(list));
    }
}
