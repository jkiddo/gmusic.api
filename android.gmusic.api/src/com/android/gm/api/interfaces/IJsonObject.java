package com.android.gm.api.interfaces;

import org.json.JSONObject;

public interface IJsonObject<T>
{
	T fromJsonObject(JSONObject jsonObject);
}
