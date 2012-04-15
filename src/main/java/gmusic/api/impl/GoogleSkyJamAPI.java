package gmusic.api.impl;

import gmusic.api.comm.JSON;
import gmusic.api.model.Song;
import gmusic.api.skyjam.interfaces.IGoogleSkyJam;
import gmusic.api.skyjam.model.Playlists;
import gmusic.api.skyjam.model.Track;
import gmusic.api.skyjam.model.TrackFeed;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.http.client.ClientProtocolException;

import com.google.common.base.Strings;

public class GoogleSkyJamAPI extends GoogleMusicAPI implements IGoogleSkyJam
{
	public GoogleSkyJamAPI(String androidDeviceId)
	{
		super(androidDeviceId);
	}

	@Override
	public Collection<Track> getAllTracks() throws ClientProtocolException, IOException, URISyntaxException
	{
		final Collection<Track> chunkedCollection = new ArrayList<Track>();
		final TrackFeed chunk = JSON.Deserialize(client.dispatchGet(new URI("https://www.googleapis.com/sj/v1beta1/tracks")), TrackFeed.class);
		chunkedCollection.addAll(chunk.getData().getItems());
		chunkedCollection.addAll(getTracks(chunk.getNextPageToken()));
		return chunkedCollection;
	}

	private final Collection<Track> getTracks(String continuationToken) throws ClientProtocolException, IOException, URISyntaxException
	{
		Collection<Track> chunkedCollection = new ArrayList<Track>();

		TrackFeed chunk = JSON.Deserialize(client.dispatchPost(new URI("https://www.googleapis.com/sj/v1beta1/trackfeed"), "{\"start-token\":\"" + continuationToken + "\"}"), TrackFeed.class);
		chunkedCollection.addAll(chunk.getData().getItems());

		if(!Strings.isNullOrEmpty(chunk.getNextPageToken()))
		{
			chunkedCollection.addAll(getTracks(chunk.getNextPageToken()));
		}
		return chunkedCollection;
	}

	@Override
	public Playlists getAllSkyJamPlaylists() throws ClientProtocolException, IOException, URISyntaxException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TrackFeed getSkyJamPlaylist(String plID) throws ClientProtocolException, IOException, URISyntaxException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<File> downloadTracks(Collection<Track> tracks) throws URISyntaxException, ClientProtocolException, IOException
	{
		Collection<File> files = new ArrayList<File>();
		for(Track track : tracks)
		{
			files.add(downloadTrack(track));
		}
		return files;
	}

	@Override
	public File downloadTrack(Track track) throws URISyntaxException, ClientProtocolException, IOException
	{
		Song s = new Song();
		s.setName(track.getTitle());
		s.setId(track.getId());
		return downloadSong(s);

		// if(androidDeviceId == null)
		// {
		// throw new InvalidAttributesException("Android Device ID not specified in constructor");
		// }
		// method.setQueryString(new NameValuePair[] {
		// new NameValuePair("key", "value")
		// });
		// client.dispatchGet(new URI("https://android.clients.google.com/music/mplay?" + track.getId()));
	}
}
