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

import gmusic.api.interfaces.IJsonDeserializer;

import com.google.gson.Gson;

public class JSON implements IJsonDeserializer
{
    private final Gson gson = new Gson();

    @Override
    public <T> T deserialize(final String data, final Class<T> clazz)
    {
        return gson.fromJson(data, clazz);
    }
}
