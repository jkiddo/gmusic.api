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

public class FormBuilder
{
	private final String boundary = "----------" + String.format("%x", new Date().getTime());
	private String contentType = "multipart/form-data; boundary=" + boundary;

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

	public final void addFields(Map<String, String> fields) throws IOException
	{
		for(Map.Entry<String, String> key : fields.entrySet())
		{
			addField(key.getKey(), key.getValue());
		}
	}

	private final void addField(String key, String value) throws IOException
	{
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("\r\n--%1$s\r\n", boundary));
		sb.append("Content-Disposition: form-data;");
		sb.append(String.format("name=\"%1$s\";\r\n\r\n%2$s", key, value));

		outputStream.write(sb.toString().getBytes());
	}

	public final void addFile(String name, String fileName, byte[] file) throws IOException
	{
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("\r\n--%1$s\r\n", boundary));
		sb.append(String.format("Content-Disposition: form-data; name=\"%1$s\"; filename=\"%2$s\"\r\n", name, fileName));

		sb.append(String.format("Content-Type: %1$s\r\n\r\n", "application/octet-stream"));

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
