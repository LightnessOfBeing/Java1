package ru.spbau.mit.vishnyakov;

//import javax.sound.midi.Track;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class Album implements Comparable<Album> {

    private final String name;
    @NotNull
    private final List<Track> tracks;
    private final Artist artist;

    public Album(Artist artist, String name, Track... tracks) {
        this.name = name;
        this.tracks = Arrays.asList(tracks);
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    @NotNull
    public List<Track> getTracks() {
        return tracks;
    }

    public Artist getArtist() {
        return artist;
    }

    @Override
    public int compareTo(@NotNull Album album) {
        return getArtist().compareTo(album.getArtist());
    }
}