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
package gmusic.api.skyjam.interfaces;

import gmusic.api.interfaces.IGoogleMusicAPI;
import gmusic.api.skyjam.model.Playlists;
import gmusic.api.skyjam.model.Track;
import gmusic.api.skyjam.model.TrackFeed;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

import org.apache.http.client.ClientProtocolException;

public interface IGoogleSkyJam extends IGoogleMusicAPI
{

    final String HTTPS_WWW_GOOGLEAPIS_COM_SJ_V1BETA1_TRACKS = "https://www.googleapis.com/sj/v1beta1/tracks";
    final String HTTPS_WWW_GOOGLEAPIS_COM_SJ_V1BETA1_TRACKFEED = "https://www.googleapis.com/sj/v1beta1/trackfeed";

    Collection<Track> getAllTracks() throws ClientProtocolException,
            IOException, URISyntaxException;

    Collection<File> downloadTracks(Collection<Track> tracks)
            throws URISyntaxException, ClientProtocolException, IOException;

    File downloadTrack(Track track) throws URISyntaxException,
            ClientProtocolException, IOException;

    Playlists getAllSkyJamPlaylists() throws ClientProtocolException,
            IOException, URISyntaxException;

    TrackFeed getSkyJamPlaylist(String plID) throws ClientProtocolException,
            IOException, URISyntaxException;

    URI getTrackURL(Track track) throws URISyntaxException,
            ClientProtocolException, IOException;
}
