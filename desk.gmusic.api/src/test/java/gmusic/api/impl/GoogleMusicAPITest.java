package gmusic.api.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;
import gmusic.api.comm.FormBuilder;
import gmusic.api.comm.JSON;
import gmusic.api.interfaces.IGoogleHttpClient;
import gmusic.api.model.Song;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

public class GoogleMusicAPITest
{
    @Mock
    private IGoogleHttpClient mockHttpClient;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getSongsReturnsSongs() throws IOException, URISyntaxException
    {
        // given
        final int expectedSongListSize = 3;

        final String expectedSong1Title = "Song Title 1";
        final String expectedSong1Artist = "Artist 1";
        final String expectedSong1Album = "Album 1";
        final int expectedSong1Year = 2012;

        final String expectedSong2Title = "Song Title 2";
        final String expectedSong2Artist = "Artist 2";
        final String expectedSong2Album = "Album 2";
        final int expectedSong2Year = 2011;

        final String testData = Files.asCharSource(
                new File("src/test/resources/loadAllTracksResponse.html"),
                Charsets.UTF_8).read();
        when(
                mockHttpClient.dispatchPost(isA(URI.class),
                        isA(FormBuilder.class))).thenReturn(testData);

        final GoogleMusicAPI api = new GoogleMusicAPI(mockHttpClient, new JSON(),
                new File("."));

        // when
        final Collection<Song> songs = api.getAllSongs();

        // then
        assertEquals(expectedSongListSize, songs.size());

        final List<Song> songList = Lists.newArrayList(songs.iterator());

        final Song song1 = songList.get(0);
        assertEquals(expectedSong1Title, song1.getTitle());
        assertEquals(expectedSong1Artist, song1.getArtist());
        assertEquals(expectedSong1Album, song1.getAlbum());
        assertEquals(expectedSong1Year, song1.getYear());

        final Song song2 = songList.get(1);
        assertEquals(expectedSong2Title, song2.getTitle());
        assertEquals(expectedSong2Artist, song2.getArtist());
        assertEquals(expectedSong2Album, song2.getAlbum());
        assertEquals(expectedSong2Year, song2.getYear());
    }

    @Test
    public void getSongsParsesSongWithCommas() throws IOException,
            URISyntaxException
    {
        // given
        final int expectedSongListSize = 3;

        final String expectedSong3Title = "Song Title, With Comma";
        final String expectedSong3Artist = "Artist 2";
        final String expectedSong3Album = "Album 2";
        final int expectedSong3Year = 2014;

        final String testData = Files.asCharSource(
                new File("src/test/resources/loadAllTracksResponse.html"),
                Charsets.UTF_8).read();
        when(
                mockHttpClient.dispatchPost(isA(URI.class),
                        isA(FormBuilder.class))).thenReturn(testData);

        final GoogleMusicAPI api = new GoogleMusicAPI(mockHttpClient, new JSON(),
                new File("."));

        // when
        final Collection<Song> songs = api.getAllSongs();

        // then
        assertEquals(expectedSongListSize, songs.size());

        final Song song = Lists.newArrayList(songs.iterator()).get(2);
        assertEquals(expectedSong3Title, song.getTitle());
        assertEquals(expectedSong3Artist, song.getArtist());
        assertEquals(expectedSong3Album, song.getAlbum());
        assertEquals(expectedSong3Year, song.getYear());
    }

    @Test
    public void getSongsParsesResponseWithMultipleJSArrays()
            throws IOException, URISyntaxException
    {
        // given
        final int expectedSongListSize = 6;

        final String expectedSong3Title = "Song Title, With Comma";
        final String expectedSong3Artist = "Artist 2";
        final String expectedSong3Album = "Album 2";
        final int expectedSong3Year = 2014;

        final String expectedSong5Title = "Song Title 5";
        final String expectedSong5Artist = "Artist 5";
        final String expectedSong5Album = "Album 5";
        final int expectedSong5Year = 2011;

        final String testData = Files
                .asCharSource(
                        new File(
                                "src/test/resources/loadAllTracksResponseWithMultipleJSArrays.html"),
                        Charsets.UTF_8).read();
        when(
                mockHttpClient.dispatchPost(isA(URI.class),
                        isA(FormBuilder.class))).thenReturn(testData);

        final GoogleMusicAPI api = new GoogleMusicAPI(mockHttpClient, new JSON(),
                new File("."));

        // when
        final Collection<Song> songs = api.getAllSongs();

        // then
        assertEquals(expectedSongListSize, songs.size());

        final List<Song> songList = Lists.newArrayList(songs.iterator());

        final Song song3 = songList.get(2);
        assertEquals(expectedSong3Title, song3.getTitle());
        assertEquals(expectedSong3Artist, song3.getArtist());
        assertEquals(expectedSong3Album, song3.getAlbum());
        assertEquals(expectedSong3Year, song3.getYear());

        final Song song5 = songList.get(4);
        assertEquals(expectedSong5Title, song5.getTitle());
        assertEquals(expectedSong5Artist, song5.getArtist());
        assertEquals(expectedSong5Album, song5.getAlbum());
        assertEquals(expectedSong5Year, song5.getYear());
    }

}
