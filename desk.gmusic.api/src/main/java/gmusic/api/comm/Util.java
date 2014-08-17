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

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;

public class Util
{
    static String extractAuthenticationToken(final String response)
    {

        // Pattern pattern = Pattern.compile("Auth=(?<AUTH>(.*?))$",
        // Pattern.CASE_INSENSITIVE);
        // String auth =
        // pattern.matcher(EntityUtils.toString(response.getEntity())).group();

        final int startIndex = response.indexOf("Auth=") + "Auth=".length();
        int endIndex = response.indexOf("\n", startIndex);

        if (startIndex > -1 && endIndex == -1)
            endIndex = response.length();
        return response.substring(startIndex, endIndex).trim();
    }

    public static String getStringFromInputStream(final InputStream is)
            throws IOException
    {
        return toString(is, Charsets.UTF_8);
    }

    public static String toString(final InputStream is, final Charset cs)
            throws IOException
    {
        Closeable closeMe = is;
        try
        {
            final InputStreamReader isr = new InputStreamReader(is, cs);
            closeMe = isr;
            return CharStreams.toString(isr);
        } finally
        {
            Closeables.close(closeMe, true);
        }
    }
}
