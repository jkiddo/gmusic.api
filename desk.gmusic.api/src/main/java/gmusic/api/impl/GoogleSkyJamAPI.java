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

import gmusic.api.interfaces.IGoogleHttpClient;
import gmusic.api.interfaces.IJsonDeserializer;
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

import com.google.common.base.Strings;

public class GoogleSkyJamAPI extends GoogleMusicAPI implements IGoogleSkyJam
{
    public GoogleSkyJamAPI()
    {
        super();
    }

    public GoogleSkyJamAPI(
        final IGoogleHttpClient httpClient,
        final IJsonDeserializer deserializer,
        final File file)
    {
        super(httpClient, deserializer, file);
    }

    @Override
    public Collection<Track> getAllTracks() throws IOException,
            URISyntaxException
    {
        final Collection<Track> chunkedCollection = new ArrayList<Track>();
        final TrackFeed chunk = deserializer.deserialize(
                client.dispatchGet(new URI(
                        HTTPS_WWW_GOOGLEAPIS_COM_SJ_V1BETA1_TRACKS)),
                TrackFeed.class);
        chunkedCollection.addAll(chunk.getData().getItems());
        chunkedCollection.addAll(getTracks(chunk.getNextPageToken()));
        return chunkedCollection;
    }

    private final Collection<Track> getTracks(final String continuationToken)
            throws IOException, URISyntaxException
    {
        final Collection<Track> chunkedCollection = new ArrayList<Track>();

        final TrackFeed chunk = deserializer.deserialize(
                client.dispatchPost(new URI(
                        HTTPS_WWW_GOOGLEAPIS_COM_SJ_V1BETA1_TRACKFEED),
                        "{\"start-token\":\"" + continuationToken + "\"}"),
                TrackFeed.class);
        chunkedCollection.addAll(chunk.getData().getItems());

        if (!Strings.isNullOrEmpty(chunk.getNextPageToken()))
        {
            chunkedCollection.addAll(getTracks(chunk.getNextPageToken()));
        }
        return chunkedCollection;
    }

    @Override
    public Collection<File> downloadTracks(final Collection<Track> tracks)
            throws URISyntaxException, IOException
    {
        final Collection<File> files = new ArrayList<File>();
        for (final Track track : tracks)
        {
            files.add(downloadTrack(track));
        }
        return files;
    }

    @Override
    public URI getTrackURL(final Track track) throws URISyntaxException,
            IOException
    {
        return getTuneURL(track);
    }

    @Override
    public File downloadTrack(final Track track) throws URISyntaxException,
            IOException
    {
        return downloadTune(track);
        /*
         * File file = new File(storageDirectory.getAbsolutePath() +
         * track.getId() + ".mp3"); if(!file.exists()) { ByteBuffer buffer =
         * Util.uriTobuffer(getTuneURL(track)); FileOutputStream fos = new
         * FileOutputStream(file); fos.write(buffer.array());
         * Closeables.close(fos, true); } return file;
         */

        // if(androidDeviceId == null)
        // {
        // throw new
        // InvalidAttributesException("Android Device ID not specified in constructor");
        // }
        // method.setQueryString(new NameValuePair[] {
        // new NameValuePair("key", "value")
        // });
        // client.dispatchGet(new
        // URI("https://android.clients.google.com/music/mplay?" +
        // track.getId()));
    }

    @Override
    public Playlists getAllSkyJamPlaylists() throws IOException,
            URISyntaxException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TrackFeed getSkyJamPlaylist(final String plID) throws IOException,
            URISyntaxException
    {
        // TODO Auto-generated method stub
        return null;
    }
}
