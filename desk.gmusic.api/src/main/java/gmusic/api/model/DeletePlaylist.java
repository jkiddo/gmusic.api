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

public class DeletePlaylist
{
    private String deleteId;

    public final String getDeleteId()
    {
        return deleteId;
    }

    public final void setDeleteId(final String deleteId)
    {
        this.deleteId = deleteId;
    }
}
