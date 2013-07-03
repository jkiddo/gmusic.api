package com.android.gm.api.model;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.gm.api.interfaces.IJsonArray;
import com.android.gm.api.interfaces.IJsonObject;

public class Playlist implements IJsonObject<Playlist>, IJsonArray<Song>
{

	private String mTitle;
	private String mPlaylistId;
	private long mRequestTime;
	private String mContinuationToken;
	private boolean mDifferentialUpdate;
	private ArrayList<Song> mPlaylist;
	private boolean mContinuation;

	public String getTitle()
	{
		return mTitle;
	}

	public void setTitle(String title)
	{
		mTitle = title;
	}

	public String getPlaylistId()
	{
		return mPlaylistId;
	}

	public void setPlaylistId(String playlistId)
	{
		mPlaylistId = playlistId;
	}

	public long getRequestTime()
	{
		return mRequestTime;
	}

	public void setRequestTime(long requestTime)
	{
		mRequestTime = requestTime;
	}

	public String getContinuationToken()
	{
		return mContinuationToken;
	}

	public void setContinuationToken(String continuationToken)
	{
		mContinuationToken = continuationToken;
	}

	public boolean isDifferentialUpdate()
	{
		return mDifferentialUpdate;
	}

	public void setDifferentialUpdate(boolean differentialUpdate)
	{
		mDifferentialUpdate = differentialUpdate;
	}

	public Collection<Song> getPlaylist()
	{
		return mPlaylist;
	}

	public void setPlaylist(ArrayList<Song> playlist)
	{
		mPlaylist = playlist;
	}

	public boolean isContinuation()
	{
		return mContinuation;
	}

	public void setContinuation(boolean continuation)
	{
		mContinuation = continuation;
	}

	@Override
	public Playlist fromJsonObject(JSONObject jsonObject)
	{
		if(jsonObject != null)
		{
			mTitle = jsonObject.optString("title", null);
			mPlaylistId = jsonObject.optString("playlistId", null);
			mRequestTime = jsonObject.optLong("requestTime");
			mContinuationToken = jsonObject.optString("continuationToken", null);
			mDifferentialUpdate = jsonObject.optBoolean("differentialUpdate");
			mContinuation = jsonObject.optBoolean("continuation");

			JSONArray songsArray = jsonObject.optJSONArray("playlist");
			mPlaylist = fromJsonArray(songsArray);
		}

		// return this object to allow chaining
		return this;
	}

	@Override
	public ArrayList<Song> fromJsonArray(JSONArray jsonArray)
	{
		ArrayList<Song> songList = new ArrayList<Song>();
		if(jsonArray != null && jsonArray.length() > 0)
		{
			for(int i = 0; i < jsonArray.length(); i++)
			{
				try
				{
					Song song = new Song().fromJsonObject(jsonArray.getJSONObject(i));
					songList.add(song);
				}
				catch(JSONException e)
				{
					e.printStackTrace();
				}
			}
		}

		return songList;
	}
}
