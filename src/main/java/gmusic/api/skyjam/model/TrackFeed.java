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
package gmusic.api.skyjam.model;

public class TrackFeed
{
	public final String getNextPageToken()
	{
		return nextPageToken;
	}
	public final TrackFeedData getData()
	{
		return data;
	}
	private String nextPageToken;
	private TrackFeedData data;
}
