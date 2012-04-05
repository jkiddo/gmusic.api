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
package gmusic.api.impl;

import gmusic.api.comm.FormBuilder;
import gmusic.api.comm.GoogleHttp;
import gmusic.api.comm.JSON;
import gmusic.api.interfaces.IGoogleHttp;
import gmusic.api.interfaces.IGoogleMusicAPI;
import gmusic.api.model.AddPlaylist;
import gmusic.api.model.DeletePlaylist;
import gmusic.api.model.Playlist;
import gmusic.api.model.Playlists;
import gmusic.api.model.Song;
import gmusic.api.model.SongUrl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.google.common.base.Strings;

public class GoogleMusicAPI implements IGoogleMusicAPI
{
	private final IGoogleHttp client;

	public GoogleMusicAPI()
	{
		client = new GoogleHttp();
	}

	@Override
	public final void login(String email, String password) throws ClientProtocolException, IOException, URISyntaxException
	{
		Map<String, String> fields = new HashMap<String, String>();
		fields.put("service", "sj");
		fields.put("Email", email);
		fields.put("Passwd", password);

		FormBuilder form = new FormBuilder();
		form.addFields(fields);
		form.close();

		client.dispatchPost(new URI("https://www.google.com/accounts/ClientLogin"), form);
	}

	@Override
	public final Collection<Song> getAllSongs() throws ClientProtocolException, IOException, URISyntaxException
	{
		return getAllSongs("");
	}

	@Override
	public final AddPlaylist addPlaylist(String playlistName) throws Exception
	{
		Map<String, String> fields = new HashMap<String, String>();
		fields.put("json", "{\"title\":\"" + playlistName + "\"}");

		FormBuilder form = new FormBuilder();
		form.addFields(fields);
		form.close();

		return JSON.Deserialize(client.dispatchPost(new URI("https://play.google.com/music/services/addplaylist"), form), AddPlaylist.class);
	}

	@Override
	public final Playlists getAllPlaylists() throws ClientProtocolException, IOException, URISyntaxException
	{
		return JSON.Deserialize(getPlaylistAssist("{}"), Playlists.class);
	}

	@Override
	public final Playlist getPlaylist(String plID) throws ClientProtocolException, IOException, URISyntaxException
	{
		return JSON.Deserialize(getPlaylistAssist("{\"id\":\"" + plID + "\"}"), Playlist.class);
	}

	@Override
	public final SongUrl getSongURL(String id) throws URISyntaxException, ClientProtocolException, IOException
	{
		return JSON.Deserialize(client.dispatchGet(new URI(String.format("https://play.google.com/music/play?u=0&songid=%1$s&pt=e", id))), SongUrl.class);
	}

	@Override
	public final DeletePlaylist deletePlaylist(String id) throws Exception
	{
		Map<String, String> fields = new HashMap<String, String>();
		fields.put("json", "{\"id\":\"" + id + "\"}");

		FormBuilder form = new FormBuilder();
		form.addFields(fields);
		form.close();

		return JSON.Deserialize(client.dispatchPost(new URI("https://play.google.com/music/services/deleteplaylist"), form), DeletePlaylist.class);
	}

	private final String getPlaylistAssist(String jsonString) throws ClientProtocolException, IOException, URISyntaxException
	{
		Map<String, String> fields = new HashMap<String, String>();
		fields.put("json", jsonString);

		FormBuilder builder = new FormBuilder();
		builder.addFields(fields);
		builder.close();

		return client.dispatchPost(new URI("https://play.google.com/music/services/loadplaylist"), builder);
	}

	private final Collection<Song> getAllSongs(String continuationToken) throws ClientProtocolException, IOException, URISyntaxException
	{
		Collection<Song> chunkedCollection = new ArrayList<Song>();

		Map<String, String> fields = new HashMap<String, String>();
		fields.put("json", "{\"continuationToken\":\"" + continuationToken + "\"}");

		FormBuilder form = new FormBuilder();
		form.addFields(fields);
		form.close();

		Playlist chunk = JSON.Deserialize(client.dispatchPost(new URI("https://play.google.com/music/services/loadalltracks"), form), Playlist.class);
		chunkedCollection.addAll(chunk.getPlaylist());

		if(!Strings.isNullOrEmpty(chunk.getContinuationToken()))
		{
			chunkedCollection.addAll(getAllSongs(chunk.getContinuationToken()));
		}
		return chunkedCollection;
	}
}
