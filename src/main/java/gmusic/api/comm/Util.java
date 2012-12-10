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
}
