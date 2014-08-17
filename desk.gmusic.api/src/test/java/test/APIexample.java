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
import gmusic.api.skyjam.model.AlbumArtRef;
import gmusic.api.skyjam.model.Track;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Collection;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.datatype.Artwork;
import org.jaudiotagger.tag.id3.ID3v24Tag;

import com.google.common.io.Files;
import com.google.common.io.Resources;

public class APIexample
{
    public static void main(String args[])
    {
        String password = "";
        String username = "";
        System.out.println(Calendar.getInstance().getTime());
        IGoogleMusicAPI api = new GoogleMusicAPI();
        // IGoogleMusicAPI api = new GoogleSkyJamAPI();
        // GoogleSkyJamAPI api = new GoogleSkyJamAPI(new ApacheConnector(), new
        // JSON(), new File("."));
        // GoogleMusicAPI aa = new GoogleMusicAPI(new ApacheConnector(), new
        // JSON(), new File("."));

        try
        {
            new GoogleSkyJamAPI().login(username, password);
            new GoogleMusicAPI().login(username, password);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            api.login(username, password);
            // QueryResponse response = api.search("Jane");
            // api.downloadSongs(response.getResults().getSongs());

            Song _song = new Song();
            _song.setId("bc94ef2b-7d16-3815-b1cb-63fbef75b87c");
            api.downloadSong(_song);

            Playlists playlists = api.getAllPlaylists();
            if (playlists.getMagicPlaylists() != null)
            {
                for (Playlist list : playlists.getMagicPlaylists())
                {
                    System.out.println("--- " + list.getTitle() + " "
                            + list.getPlaylistId() + " ---");
                    for (Song song : list.getPlaylist())
                    {
                        System.out.println(song.getName() + " "
                                + song.getArtist());
                    }
                }
            }

            Collection<Song> tracks = api.getAllSongs();

            for (Song track : tracks)
            {
                if (track.getAlbumArtUrl() != null)
                {
                    File track_f = api.downloadSong(track);
                    populateFileWithTuneTags(track_f, track);
                    break;
                }
            }

            for (Playlist list : playlists.getPlaylists())
            {
                System.out.println("--- " + list.getTitle() + " "
                        + list.getPlaylistId() + " ---");
                for (Song song : list.getPlaylist())
                {
                    System.out.println(song.getName() + " " + song.getArtist());
                    if (song.getAlbumArtUrl() != null)
                    {
                        File song_f = api.downloadSong(song);
                        populateFileWithTuneTags(song_f, song);
                        break;
                    }
                }
            }
            Collection<Song> songs = api.getAllSongs();
            api.downloadSong(songs.iterator().next());
            // api.downloadSongs(songs);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println(Calendar.getInstance().getTime());
    }

    private static void toDisk(Song song) throws MalformedURLException,
            IOException, URISyntaxException
    {
        File file = new File(new File(".")
                + System.getProperty("path.separator") + song.getId() + ".dl");
        Files.write(Resources.toByteArray(song.getAlbumArtUrlAsURI().toURL()),
                file);
    }

    private static void populateFileWithTuneTags(File file, Song song)
            throws IOException
    {
        try
        {
            AudioFile f = AudioFileIO.read(file);
            Tag tag = f.getTag();
            if (tag == null)
            {
                tag = new ID3v24Tag();
            }
            tag.setField(FieldKey.ALBUM, song.getAlbum());
            tag.setField(FieldKey.ALBUM_ARTIST, song.getAlbumArtist());
            tag.setField(FieldKey.ARTIST, song.getArtist());
            tag.setField(FieldKey.COMPOSER, song.getComposer());
            tag.setField(FieldKey.DISC_NO, String.valueOf(song.getDisc()));
            tag.setField(FieldKey.DISC_TOTAL,
                    String.valueOf(song.getTotalDiscs()));
            tag.setField(FieldKey.GENRE, song.getGenre());
            tag.setField(FieldKey.TITLE, song.getTitle());
            tag.setField(FieldKey.TRACK, String.valueOf(song.getTrack()));
            tag.setField(FieldKey.TRACK_TOTAL,
                    String.valueOf(song.getTotalTracks()));
            tag.setField(FieldKey.YEAR, String.valueOf(song.getYear()));

            if (song.getAlbumArtUrl() != null)
            {
                Artwork artwork = new Artwork();
                artwork.setBinaryData(Resources.toByteArray(song
                        .getAlbumArtUrlAsURI().toURL()));
                tag.addField(artwork);

                /*
                 * File imageFile = new File(new File(".") +
                 * System.getProperty("path.separator") + song.getId() + ".im");
                 * Files
                 * .write(Resources.toByteArray(song.getAlbumArtUrlAsURI().toURL
                 * ()), imageFile); artwork.setFromFile(imageFile);
                 */
            }

            f.setTag(tag);
            AudioFileIO.write(f);
        } catch (Exception e)
        {
            throw new IOException(e);
        }
    }

    private static void populateFileWithTuneTags(File file, Track track)
            throws IOException
    {
        try
        {
            AudioFile f = AudioFileIO.read(file);
            Tag tag = f.getTag();
            if (tag == null)
            {
                tag = new ID3v24Tag();
            }
            tag.setField(FieldKey.ALBUM, track.getAlbum());
            tag.setField(FieldKey.ALBUM_ARTIST, track.getAlbumArtist());
            tag.setField(FieldKey.ARTIST, track.getArtist());
            tag.setField(FieldKey.COMPOSER, track.getComposer());
            tag.setField(FieldKey.DISC_NO,
                    String.valueOf(track.getDiscNumber()));
            tag.setField(FieldKey.DISC_TOTAL,
                    String.valueOf(track.getTotalDiscCount()));
            tag.setField(FieldKey.GENRE, track.getGenre());
            tag.setField(FieldKey.TITLE, track.getTitle());
            tag.setField(FieldKey.TRACK, String.valueOf(track.getTrackNumber()));
            tag.setField(FieldKey.TRACK_TOTAL,
                    String.valueOf(track.getTotalTrackCount()));
            tag.setField(FieldKey.YEAR, String.valueOf(track.getYear()));

            if (track.getAlbumArtRef() != null
                    && !track.getAlbumArtRef().isEmpty())
            {
                AlbumArtRef[] array = track.getAlbumArtRef().toArray(
                        new AlbumArtRef[track.getAlbumArtRef().size()]);
                for (int i = 0; i < array.length; i++)
                {
                    Artwork artwork = new Artwork();
                    File imageFile = new File(new File(".")
                            + System.getProperty("path.separator")
                            + track.getId() + ".im");
                    Files.write(Resources.toByteArray(array[i].getUrlAsURI()
                            .toURL()), imageFile);
                    artwork.setFromFile(imageFile);
                    tag.addField(artwork);

                    artwork.setFromFile(imageFile);
                    tag.addField(artwork);
                }
            }

            f.setTag(tag);
            AudioFileIO.write(f);
        } catch (Exception e)
        {
            throw new IOException(e);
        }
    }
}
