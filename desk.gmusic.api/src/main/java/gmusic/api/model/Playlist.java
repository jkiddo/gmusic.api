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
package gmusic.api.model;

import java.util.Collection;

public class Playlist
{
    private String title;
    private String playlistId;
    private double requestTime;
    private String continuationToken;
    private boolean differentialUpdate;
    private Collection<Song> playlist;
    private boolean continuation;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(final String title)
    {
        this.title = title;
    }

    public String getPlaylistId()
    {
        return playlistId;
    }

    public void setPlaylistId(final String playlistId)
    {
        this.playlistId = playlistId;
    }

    public double getRequestTime()
    {
        return requestTime;
    }

    public void setRequestTime(final double requestTime)
    {
        this.requestTime = requestTime;
    }

    public String getContinuationToken()
    {
        return continuationToken;
    }

    public void setContinuationToken(final String continuationToken)
    {
        this.continuationToken = continuationToken;
    }

    public boolean isDifferentialUpdate()
    {
        return differentialUpdate;
    }

    public void setDifferentialUpdate(final boolean differentialUpdate)
    {
        this.differentialUpdate = differentialUpdate;
    }

    public Collection<Song> getPlaylist()
    {
        return playlist;
    }

    public void setPlaylist(final Collection<Song> playlist)
    {
        this.playlist = playlist;
    }

    public boolean isContinuation()
    {
        return continuation;
    }

    public void setContinuation(final boolean continuation)
    {
        this.continuation = continuation;
    }
}
