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

import gmusic.api.comm.ApacheConnector;
import gmusic.api.impl.GoogleMusicAPI;
import gmusic.api.impl.GoogleSkyJamAPI;
import gmusic.api.interfaces.IGoogleMusicAPI;
import gmusic.api.model.Playlist;
import gmusic.api.model.Playlists;
import gmusic.api.model.Song;

import java.util.Calendar;
import java.util.Collection;

public class APIexample
{
	public static void main(String args[])
	{
		System.out.println(Calendar.getInstance().getTime());
		GoogleSkyJamAPI skyJam = new GoogleSkyJamAPI();

		try
		{
			skyJam.login("jenskristianvilladsen@gmail.com", "yidxhrioyxapunee");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		IGoogleMusicAPI api = new GoogleMusicAPI(new ApacheConnector());
		try
		{
			api.login("jenskristianvilladsen@gmail.com", "yidxhrioyxapunee");
			Playlists playlists = api.getAllPlaylists();
			for(Playlist list : playlists.getMagicPlaylists())
			{
				System.out.println("--- " + list.getTitle() + " " + list.getPlaylistId() + " ---");
				for(Song song : list.getPlaylist())
				{
//					System.out.println(song.getName() + " " + song.getArtist());
				}
			}

			for(Playlist list : playlists.getPlaylists())
			{
				System.out.println("--- " + list.getTitle() + " " + list.getPlaylistId() + " ---");
				for(Song song : list.getPlaylist())
				{
//					System.out.println(song.getName() + " " + song.getArtist());
				}
			}
			 Collection<Song> songs = api.getAllSongs();
			 api.downloadSong(songs.iterator().next());
			// api.downloadSongs(songs);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println(Calendar.getInstance().getTime());
	}
}
