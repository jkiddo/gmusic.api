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
