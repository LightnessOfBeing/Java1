package ru.spbau.mit.vishnyakov;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class FirstPartTasks {

    private FirstPartTasks() {}

    // Список названий альбомов
    public static List<String> allNames(@NotNull Stream<Album> albums) {
        return albums.map(Album::getName).collect(Collectors.toList());
    }

    // Список названий альбомов, отсортированный лексикографически по названию
    public static List<String> allNamesSorted(@NotNull Stream<Album> albums) {
        return albums.map(Album::getName).sorted(String::compareTo).collect(Collectors.toList());
    }

    // Список треков, отсортированный лексикографически по названию, включающий все треки альбомов из 'albums'
    public static List<String> allTracksSorted(@NotNull Stream<Album> albums) {
        return albums.flatMap(p -> p.getTracks().stream().map(Track::getName)).sorted().collect(Collectors.toList());
    }

    // Список альбомов, в которых есть хотя бы один трек с рейтингом более 95, отсортированный по названию
    public static List<Album> sortedFavorites(@NotNull Stream<Album> s) {
        return s.filter(p -> p.getTracks().stream().anyMatch(l -> l.getRating() > 95)).sorted(Comparator.comparing(Album::getName)).collect(Collectors.toList());
    }

    // Сгруппировать альбомы по артистам
    public static Map<Artist, List<Album>> groupByArtist(@NotNull Stream<Album> albums) {
        return albums.collect(Collectors.groupingBy(Album::getArtist));
    }

    // Сгруппировать альбомы по артистам (в качестве значения вместо объекта 'Artist' использовать его имя)
    public static Map<Artist, List<String>> groupByArtistMapName(@NotNull Stream<Album> albums) {
        return albums.collect(Collectors.groupingBy(Album::getArtist,  Collectors.mapping(Album::getName, Collectors.toList())));
    }

    // Число повторяющихся альбомов в потоке
    public static long countAlbumDuplicates(@NotNull Stream<Album> albums) {
        Set<Album> allItems = new HashSet<>();
        return albums.filter(p -> !allItems.add(p)).count();
    }

    // Альбом, в котором максимум рейтинга минимален
    // (если в альбоме нет ни одного трека, считать, что максимум рейтинга в нем --- 0)
    public static Optional<Album> minMaxRating(@NotNull Stream<Album> albums) {
        return albums.min(Comparator.comparing(p -> p.getTracks().stream().mapToInt(Track::getRating).max().orElse(0)));
    }

    // Список альбомов, отсортированный по убыванию среднего рейтинга его треков (0, если треков нет)
    public static List<Album> sortByAverageRating(@NotNull Stream<Album> albums) {
        return albums.sorted(Comparator.comparing(p -> -p.getTracks().stream().mapToInt(Track::getRating).average().orElse(0))).collect(Collectors.toList());
    }

    // Произведение всех чисел потока по модулю 'modulo'
    // (все числа от 0 до 10000)
    public static int moduloProduction(@NotNull IntStream stream, int modulo) {
        return stream.reduce(1, (p1, p2) -> p1 * p2 % modulo);
    }

    // Вернуть строку, состояющую из конкатенаций переданного массива, и окруженную строками "<", ">"
    // см. тесты
    public static String joinTo(@NotNull String... strings) {
        return Arrays.stream(strings).collect(Collectors.joining(", ","<", ">"));
    }

    // Вернуть поток из объектов класса 'clazz'
    public static <R> Stream<R> filterIsInstance(@NotNull Stream<?> s, @NotNull Class<R> clazz) {
        return s.filter(clazz::isInstance).map(clazz::cast);
    }
}