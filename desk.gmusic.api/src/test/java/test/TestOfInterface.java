package test;

import gmusic.api.impl.GoogleMusicAPI;
import gmusic.api.impl.InvalidCredentialsException;
import gmusic.api.interfaces.IGoogleMusicAPI;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.naming.directory.InvalidAttributesException;

import org.junit.Before;
import org.junit.Test;

public class TestOfInterface
{

    private static final String username = "";
    private static final String password = "";
    private IGoogleMusicAPI api;

    @Before
    public void before()
    {
        api = new GoogleMusicAPI();
    }

    @Test
    public void testLogin() throws IOException, URISyntaxException,
            InvalidCredentialsException
    {
        api.login(username, password);
        api.getAllSongs();
    }

    @Test
    public void testGetAllSongs() throws IOException, URISyntaxException,
            InvalidCredentialsException
    {
        api.login(username, password);
        api.getAllSongs();
    }

    @Test
    public void testGetAllPlaylists() throws IOException, URISyntaxException,
            InvalidCredentialsException
    {
        api.login(username, password);
        api.getAllPlaylists();
    }

    @Test
    public void testGetSongUrl() throws IOException, URISyntaxException,
            InvalidCredentialsException
    {
        api.login(username, password);
        api.getSongURL(api.getAllSongs().iterator().next());
    }

    @Test
    public void testDownloadSong() throws IOException, URISyntaxException,
            InvalidCredentialsException, InvalidAttributesException
    {
        api.login(username, password);
        api.downloadSong(api.getAllSongs().iterator().next());
    }
}
