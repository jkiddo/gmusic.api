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
package gmusic.api.skyjam.model;

import java.net.URI;
import java.net.URISyntaxException;

public class AlbumArtRef
{
    private String url;

    public final String getUrl()
    {
        return url;
    }

    public final URI getUrlAsURI() throws URISyntaxException
    {
        return new URI(url);
    }
}
