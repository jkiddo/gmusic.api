/*******************************************************************************
 * Copyright (c) 2012 Jens Kristian Villadsen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Jens Kristian Villadsen - initial API and implementation
 ******************************************************************************/
package gmusic.api.comm;

import gmusic.api.interfaces.IGoogleHttp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.google.common.base.Strings;

public class GoogleHttp implements IGoogleHttp
{
	private final HttpClient httpClient;
	private final HttpContext localContext;
	private final CookieStore cookieStore;
	private boolean isStartup = true;
	private String authroizationToken = null;

	public GoogleHttp()
	{
		HttpParams http = new BasicHttpParams();
		http.removeParameter("User-Agent");
		http.setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BEST_MATCH);
		httpClient = new DefaultHttpClient(http);
		cookieStore = new BasicCookieStore();
		localContext = new BasicHttpContext();
		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
	}

	private HttpResponse execute(URI uri, HttpRequestBase request) throws ClientProtocolException, IOException, URISyntaxException
	{
		HttpResponse response = httpClient.execute(adjustAddress(uri, request), localContext);
		if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
		{
			EntityUtils.toString(response.getEntity());
			throw new IllegalStateException("Statuscode " + response.getStatusLine().getStatusCode() + " not supported");
		}
		return response;
	}

	@Override
	public final synchronized String dispatchGet(URI address) throws URISyntaxException, ClientProtocolException, IOException
	{
		return EntityUtils.toString(execute(address, new HttpGet()).getEntity());
	}

	@Override
	public final synchronized String dispatchPost(URI address, FormBuilder form) throws ClientProtocolException, IOException, URISyntaxException
	{
		HttpPost request = new HttpPost();
		request.setEntity(new ByteArrayEntity(form.getBytes()));

		if(!Strings.isNullOrEmpty(form.getContentType()))
		{
			request.setHeader("Content-Type", form.getContentType());
		}

		String response = EntityUtils.toString(execute(address, request).getEntity());
		if(!isStartup)
		{
			return response;
		}
		return setupAuthentication(response);
	}

	private String setupAuthentication(String response) throws ParseException, IOException, URISyntaxException
	{
		isStartup = false;
		// Pattern pattern = Pattern.compile("Auth=(?<AUTH>(.*?))$", Pattern.CASE_INSENSITIVE);
		// String auth = pattern.matcher(EntityUtils.toString(response.getEntity())).group();

		int startIndex = response.indexOf("Auth=") + "Auth=".length();
		int endIndex = response.indexOf("\n", startIndex);

		authroizationToken = response.substring(startIndex, endIndex).trim();
		return dispatchPost(new URI("https://play.google.com/music/listen?hl=en&u=0"), FormBuilder.getEmpty());
	}

	private HttpRequestBase adjustAddress(URI address, HttpRequestBase request) throws MalformedURLException, URISyntaxException
	{
		if(address.toString().startsWith("https://play.google.com/music/services/"))
		{
			address = new URI(address.toURL() + String.format("?u=0&xt=%1$s", getCookieValue("xt")));
		}

		request.setURI(address);

		if(authroizationToken != null)
		{
			request.addHeader("Authorization", String.format("GoogleLogin auth=%1$s", authroizationToken));
		}
		// if((address.toString().startsWith("https://android.clients.google.com/music/mplay")) && deviceId != null)
		// {
		// request.addHeader("X-Device-ID", deviceId);
		// }

		return request;
	}
	private String getCookieValue(String cookieName)
	{
		for(Cookie cookie : cookieStore.getCookies())
		{
			if(cookie.getName().equals(cookieName))
			{
				return cookie.getValue();
			}
		}
		return null;
	}

	@Override
	public String dispatchPost(URI address, String json) throws ParseException, ClientProtocolException, IOException, URISyntaxException
	{
		HttpPost request = new HttpPost();
		request.setEntity(new StringEntity(json));
		request.setHeader("Content-Type", "application/json");

		String response = EntityUtils.toString(execute(address, request).getEntity());
		if(!isStartup)
		{
			return response;
		}
		return setupAuthentication(response);
	}
}
