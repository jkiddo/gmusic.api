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

/**
 * Query results
 */
public class QueryResults
{

    private Collection<Song> artists;
    private Collection<Song> albums;
    private Collection<Song> songs;

    public Collection<Song> getArtists()
    {
        return artists;
    }

    public void setArtists(final Collection<Song> artists)
    {
        this.artists = artists;
    }

    public Collection<Song> getAlbums()
    {
        return albums;
    }

    public void setAlbums(final Collection<Song> albums)
    {
        this.albums = albums;
    }

    public Collection<Song> getSongs()
    {
        return songs;
    }

    public void setSongs(final Collection<Song> songs)
    {
        this.songs = songs;
    }

}
