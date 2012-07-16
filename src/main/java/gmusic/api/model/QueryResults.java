package gmusic.api.model;

import java.util.Collection;

/**
 * Query results
 */
public class QueryResults {

    private Collection<Song> artists;
    private Collection<Song> albums;
    private Collection<Song> songs;

    public Collection<Song> getArtists() {
        return artists;
    }

    public void setArtists(Collection<Song> artists) {
        this.artists = artists;
    }

    public Collection<Song> getAlbums() {
        return albums;
    }

    public void setAlbums(Collection<Song> albums) {
        this.albums = albums;
    }

    public Collection<Song> getSongs() {
        return songs;
    }

    public void setSongs(Collection<Song> songs) {
        this.songs = songs;
    }

}
