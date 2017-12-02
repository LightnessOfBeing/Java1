package ru.spbau.mit.vishnyakov;

import org.jetbrains.annotations.NotNull;

public class Artist implements Comparable<Artist> {

    private final String name;

    public Artist(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(@NotNull Artist artist) {
        return getName().compareTo(artist.getName());
    }
}