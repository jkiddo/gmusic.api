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

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.ByteBuffer;

import org.apache.http.ParseException;

public class Util
{
	static String extractAuthenticationToken(String response) throws ParseException
	{

		// Pattern pattern = Pattern.compile("Auth=(?<AUTH>(.*?))$", Pattern.CASE_INSENSITIVE);
		// String auth = pattern.matcher(EntityUtils.toString(response.getEntity())).group();

		int startIndex = response.indexOf("Auth=") + "Auth=".length();
		int endIndex = response.indexOf("\n", startIndex);

		return response.substring(startIndex, endIndex).trim();
	}

	public static byte[] readBytes(InputStream inputStream) throws IOException
	{
		ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
		int bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];

		int len = 0;
		while((len = inputStream.read(buffer)) != -1)
		{
			byteBuffer.write(buffer, 0, len);
		}

		return byteBuffer.toByteArray();
	}

	public static String getStringFromInputStream(InputStream is)
	{
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try
		{

			br = new BufferedReader(new InputStreamReader(is));
			while((line = br.readLine()) != null)
			{
				sb.append(line);
			}

		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(br != null)
			{
				try
				{
					br.close();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}

	public static ByteBuffer uriTobuffer(URI uri) throws IOException
	{
		ByteArrayOutputStream bais = new ByteArrayOutputStream();
		InputStream is = null;
		try
		{
			is = uri.toURL().openStream();
			byte[] byteChunk = new byte[4096]; // Or whatever size you want to read in at a time.
			int n;

			while((n = is.read(byteChunk)) > 0)
			{
				bais.write(byteChunk, 0, n);
			}
			return ByteBuffer.wrap(bais.toByteArray()).asReadOnlyBuffer();

		}
		catch(IOException e)
		{
			throw e;
		}
		finally
		{
			if(is != null)
			{
				is.close();
			}
		}
	}
}
