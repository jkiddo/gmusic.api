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
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.io.IOUtils;

import com.google.common.base.Strings;

public class HttpUrlConnector implements IGoogleHttp
{
	private boolean isStartup = true;
	private String authorizationToken = null;
	private String cookie;
	private String rawCookie;

	public HttpUrlConnector()
	{
		CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));

	}

	private HttpURLConnection prepareConnection(URI address, boolean output, String method) throws URISyntaxException, IOException
	{
		HttpURLConnection connection = (HttpURLConnection) adjustAddress(address).toURL().openConnection();
		connection.setRequestMethod(method);
		connection.setDoOutput(output);
		if(authorizationToken != null)
		{
			connection.setRequestProperty("Authorization", String.format("GoogleLogin auth=%1$s", authorizationToken));
		}
		return connection;
	}

	@Override
	public final synchronized String dispatchGet(URI address) throws URISyntaxException, IOException
	{
//		HttpURLConnection connection = (HttpURLConnection) adjustAddress(address).toURL().openConnection();
//		connection.setRequestMethod("GET");
//		connection.setRequestProperty("Cookie", rawCookie);
//		if(authorizationToken != null)
//		{
//			connection.setRequestProperty("Authorization", String.format("GoogleLogin auth=%1$s", authorizationToken));
//		}
		HttpURLConnection connection = prepareConnection(address, false, "GET");
		
		connection.connect();
		if(connection.getResponseCode() != 200)
		{
			throw new IllegalStateException("Statuscode " + connection.getResponseCode() + " not supported");
		}

		setCookie(connection);

		return IOUtils.toString(connection.getInputStream());
	}

	@Override
	public final synchronized String dispatchPost(URI address, FormBuilder form) throws IOException, URISyntaxException
	{
		HttpURLConnection connection = prepareConnection(address, true, "POST");
		if(!Strings.isNullOrEmpty(form.getContentType()))
		{
			connection.setRequestProperty("Content-Type", form.getContentType());
		}
		connection.connect();
		connection.getOutputStream().write(form.getBytes());
		if(connection.getResponseCode() != 200)
		{
			throw new IllegalStateException("Statuscode " + connection.getResponseCode() + " not supported");
		}

		String response = IOUtils.toString(connection.getInputStream());

		setCookie(connection);

		if(!isStartup)
		{
			return response;
		}
		return setupAuthentication(response);
	}

	private void setCookie(HttpURLConnection connection)
	{
		if(!Strings.isNullOrEmpty(connection.getHeaderField("Set-Cookie")) && cookie == null)
		{
			rawCookie = connection.getHeaderField("Set-Cookie");
			int startIndex = rawCookie.indexOf("xt=") + "xt=".length();
			int endIndex = rawCookie.indexOf(";", startIndex);
			cookie = rawCookie.substring(startIndex, endIndex);
		}
	}

	private String setupAuthentication(String response) throws IOException, URISyntaxException
	{
		isStartup = false;
		// Pattern pattern = Pattern.compile("Auth=(?<AUTH>(.*?))$", Pattern.CASE_INSENSITIVE);
		// String auth = pattern.matcher(EntityUtils.toString(response.getEntity())).group();

		int startIndex = response.indexOf("Auth=") + "Auth=".length();
		int endIndex = response.indexOf("\n", startIndex);

		authorizationToken = response.substring(startIndex, endIndex).trim();
		return dispatchPost(new URI("https://play.google.com/music/listen?hl=en&u=0"), FormBuilder.getEmpty());
	}

	private URI adjustAddress(URI address) throws MalformedURLException, URISyntaxException
	{
		if(address.toString().startsWith("https://play.google.com/music/services/"))
		{
			return address = new URI(address.toURL() + String.format("?u=0&xt=%1$s", cookie));
		}

		return address;
	}

	@Override
	public String dispatchPost(URI address, String json) throws IOException, URISyntaxException
	{
		HttpURLConnection connection = prepareConnection(address, true, "POST");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.connect();
		connection.getOutputStream().write(json.getBytes());
		if(connection.getResponseCode() != 200)
		{
			throw new IllegalStateException("Statuscode " + connection.getResponseCode() + " not supported");
		}

		String response = IOUtils.toString(connection.getInputStream());
		if(!isStartup)
		{
			return response;
		}
		return setupAuthentication(response);
	}
}
