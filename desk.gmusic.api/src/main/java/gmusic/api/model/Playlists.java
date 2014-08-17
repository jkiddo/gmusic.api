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
package gmusic.api.model;

import java.util.Collection;

public class Playlists
{
    private Collection<Playlist> playlists;
    private Collection<Playlist> magicPlaylists;

    public Collection<Playlist> getPlaylists()
    {
        return playlists;
    }

    public void setPlaylists(final Collection<Playlist> playlists)
    {
        this.playlists = playlists;
    }

    public Collection<Playlist> getMagicPlaylists()
    {
        return magicPlaylists;
    }

    public void setMagicPlaylists(final Collection<Playlist> magicPlaylists)
    {
        this.magicPlaylists = magicPlaylists;
    }
}
