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
import gmusic.api.comm.HttpUrlConnector;
import gmusic.api.comm.JSON;
import gmusic.api.interfaces.IGoogleHttpClient;
import gmusic.api.interfaces.IGoogleMusicAPI;
import gmusic.api.interfaces.IJsonDeserializer;
import gmusic.api.model.AddPlaylist;
import gmusic.api.model.DeletePlaylist;
import gmusic.api.model.Playlist;
import gmusic.api.model.Playlists;
import gmusic.api.model.QueryResponse;
import gmusic.api.model.Song;
import gmusic.api.model.SongUrl;
import gmusic.model.Tune;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Strings;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class GoogleMusicAPI implements IGoogleMusicAPI
{
	protected final IGoogleHttpClient client;
	protected final IJsonDeserializer deserializer;
	protected final File storageDirectory;

	public GoogleMusicAPI()
	{
		this(new HttpUrlConnector(), new JSON(), new File("."));
	}

	public GoogleMusicAPI(IGoogleHttpClient httpClient, IJsonDeserializer jsonDeserializer, File file)
	{
		client = httpClient;
		deserializer = jsonDeserializer;
		storageDirectory = file;
	}

	@Override
	public final void login(String email, String password) throws IOException, URISyntaxException, InvalidCredentialsException
	{
		Map<String, String> fields = new HashMap<String, String>();
		fields.put("service", "sj");
		fields.put("Email", email);
		fields.put("Passwd", password);

		FormBuilder form = new FormBuilder();
		form.addFields(fields);
		form.close();

		try
		{
			client.dispatchPost(new URI(HTTPS_WWW_GOOGLE_COM_ACCOUNTS_CLIENT_LOGIN), form);
		}
		catch(IllegalStateException ise)
		{
			throw new InvalidCredentialsException(ise, "Provided credentials: '" + email + "' and '" + password + "' where insufficient");
		}
	}

	@Override
	public final Collection<Song> getAllSongs() throws IOException, URISyntaxException
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

		return deserializer.deserialize(client.dispatchPost(new URI(HTTPS_PLAY_GOOGLE_COM_MUSIC_SERVICES_ADDPLAYLIST), form), AddPlaylist.class);
	}

	@Override
	public final Playlists getAllPlaylists() throws IOException, URISyntaxException
	{
		return deserializer.deserialize(getPlaylistAssist("{}"), Playlists.class);
	}

	@Override
	public final Playlist getPlaylist(String plID) throws IOException, URISyntaxException
	{
		return deserializer.deserialize(getPlaylistAssist("{\"id\":\"" + plID + "\"}"), Playlist.class);
	}

	protected final URI getTuneURL(Tune tune) throws URISyntaxException, IOException
	{
		return new URI(deserializer.deserialize(client.dispatchGet(new URI(String.format(HTTPS_PLAY_GOOGLE_COM_MUSIC_PLAY_SONGID, tune.getId()))), SongUrl.class).getUrl());
	}

	@Override
	public URI getSongURL(Song song) throws URISyntaxException, IOException
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

		return deserializer.deserialize(client.dispatchPost(new URI(HTTPS_PLAY_GOOGLE_COM_MUSIC_SERVICES_DELETEPLAYLIST), form), DeletePlaylist.class);
	}

	private final String getPlaylistAssist(String jsonString) throws IOException, URISyntaxException
	{
		Map<String, String> fields = new HashMap<String, String>();
		fields.put("json", jsonString);

		FormBuilder builder = new FormBuilder();
		builder.addFields(fields);
		builder.close();
		return client.dispatchPost(new URI(HTTPS_PLAY_GOOGLE_COM_MUSIC_SERVICES_LOADPLAYLIST), builder);
	}

	private final Collection<Song> getSongs(String continuationToken) throws IOException, URISyntaxException
	{
		Collection<Song> chunkedCollection = new ArrayList<Song>();

		// Map<String, String> fields = new HashMap<String, String>();
		// fields.put("json", "{\"continuationToken\":\"" + continuationToken + "\"}");

		FormBuilder form = new FormBuilder();
		// form.addFields(fields);
		form.close();

		String response = client.dispatchPost(new URI(HTTPS_PLAY_GOOGLE_COM_MUSIC_SERVICES_LOADALLTRACKS), form);

		// extract the JSON from the response
		List<String> jsSongCollectionWrappers = getJsSongCollectionWrappers(response);

		Gson gson = new Gson();
		JsonParser parser = new JsonParser();

		for (String songCollectionWrapperJson : jsSongCollectionWrappers)
		{
			JsonArray songCollectionWrapper = parser.parse(new StringReader(songCollectionWrapperJson)).getAsJsonArray();

			// the song collection is the first element of the "wrapper"
			JsonArray songCollection = songCollectionWrapper.get(0).getAsJsonArray();

			// each element of the songCollection is an array of song values
			for (JsonElement songValues : songCollection)
			{
				// retrieve the songValues as an Array for parsing to a Song object
				JsonArray values = songValues.getAsJsonArray();

				Song s = new Song();
				s.setId(gson.fromJson(values.get(0), String.class));
				s.setTitle(gson.fromJson(values.get(1), String.class));
				s.setName(gson.fromJson(values.get(1), String.class));
				if (!Strings.isNullOrEmpty(gson.fromJson(values.get(2), String.class)))
				{
					s.setAlbumArtUrl("https:" + gson.fromJson(values.get(2), String.class));
				}
				s.setArtist(gson.fromJson(values.get(3), String.class));
				s.setAlbum(gson.fromJson(values.get(4), String.class));
				s.setAlbumArtist(gson.fromJson(values.get(5), String.class));
				s.setGenre(gson.fromJson(values.get(11), String.class));
				s.setDurationMillis(gson.fromJson(values.get(13), Long.class));
				s.setType(gson.fromJson(values.get(16), Integer.class));
				s.setYear(gson.fromJson(values.get(18), Integer.class));
				s.setPlaycount(gson.fromJson(values.get(22), Integer.class));
				s.setRating(gson.fromJson(values.get(23), String.class));
				if (!Strings.isNullOrEmpty(gson.fromJson(values.get(24), String.class)))
				{
					s.setCreationDate(gson.fromJson(values.get(24), Float.class) / 1000);
				}
				if (!Strings.isNullOrEmpty(gson.fromJson(values.get(36), String.class)))
				{
					s.setUrl("https:" + gson.fromJson(values.get(36), String.class));
				}

				chunkedCollection.add(s);
			}
		}

		return chunkedCollection;
	}

	/**
	 * Locate each "window.parent['slat_process']" statement and extract the JSON representing a song
	 * collection. For a provided response, there may be one or more separate song collections.
	 * 
	 * @param response
	 *           the HTML response from LOADALLTRACKS
	 * @return a "wrapper" JSON object with a list of song collection JSON objects as its first
	 *         element
	 */
	private List<String> getJsSongCollectionWrappers(String response)
	{
		List<String> songCollectionList = new ArrayList<String>();

		// locate the contents of: window.parent['slat_process']( );
		// where the song collection JSON is between the parentheses
		Pattern p = Pattern.compile("window.parent\\['slat_process'\\]\\((.*?)\\);", Pattern.DOTALL);
		Matcher m = p.matcher(response);
		while (m.find())
		{
			String songCollectionWrapperJson = m.group(1);
			songCollectionList.add(songCollectionWrapperJson);
		}

		return songCollectionList;
	}

	@Override
	public Collection<File> downloadSongs(Collection<Song> songs) throws MalformedURLException, IOException, URISyntaxException
	{
		Collection<File> files = new ArrayList<File>();
		for(Song song : songs)
		{
			files.add(downloadSong(song));
		}
		return files;
	}

	@Override
	public File downloadSong(Song song) throws MalformedURLException, IOException, URISyntaxException
	{
		return downloadTune(song);
	}

	@Override
	public QueryResponse search(String query) throws IOException, URISyntaxException
	{
		if(Strings.isNullOrEmpty(query))
		{
			throw new IllegalArgumentException("query is null or empty");
		}

		Map<String, String> fields = new HashMap<String, String>();
		fields.put("json", "{\"q\":\"" + query + "\"}");

		FormBuilder form = new FormBuilder();
		form.addFields(fields);
		form.close();

		String response = client.dispatchPost(new URI(HTTPS_PLAY_GOOGLE_COM_MUSIC_SERVICES_SEARCH), form);

		return deserializer.deserialize(response, QueryResponse.class);
	}

	protected File downloadTune(Tune song) throws MalformedURLException, IOException, URISyntaxException
	{
		File file = new File(storageDirectory.getAbsolutePath() + System.getProperty("path.separator") + song.getId() + ".mp3");
		if(!file.exists())
		{
			Files.write(Resources.toByteArray(getTuneURL(song).toURL()), file);
		}
		return file;
	}

	@Override
	public void uploadSong(File song)
	{

	}
}
