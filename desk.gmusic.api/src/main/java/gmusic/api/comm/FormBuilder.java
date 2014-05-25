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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.apache.http.entity.ContentType;

import com.google.common.net.HttpHeaders;

public class FormBuilder
{
    private final String boundary = "----------"
            + String.format("%x", new Date().getTime());
    private final String contentType = ContentType.MULTIPART_FORM_DATA
            + "; boundary=" + boundary;

    private final ByteArrayOutputStream outputStream;

    public final static FormBuilder getEmpty() throws IOException
    {
        final FormBuilder b = new FormBuilder();
        b.close();
        return b;
    }

    public final String getContentType()
    {
        return contentType;
    }

    public FormBuilder()
    {
        outputStream = new ByteArrayOutputStream();
    }

    public final void addFields(final Map<String, String> fields)
            throws IOException
    {
        for (final Map.Entry<String, String> key : fields.entrySet())
        {
            addField(key.getKey(), key.getValue());
        }
    }

    private final void addField(final String key, final String value)
            throws IOException
    {
        final StringBuilder sb = new StringBuilder();

        sb.append(String.format("\r\n--%1$s\r\n", boundary));
        sb.append(HttpHeaders.CONTENT_DISPOSITION + ": form-data;");
        sb.append(String.format("name=\"%1$s\";\r\n\r\n%2$s", key, value));

        outputStream.write(sb.toString().getBytes());
    }

    public final void addFile(final String name, final String fileName,
            final byte[] file)
            throws IOException
    {
        final StringBuilder sb = new StringBuilder();

        sb.append(String.format("\r\n--%1$s\r\n", boundary));
        sb.append(String
                .format(HttpHeaders.CONTENT_DISPOSITION
                        + ": form-data; name=\"%1$s\"; filename=\"%2$s\"\r\n",
                        name, fileName));

        sb.append(String.format(HttpHeaders.CONTENT_TYPE + ": %1$s\r\n\r\n",
                ContentType.APPLICATION_OCTET_STREAM));

        outputStream.write(sb.toString().getBytes());
        outputStream.write(file, 0, file.length);
    }

    public final void close() throws IOException
    {
        outputStream.write(("\r\n--" + boundary + "--\r\n").getBytes());
    }

    public final byte[] getBytes()
    {
        return outputStream.toByteArray();
    }
}
