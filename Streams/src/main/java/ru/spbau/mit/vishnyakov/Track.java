package ru.spbau.mit.vishnyakov;

import org.jetbrains.annotations.NotNull;

public class Track implements Comparable<Track> {

    private final String name;
    private final int rating;

    public Track(String name, int rating) {
        this.name = name;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }


    @Override
    public int compareTo(@NotNull Track track) {
        Integer i1 = getRating();
        return i1.compareTo(track.getRating());
    }
}