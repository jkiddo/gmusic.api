package com.android.gm.api.model;

import org.json.JSONObject;

import com.android.gm.api.interfaces.IJsonObject;

// not used in the Android app
public class AddPlaylistResponse implements IJsonObject<AddPlaylistResponse>
{
	private String mId;
	private String mTitle;
	private boolean mSuccess;

	public final String getId()
	{
		return mId;
	}

	public final void setId(String id)
	{
		mId = id;
	}

	public final String getTitle()
	{
		return mTitle;
	}

	public final void setTitle(String title)
	{
		mTitle = title;
	}

	public final boolean isSuccess()
	{
		return mSuccess;
	}

	public final void setSuccess(boolean success)
	{
		mSuccess = success;
	}

	@Override
	public AddPlaylistResponse fromJsonObject(JSONObject jsonObject)
	{
		if(jsonObject != null)
		{
			mId = jsonObject.optString("id", null);
			mTitle = jsonObject.optString("title", null);
			mSuccess = jsonObject.optBoolean("success");
		}

		// return this object to allow chaining
		return this;
	}
}
