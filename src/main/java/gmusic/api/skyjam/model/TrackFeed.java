package gmusic.api.skyjam.model;

public class TrackFeed
{
	public final String getNextPageToken()
	{
		return nextPageToken;
	}
	public final TrackFeedData getData()
	{
		return data;
	}
	private String nextPageToken;
	private TrackFeedData data;
}
