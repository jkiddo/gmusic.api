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

public class AddPlaylist
{
    private String id;
    private String title;
    private boolean success;

    public final String getId()
    {
        return id;
    }

    public final void setId(final String id)
    {
        this.id = id;
    }

    public final String getTitle()
    {
        return title;
    }

    public final void setTitle(final String title)
    {
        this.title = title;
    }

    public final boolean isSuccess()
    {
        return success;
    }

    public final void setSuccess(final boolean success)
    {
        this.success = success;
    }

}
