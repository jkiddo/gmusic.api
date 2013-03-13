package com.android.gm.api.comm;

import org.apache.http.HttpEntity;

import android.content.Context;
import android.os.Message;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

public class GmHttpClient extends SyncHttpClient {
	private int responseCode;

	/*
	 * as this is a synchronous request this is just a helping mechanism to pass
	 * the result back to this method. Therefore the result object has to be a
	 * field to be accessible
	 */
	private String result;
	private AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler() {

		protected void sendResponseMessage(org.apache.http.HttpResponse response) {
			responseCode = response.getStatusLine().getStatusCode();
			super.sendResponseMessage(response);
		};

		@Override
		protected void sendMessage(Message msg) {
			/*
			 * Dont use the handler and send it directly to the analysis
			 * (because its all the same thread)
			 */
			handleMessage(msg);
		}

		@Override
		public void onSuccess(String content) {
			result = content;
		}

		@Override
		public void onFailure(Throwable error, String content) {
			result = onRequestFailed(error, content);
		}
	};

	public GmHttpClient() {
		super();
	}

    public String post(Context context, String url, HttpEntity entity, String contentType) {
    	post(context, url, entity, contentType, responseHandler);
    	return result;
    }

	@Override
	public String onRequestFailed(Throwable error, String content) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @return the response code for the last request, might be usefull
	 *         sometimes
	 */
	@Override
	public int getResponseCode() {
		return responseCode;
	}
}
