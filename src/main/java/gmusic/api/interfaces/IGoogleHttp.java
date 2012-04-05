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

import org.apache.http.client.ClientProtocolException;

public interface IGoogleHttp
{

	String dispatchPost(URI address, FormBuilder form) throws ClientProtocolException, IOException, URISyntaxException;

	String dispatchGet(URI address) throws URISyntaxException, ClientProtocolException, IOException;

}
