package recommendify.models;

import java.util.ArrayList;

/**
 * Model class for a Playlist data object
 *
 * @author Sam Lim
 */
public class Playlist {
    private String playlistName;
    private String playlistId;
    private String playlistUri;
    private ArrayList playlistTracks;
    private Integer playlistTracksCount;

    /**
     * Constructor for playlist model
     *
     */
    public Playlist() {
        this.playlistName = null;
        this.playlistTracks = null;
        this.playlistTracksCount = null;
        this.playlistId = null;
        this.playlistUri = null;
    }

    /**
     * Getter methods for playlist instance vars
     */
    public String getPlaylistName() {
        return this.playlistName;
    }

    public String getPlaylistId() {
        return this.playlistId;
    }

    public Integer getPlaylistTracksCount() {
        return this.playlistTracksCount;
    }

    public String getPlaylistUri() {
        return this.playlistUri;
    }

    public ArrayList getPlaylistTracks() {
        return this.playlistTracks;
    }

    /**
     * Setter methods for playlist instance vars
     */
    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public void setPlaylistTracks(ArrayList playlistTracks) {
        this.playlistTracks = playlistTracks;
    }

    public void setPlaylistTracksCount(Integer playlistTracksCount) {
        this.playlistTracksCount = playlistTracksCount;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public void setPlaylistUri(String playlistUri) {
        this.playlistUri = playlistUri;
    }
}
