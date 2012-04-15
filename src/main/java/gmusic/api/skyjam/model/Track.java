package gmusic.api.skyjam.model;

import java.util.Collection;

public class Track
{
	private String id;
	private String clientId;
	private long creationTimestamp;
	private long lastModifiedTimestamp;
	private boolean deleted;
	private String title;
	private String artist;
	private String composer;
	private String album;
	private String albumArtist;
	private int year;
	private String comment;
	private int trackNumber;
	private String genre;
	private long durationMillis;
	private int beatsPerMinute;
	private Collection<AlbumArtRef> albumArtRef;
	private int playCount;
	private int totalTrackCount;
	private int discNumber;
	private int totalDiscCount;
	private String rating;
	private long estimatedSize;
	public final String getId()
	{
		return id;
	}
	public final void setId(String id)
	{
		this.id = id;
	}
	public final String getClientId()
	{
		return clientId;
	}
	public final long getCreationTimestamp()
	{
		return creationTimestamp;
	}
	public final long getLastModifiedTimestamp()
	{
		return lastModifiedTimestamp;
	}
	public final boolean isDeleted()
	{
		return deleted;
	}
	public final String getTitle()
	{
		return title;
	}
	public final void setTitle(String title)
	{
		this.title = title;
	}
	public final String getArtist()
	{
		return artist;
	}
	public final String getComposer()
	{
		return composer;
	}
	public final String getAlbum()
	{
		return album;
	}
	public final String getAlbumArtist()
	{
		return albumArtist;
	}
	public final int getYear()
	{
		return year;
	}
	public final String getComment()
	{
		return comment;
	}
	public final int getTrackNumber()
	{
		return trackNumber;
	}
	public final String getGenre()
	{
		return genre;
	}
	public final long getDurationMillis()
	{
		return durationMillis;
	}
	public final int getBeatsPerMinute()
	{
		return beatsPerMinute;
	}
	public final Collection<AlbumArtRef> getAlbumArtRef()
	{
		return albumArtRef;
	}
	public final int getPlayCount()
	{
		return playCount;
	}
	public final int getTotalTrackCount()
	{
		return totalTrackCount;
	}
	public final int getDiscNumber()
	{
		return discNumber;
	}
	public final int getTotalDiscCount()
	{
		return totalDiscCount;
	}
	public final String getRating()
	{
		return rating;
	}
	public final long getEstimatedSize()
	{
		return estimatedSize;
	}

}
