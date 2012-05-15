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
import gmusic.api.comm.ApacheConnector;
import gmusic.api.comm.HttpUrlConnector;
import gmusic.api.comm.JSON;
import gmusic.api.interfaces.IGoogleHttp;
import gmusic.api.interfaces.IGoogleMusicAPI;
import gmusic.api.model.AddPlaylist;
import gmusic.api.model.DeletePlaylist;
import gmusic.api.model.Playlist;
import gmusic.api.model.Playlists;
import gmusic.api.model.Song;
import gmusic.api.model.SongUrl;
import gmusic.model.Tune;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.ClientProtocolException;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.id3.ID3v24Tag;

import com.google.common.base.Strings;

public class GoogleMusicAPI implements IGoogleMusicAPI
{
	public static enum CONNECTION_TYPE
	{
		APACHE, HTTP_URL;
	}
	protected IGoogleHttp client;

	public GoogleMusicAPI()
	{
		this(CONNECTION_TYPE.APACHE);
	}

	public GoogleMusicAPI(CONNECTION_TYPE type)
	{
		switch(type)
		{
			case APACHE:
				client = new ApacheConnector();
				break;
			case HTTP_URL:
				client = new HttpUrlConnector();
				break;
			default:
				throw new IllegalArgumentException("Invalid CONNECTION_TYPE");
		}

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
		return getSongs("");
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

	protected final URI getTuneURL(Tune tune) throws URISyntaxException, ClientProtocolException, IOException
	{
		return new URI(JSON.Deserialize(client.dispatchGet(new URI(String.format("https://play.google.com/music/play?u=0&songid=%1$s&pt=e", tune.getId()))), SongUrl.class).getUrl());
	}

	@Override
	public URI getSongURL(Song song) throws URISyntaxException, ClientProtocolException, IOException
	{
		return getTuneURL(song);
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

	private final Collection<Song> getSongs(String continuationToken) throws ClientProtocolException, IOException, URISyntaxException
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
			chunkedCollection.addAll(getSongs(chunk.getContinuationToken()));
		}
		return chunkedCollection;
	}

	@Override
	public Collection<File> downloadSongs(Collection<Song> songs) throws MalformedURLException, ClientProtocolException, IOException, URISyntaxException
	{
		Collection<File> files = new ArrayList<File>();
		for(Song song : songs)
		{
			files.add(downloadSong(song));
		}
		return files;
	}

	@Override
	public File downloadSong(Song song) throws MalformedURLException, ClientProtocolException, IOException, URISyntaxException
	{
		return downloadTune(song);
	}

	protected File downloadTune(Tune tune) throws MalformedURLException, ClientProtocolException, IOException, URISyntaxException
	{
		File file = new File(tune.getId() + ".mp3");
		if(!file.exists())
		{
			FileUtils.copyURLToFile(getTuneURL(tune).toURL(), file);

			populateFileWithTuneTags(file, tune);
		}
		return file;
	}

	private void populateFileWithTuneTags(File file, Tune tune) throws IOException
	{
		try
		{
			AudioFile f = AudioFileIO.read(file);
			Tag tag = f.getTag();
			if(tag == null)
			{
				tag = new ID3v24Tag();
			}
			tag.setField(FieldKey.ARTIST, tune.getAlbumArtist());
			tag.setField(FieldKey.ALBUM, tune.getAlbum());
			tag.setField(FieldKey.TITLE, tune.getTitle());
			f.setTag(tag);
			AudioFileIO.write(f);
		}
		catch(Exception e)
		{
			throw new IOException(e);
		}
	}
}
