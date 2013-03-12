package com.android.gm.api;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import android.content.Context;
import android.text.TextUtils;

import com.android.gm.api.comm.GmHttpClient;
import com.android.gm.api.comm.SimpleForm;
import com.android.gm.api.model.AddPlaylistResponse;
import com.android.gm.api.model.Playlist;
import com.android.gm.api.model.Playlists;
import com.android.gm.api.model.QueryResults;
import com.android.gm.api.model.Song;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleMusicApi {
	private static PersistentCookieStore mCookieStore;
	private static GmHttpClient mHttpClient;
	private static GoogleMusicApi mInstance;

	public static void createInstance(Context context) {
		getInstance(context);
	}

	public static GoogleMusicApi getInstance(Context context) {
		if (mInstance == null)
			mInstance = new GoogleMusicApi(context);
		return mInstance;
	}

	private GoogleMusicApi(Context context) {
		mHttpClient = new GmHttpClient();

		mCookieStore = new PersistentCookieStore(
				context.getApplicationContext());
		mHttpClient.setCookieStore(mCookieStore);
		mHttpClient.setUserAgent("");
	}

	public static final HttpClient getRawHttpClient() {
		return mHttpClient.getHttpClient();
	}

	public static final void setAuthorizationHeader(String authToken) {
		mHttpClient.addHeader("Authorization", "GoogleLogin auth=" + authToken);
	}

	public static final void setUserAgent(String userAgent) {
		mHttpClient.setUserAgent(userAgent);
	}

	public static final boolean login(Context context, String authToken) {
		if (!TextUtils.isEmpty(authToken)) {
			SimpleForm form = new SimpleForm().close();
			GoogleMusicApi.setAuthorizationHeader(authToken);
			mHttpClient.post(context,
					"https://play.google.com/music/listen?hl=en&u=0",
					new ByteArrayEntity(form.toString().getBytes()),
					form.getContentType());
			return true;
		} else
			return false;
	}

	public static final boolean login(Context context, String email,
			String password) {

		SimpleForm form = new SimpleForm();
		form.addField("service", "sj");
		form.addField("Email", email);
		form.addField("Passwd", password);
		form.close();

		mHttpClient.getHttpClient().getParams()
				.removeParameter("Authorization");

		String response = mHttpClient.post(context,
				"https://www.google.com/accounts/clientlogin",
				new ByteArrayEntity(form.toString().getBytes()),
				form.getContentType());

		if (mHttpClient.getResponseCode() == HttpStatus.SC_OK) {
			int startIndex = response.indexOf("Auth=") + "Auth=".length();
			int endIndex = response.indexOf("\n", startIndex);

			String authToken = response.substring(startIndex, endIndex).trim();

			return login(context, authToken);
		}

		return false;
	}

	public static final Playlists getAllPlaylist(Context context)
			throws JSONException {

		String response = getPlaylistHelper(context, null);

		JSONObject jsonObject = new JSONObject(response);

		return new Playlists().fromJsonObject(jsonObject);
	}

	public static final URI getSongStream(Song song) throws JSONException,
			URISyntaxException {

		RequestParams params = new RequestParams();
		params.put("u", "0");
		params.put("songid", song.getId());
		params.put("pt", "e");

		String response = mHttpClient.get("https://play.google.com/music/play",
				params);

		JSONObject jsonObject = new JSONObject(response);

		return new URI(jsonObject.optString("url", null));
	}

	public static final ArrayList<Song> getAllSongs(Context context)
			throws JSONException {
		return getSongs(context, "");
	}

	public static final ArrayList<Song> getSongs(Context context,
			String continuationToken) throws JSONException {

		SimpleForm form = new SimpleForm();
		form.addField("json", "{\"continuationToken\":\"" + continuationToken
				+ "\"}");
		form.close();

		String response = mHttpClient.post(context,
				"https://play.google.com/music/services/loadalltracks?u=0&xt="
						+ getXtCookieValue(), new ByteArrayEntity(form
						.toString().getBytes()), form.getContentType());

		JSONObject jsonObject = new JSONObject(response);
		Playlist playlist = new Playlist().fromJsonObject(jsonObject);

		ArrayList<Song> chunkedSongList = new ArrayList<Song>();
		chunkedSongList.addAll(playlist.getPlaylist());

		if (!TextUtils.isEmpty(playlist.getContinuationToken()))
			chunkedSongList.addAll(getSongs(context,
					playlist.getContinuationToken()));

		return chunkedSongList;
	}

	private static final String getPlaylistHelper(Context context,
			String playlistId) throws JSONException {

		JSONObject jsonParam = new JSONObject();
		// if playlistId is null, the value will not be put
		jsonParam.putOpt("id", playlistId);

		SimpleForm form = new SimpleForm();
		form.addField("json", jsonParam.toString());
		form.close();

		return mHttpClient.post(context,
				"https://play.google.com/music/services/loadplaylist?u=0&xt="
						+ getXtCookieValue(), new ByteArrayEntity(form
						.toString().getBytes()), form.getContentType());
	}

	private static final String getXtCookieValue() {

		for (Cookie cookie : mCookieStore.getCookies()) {
			if (cookie.getName().equals("xt"))
				return cookie.getValue();
		}

		return null;
	}

	/*
	 * These methods are not used in the Android app. I built them out for
	 * completeness.
	 */

	public static final Playlist getPlaylist(Context context, String playlistId)
			throws JSONException {

		if (TextUtils.isEmpty(playlistId))
			throw new IllegalArgumentException(
					"The playlist id parameter cannot be empty.");

		String response = getPlaylistHelper(context, playlistId);

		JSONObject jsonObject = new JSONObject(response);

		return new Playlist().fromJsonObject(jsonObject);
	}

	public final AddPlaylistResponse addPlaylist(Context context,
			String playlistName) throws JSONException, IllegalArgumentException {

		if (TextUtils.isEmpty(playlistName))
			throw new IllegalArgumentException(
					"The playlist name parameter cannot be empty.");

		SimpleForm form = new SimpleForm();
		form.addField("json", "{\"title\":\"" + playlistName + "\"}");
		form.close();

		String response = mHttpClient.post(context,
				"https://play.google.com/music/services/addplaylist",
				new ByteArrayEntity(form.toString().getBytes()),
				form.getContentType());

		JSONObject jsonObject = new JSONObject(response);

		return new AddPlaylistResponse().fromJsonObject(jsonObject);
	}

	public static final String deletePlaylist(Context context, String id)
			throws JSONException, IllegalArgumentException {

		if (TextUtils.isEmpty(id))
			throw new IllegalArgumentException(
					"The id parameter cannot be empty.");

		SimpleForm form = new SimpleForm();
		form.addField("json", "{\"id\":\"" + id + "\"}");
		form.close();

		String response = mHttpClient.post(context,
				"https://play.google.com/music/services/deletepaylist",
				new ByteArrayEntity(form.toString().getBytes()),
				form.getContentType());

		JSONObject jsonObject = new JSONObject(response);

		return jsonObject.optString("deleteId", null);
	}

	public static final QueryResults search(Context context, String query)
			throws JSONException, IllegalArgumentException {

		if (TextUtils.isEmpty(query))
			throw new IllegalArgumentException(
					"The query parameter cannot be empty.");

		JSONObject jsonParam = new JSONObject();
		jsonParam.putOpt("q", query);

		SimpleForm form = new SimpleForm();
		form.addField("json", jsonParam.toString());
		form.close();

		String response = mHttpClient.post(context,
				"https://play.google.com/music/services/search",
				new ByteArrayEntity(form.toString().getBytes()),
				form.getContentType());

		JSONObject jsonObject = new JSONObject(response);

		return new QueryResults().fromJsonObject(jsonObject
				.optJSONObject("results"));
	}
}
