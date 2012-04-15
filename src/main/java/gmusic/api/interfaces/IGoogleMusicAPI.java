/*******************************************************************************
 * Copyright (c) 2012 Jens Kristian Villadsen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Jens Kristian Villadsen - initial API and implementation
 ******************************************************************************/
package gmusic.api.interfaces;

import gmusic.api.model.AddPlaylist;
import gmusic.api.model.DeletePlaylist;
import gmusic.api.model.Playlist;
import gmusic.api.model.Playlists;
import gmusic.api.model.Song;
import gmusic.api.model.SongUrl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Collection;

import javax.naming.directory.InvalidAttributesException;

import org.apache.http.client.ClientProtocolException;

/**
 * http://readthedocs.org/docs/unofficial-google-music-api/en/latest/
 * 
 * https://github.com/simon-weber/Unofficial-Google-Music-API
 * 
 * @author JKidd
 */
public interface IGoogleMusicAPI
{
	void login(String email, String password) throws ClientProtocolException, IOException, URISyntaxException;
	
	Collection<Song> getAllSongs() throws ClientProtocolException, IOException, URISyntaxException;

	AddPlaylist addPlaylist(String playlistName) throws Exception;

	Playlists getAllPlaylists() throws ClientProtocolException, IOException, URISyntaxException;

	Playlist getPlaylist(String plID) throws ClientProtocolException, IOException, URISyntaxException;

	SongUrl getSongURL(String id) throws URISyntaxException, ClientProtocolException, IOException;
	
	SongUrl getSongURL(Song song) throws URISyntaxException, ClientProtocolException, IOException;

	DeletePlaylist deletePlaylist(String id) throws Exception;
	
	Collection<File> downloadSongs(Collection<Song> songs) throws MalformedURLException, ClientProtocolException, IOException, URISyntaxException, InvalidAttributesException;

	File downloadSong(Song song) throws MalformedURLException, ClientProtocolException, IOException, URISyntaxException, InvalidAttributesException;
}
