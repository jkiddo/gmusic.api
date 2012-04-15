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

public class Song
{
	private String genre;
	private int beatsPerMinute;
	private String albumArtistNorm;
	private String artistNorm;
	private String album;
	private double lastPlayed;
	private String metajamId;
	private int type;
	private int disc;
	private String id;
	private String composer;
	private String title;
	private String albumArtist;
	private int totalTracks;
	private boolean subjectToCuration;
	private String name;
	private int totalDiscs;
	private int year;
	private String titleNorm;
	private String artist;
	private String albumNorm;
	private int track;
	private long durationMillis;
	private String albumArtUrl;
	private boolean deleted;
	private String url;
	private float creationDate;
	private int playcount;
	private int rating;
	private String comment;

	public Song()
	{}

	public final String getMetajamId()
	{
		return metajamId;
	}

	public final void setMetajamId(String metajamId)
	{
		this.metajamId = metajamId;
	}

	public final boolean isSubjectToCuration()
	{
		return subjectToCuration;
	}

	public final void setSubjectToCuration(boolean subjectToCuration)
	{
		this.subjectToCuration = subjectToCuration;
	}

	public final String getAlbumArtUrl()
	{
		return albumArtUrl;
	}

	public final void setAlbumArtUrl(String albumArtUrl)
	{
		this.albumArtUrl = albumArtUrl;
	}
	public final String getGenre()
	{
		return genre;
	}
	public final void setGenre(String genre)
	{
		this.genre = genre;
	}
	public final int getBeatsPerMinute()
	{
		return beatsPerMinute;
	}
	public final void setBeatsPerMinute(int beatsPerMinute)
	{
		this.beatsPerMinute = beatsPerMinute;
	}
	public final String getAlbumArtistNorm()
	{
		return albumArtistNorm;
	}
	public final void setAlbumArtistNorm(String albumArtistNorm)
	{
		this.albumArtistNorm = albumArtistNorm;
	}
	public final String getArtistNorm()
	{
		return artistNorm;
	}
	public final void setArtistNorm(String artistNorm)
	{
		this.artistNorm = artistNorm;
	}
	public final String getAlbum()
	{
		return album;
	}
	public final void setAlbum(String album)
	{
		this.album = album;
	}
	public final double getLastPlayed()
	{
		return lastPlayed;
	}
	public final void setLastPlayed(double lastPlayed)
	{
		this.lastPlayed = lastPlayed;
	}
	public final int getType()
	{
		return type;
	}
	public final void setType(int type)
	{
		this.type = type;
	}
	public final int getDisc()
	{
		return disc;
	}
	public final void setDisc(int disc)
	{
		this.disc = disc;
	}
	public final String getId()
	{
		return id;
	}
	public final void setId(String id)
	{
		this.id = id;
	}
	public final String getComposer()
	{
		return composer;
	}
	public final void setComposer(String composer)
	{
		this.composer = composer;
	}
	public final String getTitle()
	{
		return title;
	}
	public final void setTitle(String title)
	{
		this.title = title;
	}
	public final String getAlbumArtist()
	{
		return albumArtist;
	}
	public final void setAlbumArtist(String albumArtist)
	{
		this.albumArtist = albumArtist;
	}
	public final int getTotalTracks()
	{
		return totalTracks;
	}
	public final void setTotalTracks(int totalTracks)
	{
		this.totalTracks = totalTracks;
	}
	public final String getName()
	{
		return name;
	}
	public final void setName(String name)
	{
		this.name = name;
	}
	public final int getTotalDiscs()
	{
		return totalDiscs;
	}
	public final void setTotalDiscs(int totalDiscs)
	{
		this.totalDiscs = totalDiscs;
	}
	public final int getYear()
	{
		return year;
	}
	public final void setYear(int year)
	{
		this.year = year;
	}
	public final String getTitleNorm()
	{
		return titleNorm;
	}
	public final void setTitleNorm(String titleNorm)
	{
		this.titleNorm = titleNorm;
	}
	public final String getArtist()
	{
		return artist;
	}
	public final void setArtist(String artist)
	{
		this.artist = artist;
	}
	public final String getAlbumNorm()
	{
		return albumNorm;
	}
	public final void setAlbumNorm(String albumNorm)
	{
		this.albumNorm = albumNorm;
	}
	public final int getTrack()
	{
		return track;
	}
	public final void setTrack(int track)
	{
		this.track = track;
	}
	public final long getDurationMillis()
	{
		return durationMillis;
	}
	public final void setDurationMillis(long durationMillis)
	{
		this.durationMillis = durationMillis;
	}
	public final boolean isDeleted()
	{
		return deleted;
	}
	public final void setDeleted(boolean deleted)
	{
		this.deleted = deleted;
	}
	public final String getUrl()
	{
		return url;
	}
	public final void setUrl(String url)
	{
		this.url = url;
	}
	public final float getCreationDate()
	{
		return creationDate;
	}
	public final void setCreationDate(float creationDate)
	{
		this.creationDate = creationDate;
	}
	public final int getPlaycount()
	{
		return playcount;
	}
	public final void setPlaycount(int playcount)
	{
		this.playcount = playcount;
	}
	public final int getRating()
	{
		return rating;
	}
	public final void setRating(int rating)
	{
		this.rating = rating;
	}
	public final String getComment()
	{
		return comment;
	}
	public final void setComment(String comment)
	{
		this.comment = comment;
	}

}
