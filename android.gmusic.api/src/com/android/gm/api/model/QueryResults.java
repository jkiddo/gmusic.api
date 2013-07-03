package com.android.gm.api.model;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.gm.api.interfaces.IJsonArray;
import com.android.gm.api.interfaces.IJsonObject;

//not used in the Android app
public class QueryResults implements IJsonObject<QueryResults>, IJsonArray<Song>
{

	private ArrayList<Song> mArtists;
	private ArrayList<Song> mAlbums;
	private ArrayList<Song> mSongs;

	public ArrayList<Song> getArtists()
	{
		return mArtists;
	}

	public void setArtists(ArrayList<Song> artists)
	{
		mArtists = artists;
	}

	public ArrayList<Song> getAlbums()
	{
		return mAlbums;
	}

	public void setAlbums(ArrayList<Song> albums)
	{
		mAlbums = albums;
	}

	public ArrayList<Song> getSongs()
	{
		return mSongs;
	}

	public void setSongs(ArrayList<Song> songs)
	{
		mSongs = songs;
	}

	@Override
	public QueryResults fromJsonObject(JSONObject jsonObject)
	{
		if(jsonObject != null)
		{
			JSONArray jsonArray = jsonObject.optJSONArray("artists");
			mArtists = (ArrayList<Song>) fromJsonArray(jsonArray);

			jsonArray = jsonObject.optJSONArray("albums");
			mAlbums = (ArrayList<Song>) fromJsonArray(jsonArray);

			jsonArray = jsonObject.optJSONArray("songs");
			mSongs = (ArrayList<Song>) fromJsonArray(jsonArray);
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
