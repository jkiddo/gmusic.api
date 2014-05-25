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
package gmusic.api.interfaces;

import gmusic.api.comm.FormBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public interface IGoogleHttpClient
{
    final String COOKIE_FORMAT = "?u=0&xt=%1$s";
    final String GOOGLE_LOGIN_AUTH_KEY = "Authorization";
    final String GOOGLE_LOGIN_AUTH_VALUE = "GoogleLogin auth=%1$s";
    final String HTTPS_PLAY_GOOGLE_COM_MUSIC_LISTEN = "https://play.google.com/music/listen?hl=en&u=0";
    final String HTTPS_PLAY_GOOGLE_COM_MUSIC_SERVICES = "https://play.google.com/music/services/";

    String dispatchPost(URI address, FormBuilder form) throws IOException,
            URISyntaxException;

    String dispatchPost(URI address, String json) throws IOException,
            URISyntaxException;

    String dispatchGet(URI address) throws URISyntaxException, IOException;
}
