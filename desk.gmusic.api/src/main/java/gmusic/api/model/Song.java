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

import gmusic.model.Tune;

import java.net.URI;
import java.net.URISyntaxException;

public class Song extends Tune
{
    private int totalTracks;
    private boolean subjectToCuration;
    private String name;
    private int totalDiscs;
    private String titleNorm;
    private String albumNorm;
    private int track;
    private String albumArtUrl;
    private String url;
    private float creationDate;
    private String albumArtistNorm;
    private String artistNorm;
    private double lastPlayed;
    private String metajamId;
    private int type;
    private int disc;

    public Song()
    {
    }

    public final String getMetajamId()
    {
        return metajamId;
    }

    public final void setMetajamId(final String metajamId)
    {
        this.metajamId = metajamId;
    }

    public final boolean isSubjectToCuration()
    {
        return subjectToCuration;
    }

    public final void setSubjectToCuration(final boolean subjectToCuration)
    {
        this.subjectToCuration = subjectToCuration;
    }

    public final String getAlbumArtUrl()
    {
        return albumArtUrl;
    }

    public final URI getAlbumArtUrlAsURI() throws URISyntaxException
    {
        return new URI("http:" + albumArtUrl);
    }

    public final void setAlbumArtUrl(final String albumArtUrl)
    {
        this.albumArtUrl = albumArtUrl;
    }

    public final void setGenre(final String genre)
    {
        this.genre = genre;
    }

    public final void setBeatsPerMinute(final int beatsPerMinute)
    {
        this.beatsPerMinute = beatsPerMinute;
    }

    public final String getAlbumArtistNorm()
    {
        return albumArtistNorm;
    }

    public final void setAlbumArtistNorm(final String albumArtistNorm)
    {
        this.albumArtistNorm = albumArtistNorm;
    }

    public final String getArtistNorm()
    {
        return artistNorm;
    }

    public final void setArtistNorm(final String artistNorm)
    {
        this.artistNorm = artistNorm;
    }

    public final void setAlbum(final String album)
    {
        this.album = album;
    }

    public final double getLastPlayed()
    {
        return lastPlayed;
    }

    public final void setLastPlayed(final double lastPlayed)
    {
        this.lastPlayed = lastPlayed;
    }

    public final int getType()
    {
        return type;
    }

    public final void setType(final int type)
    {
        this.type = type;
    }

    public final int getDisc()
    {
        return disc;
    }

    public final void setDisc(final int disc)
    {
        this.disc = disc;
    }

    public final void setId(final String id)
    {
        this.id = id;
    }

    public final void setComposer(final String composer)
    {
        this.composer = composer;
    }

    public final void setTitle(final String title)
    {
        this.title = title;
    }

    public final void setAlbumArtist(final String albumArtist)
    {
        this.albumArtist = albumArtist;
    }

    public final int getTotalTracks()
    {
        return totalTracks;
    }

    public final void setTotalTracks(final int totalTracks)
    {
        this.totalTracks = totalTracks;
    }

    public final String getName()
    {
        return name;
    }

    public final void setName(final String name)
    {
        this.name = name;
    }

    public final int getTotalDiscs()
    {
        return totalDiscs;
    }

    public final void setTotalDiscs(final int totalDiscs)
    {
        this.totalDiscs = totalDiscs;
    }

    public final void setYear(final int year)
    {
        this.year = year;
    }

    public final String getTitleNorm()
    {
        return titleNorm;
    }

    public final void setTitleNorm(final String titleNorm)
    {
        this.titleNorm = titleNorm;
    }

    public final void setArtist(final String artist)
    {
        this.artist = artist;
    }

    public final String getAlbumNorm()
    {
        return albumNorm;
    }

    public final void setAlbumNorm(final String albumNorm)
    {
        this.albumNorm = albumNorm;
    }

    public final int getTrack()
    {
        return track;
    }

    public final void setTrack(final int track)
    {
        this.track = track;
    }

    public final void setDurationMillis(final long durationMillis)
    {
        this.durationMillis = durationMillis;
    }

    public final void setDeleted(final boolean deleted)
    {
        this.deleted = deleted;
    }

    public final String getUrl()
    {
        return url;
    }

    public final void setUrl(final String url)
    {
        this.url = url;
    }

    public final float getCreationDate()
    {
        return creationDate;
    }

    public final void setCreationDate(final float creationDate)
    {
        this.creationDate = creationDate;
    }

    public final int getPlaycount()
    {
        return playCount;
    }

    public final void setPlaycount(final int playcount)
    {
        this.playCount = playcount;
    }

    public final void setRating(final String rating)
    {
        this.rating = rating;
    }

    public final void setComment(final String comment)
    {
        this.comment = comment;
    }

}
