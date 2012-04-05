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
import gmusic.api.interfaces.IGoogleMusicAPI;
import gmusic.api.model.Playlist;
import gmusic.api.model.Playlists;
import gmusic.api.model.Song;

import java.io.File;
import java.net.URL;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

public class APIexample
{
	public static void main(String args[])
	{
		IGoogleMusicAPI api = new GoogleMusicAPI();
		try
		{
			api.login("someemail@gmail.com", "myPass");
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
			Collection<Song> songs = api.getAllSongs();
			for(Song song : songs)
			{
				System.out.println(song.getName());
				System.out.println(api.getSongURL(song.getId()).getUrl());
//				FileUtils.copyURLToFile(new URL(api.getSongURL(song.getId()).getUrl()), new File(song.getName()));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
