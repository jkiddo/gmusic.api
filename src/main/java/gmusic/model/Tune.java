package gmusic.model;

public abstract class Tune
{
	protected String genre;
	protected int beatsPerMinute;
	protected String album;
	protected String id;
	protected String composer;
	protected String title;
	protected String albumArtist;
	protected int year;
	protected String artist;
	protected long durationMillis;
	protected boolean deleted;
	protected int playCount;
	protected String rating;
	public final String getGenre()
	{
		return genre;
	}
	public final int getBeatsPerMinute()
	{
		return beatsPerMinute;
	}
	public final String getAlbum()
	{
		return album;
	}
	public final String getId()
	{
		return id;
	}
	public final String getComposer()
	{
		return composer;
	}
	public final String getTitle()
	{
		return title;
	}
	public final String getAlbumArtist()
	{
		return albumArtist;
	}
	public final int getYear()
	{
		return year;
	}
	public final String getArtist()
	{
		return artist;
	}
	public final long getDurationMillis()
	{
		return durationMillis;
	}
	public final boolean isDeleted()
	{
		return deleted;
	}
	public final int getPlayCount()
	{
		return playCount;
	}
	public final String getRating()
	{
		return rating;
	}
	public final String getComment()
	{
		return comment;
	}
	protected String comment;
}
