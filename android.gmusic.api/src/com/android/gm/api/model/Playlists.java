package com.android.gm.api.model;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.gm.api.interfaces.IJsonArray;
import com.android.gm.api.interfaces.IJsonObject;

public class Playlists implements IJsonObject<Playlists>, IJsonArray<Playlist>
{
	private ArrayList<Playlist> mPlaylists;
	private ArrayList<Playlist> mMagicPlaylists;

	public Collection<Playlist> getPlaylists()
	{
		return mPlaylists;
	}

	public void setPlaylists(ArrayList<Playlist> playlists)
	{
		mPlaylists = playlists;
	}

	public ArrayList<Playlist> getMagicPlaylists()
	{
		return mMagicPlaylists;
	}

	public void setMagicPlaylists(ArrayList<Playlist> magicPlaylists)
	{
		mMagicPlaylists = magicPlaylists;
	}

	@Override
	public Playlists fromJsonObject(JSONObject jsonObject)
	{
		if(jsonObject != null)
		{
			JSONArray playlistsArray = jsonObject.optJSONArray("playlists");
			mPlaylists = fromJsonArray(playlistsArray);

			playlistsArray = jsonObject.optJSONArray("magicPlaylists");
			mMagicPlaylists = fromJsonArray(playlistsArray);
		}

		// return this object to allow chaining
		return this;
	}

	@Override
	public ArrayList<Playlist> fromJsonArray(JSONArray jsonArray)
	{
		ArrayList<Playlist> playlists = new ArrayList<Playlist>();
		if(jsonArray != null && jsonArray.length() > 0)
		{
			for(int i = 0; i < jsonArray.length(); i++)
			{
				try
				{
					Playlist playlist = new Playlist().fromJsonObject(jsonArray.getJSONObject(i));
					playlists.add(playlist);
				}
				catch(JSONException e)
				{
					e.printStackTrace();
				}
			}
		}

		return playlists;
	}
}
