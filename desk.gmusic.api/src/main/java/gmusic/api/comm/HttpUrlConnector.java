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

import gmusic.api.interfaces.IGoogleHttpClient;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import com.google.common.base.Strings;

public class HttpUrlConnector implements IGoogleHttpClient
{
    private boolean isStartup = true;
    private String authorizationToken = null;
    private String cookie;
    private String rawCookie;

    public HttpUrlConnector()
    {
        CookieHandler.setDefault(new CookieManager(null,
                CookiePolicy.ACCEPT_ALL));

    }

    private HttpURLConnection prepareConnection(final URI address,
            final boolean output,
            final String method) throws URISyntaxException, IOException
    {
        final HttpURLConnection connection = (HttpURLConnection) adjustAddress(
                address).toURL().openConnection();
        connection.setRequestProperty("Accept-Encoding", "gzip,deflate");
        connection.setRequestMethod(method);
        connection.setDoOutput(output);
        if (authorizationToken != null)
        {
            connection.setRequestProperty(GOOGLE_LOGIN_AUTH_KEY,
                    String.format(GOOGLE_LOGIN_AUTH_VALUE, authorizationToken));
        }
        return connection;
    }

    @Override
    public final synchronized String dispatchGet(final URI address)
            throws URISyntaxException, IOException
    {
        final HttpURLConnection connection = prepareConnection(address, false,
                "GET");
        connection.connect();
        if (connection.getResponseCode() != 200)
        {
            throw new IllegalStateException("Statuscode "
                    + connection.getResponseCode() + " not supported");
        }

        setCookie(connection);
        return Util.getStringFromInputStream(connection.getInputStream());
    }

    @Override
    public final synchronized String dispatchPost(final URI address,
            final FormBuilder form)
            throws IOException, URISyntaxException
    {
        final HttpURLConnection connection = prepareConnection(address, true,
                "POST");
        if (!Strings.isNullOrEmpty(form.getContentType()))
        {
            connection
                    .setRequestProperty("Content-Type", form.getContentType());
        }
        connection.connect();
        connection.getOutputStream().write(form.getBytes());
        if (connection.getResponseCode() != 200)
        {
            throw new IllegalStateException("Statuscode "
                    + connection.getResponseCode() + " not supported");
        }

        final String response = Util.getStringFromInputStream(connection
                .getInputStream());
        setCookie(connection);

        if (!isStartup)
        {
            return response;
        }
        return setupAuthentication(response);
    }

    private void setCookie(final HttpURLConnection connection)
    {
        if (!Strings.isNullOrEmpty(connection.getHeaderField("Set-Cookie"))
                && cookie == null)
        {
            rawCookie = connection.getHeaderField("Set-Cookie");
            final int startIndex = rawCookie.indexOf("xt=") + "xt=".length();
            final int endIndex = rawCookie.indexOf(";", startIndex);
            cookie = rawCookie.substring(startIndex, endIndex);
        }
    }

    private String setupAuthentication(final String response)
            throws IOException,
            URISyntaxException
    {
        isStartup = false;
        authorizationToken = Util.extractAuthenticationToken(response);
        return dispatchPost(new URI(HTTPS_PLAY_GOOGLE_COM_MUSIC_LISTEN),
                FormBuilder.getEmpty());
    }

    private URI adjustAddress(URI address) throws MalformedURLException,
            URISyntaxException
    {
        if (address.toString().startsWith(HTTPS_PLAY_GOOGLE_COM_MUSIC_SERVICES))
        {
            return address = new URI(address.toURL()
                    + String.format(COOKIE_FORMAT, cookie) + "&format=jsarray");
        }

        return address;
    }

    @Override
    public String dispatchPost(final URI address, final String json)
            throws IOException,
            URISyntaxException
    {
        final HttpURLConnection connection = prepareConnection(address, true,
                "POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.connect();
        connection.getOutputStream().write(json.getBytes());
        if (connection.getResponseCode() != 200)
        {
            throw new IllegalStateException("Statuscode "
                    + connection.getResponseCode() + " not supported");
        }

        final String response = Util.getStringFromInputStream(connection
                .getInputStream());
        if (!isStartup)
        {
            return response;
        }
        return setupAuthentication(response);
    }
}
