package recommendify.models;

import java.util.ArrayList;

/**
 * Model class for a Playlist data object
 *
 * @author Sam Lim
 */
public class Playlist {
    private String playlistName;
    private ArrayList playlistTracks;

    /**
     * Constructor for playlist model
     *
     * @param playlistName
     * @param playlistTracks
     *
     */
    public Playlist(String playlistName, ArrayList playlistTracks) {
        this.playlistName = new String(playlistName);
        this.playlistTracks = new ArrayList(playlistTracks);
    }
}
