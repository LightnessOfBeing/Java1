package ru.spbau.mit.vishnyakov;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class SecondPartTasks {

    private SecondPartTasks() {}

    // Найти строки из переданных файлов, в которых встречается указанная подстрока.
    public static List<String> findQuotes(@NotNull List<String> paths, @NotNull CharSequence sequence) throws IOException {
        return paths.stream().map(Paths::get).flatMap(path -> {
            try {
                return Files.lines(path);
            } catch (IOException e) {
                e.printStackTrace();
                return Stream.empty();
            }
        }).filter(p -> p.contains(sequence)).collect(Collectors.toList());
    }

    // В квадрат с длиной стороны 1 вписана мишень.
    // Стрелок атакует мишень и каждый раз попадает в произвольную точку квадрата.
    // Надо промоделировать этот процесс с помощью класса java.util.Random и посчитать, какова вероятность попасть в мишень.
    public static double piDividedBy4() {
        int lim = 100000;
        Random r = new Random();
        Stream<Point> stream = Stream.generate(() -> new Point(r.nextDouble(), r.nextDouble())).limit(lim);
        return stream.filter((p) -> p.getX() * p.getX() + p.getY() * p.getY() <= 1).count() / (double) lim;
    }

    // Дано отображение из имени автора в список с содержанием его произведений.
    // Надо вычислить, чья общая длина произведений наибольшая.
    @SuppressWarnings("ConstantConditions")
    public static String findPrinter(@NotNull Map<String, List<String>> compositions) {
        if (compositions.isEmpty()) {
            return null;
        }
        return compositions.entrySet().stream().max(Comparator.comparing(p -> p.getValue().stream().mapToInt(String::length).sum())).get().getKey();
    }

    // Вы крупный поставщик продуктов. Каждая торговая сеть делает вам заказ в виде Map<Товар, Количество>.
    // Необходимо вычислить, какой товар и в каком количестве надо поставить.
    public static Map<String, Integer> calculateGlobalOrder(@NotNull List<Map<String, Integer>> orders) {
        return orders.stream().map(Map::entrySet).flatMap(Collection::stream).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a + b));
    }

    /**
     * Class that represents a 2D Point.
     */

    private static class Point {
        /**
         * X coordinate.
         */

        private double x;

        /**
         * Y coordinate.
         */

        private double y;

        /**
         * Constructor that receives a point.
         * @param f -- x coordinate.
         * @param s -- y coordinate.
         */

        private Point(double f, double s) {
            x = f;
            y = s;
        }

        private double getX() {
            return x;
        }

        private double getY() {
            return y;
        }
    }
}