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
package test;

import gmusic.api.impl.GoogleMusicAPI;
import gmusic.api.impl.GoogleSkyJamAPI;
import gmusic.api.interfaces.IGoogleMusicAPI;
import gmusic.api.model.Playlist;
import gmusic.api.model.Playlists;
import gmusic.api.model.Song;

public class APIexample
{
	public static void main(String args[])
	{
		GoogleSkyJamAPI skyJam = new GoogleSkyJamAPI();

		try
		{
			skyJam.login("1234@gmail.com", "1234");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		IGoogleMusicAPI api = new GoogleMusicAPI();
		try
		{
			api.login("1234@gmail.com", "1234");
			Playlists playlists = api.getAllPlaylists();
			for(Playlist list : playlists.getMagicPlaylists())
			{
				System.out.println("--- " + list.getTitle() + " " + list.getPlaylistId() + " ---");
				for(Song song : list.getPlaylist())
				{
					System.out.println(song.getName() + " " + song.getArtist());
				}
			}

			for(Playlist list : playlists.getPlaylists())
			{
				System.out.println("--- " + list.getTitle() + " " + list.getPlaylistId() + " ---");
				for(Song song : list.getPlaylist())
				{
					System.out.println(song.getName() + " " + song.getArtist());
				}
			}
			// Collection<Song> songs = api.getAllSongs();
			// api.downloadSongs(songs);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
