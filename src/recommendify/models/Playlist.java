package recommendify.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

/**
 * Model class for a Playlist data object
 *
 * @author Sam Lim
 */
public class Playlist {
    private SimpleStringProperty playlistName;
    private SimpleStringProperty playlistId;
    private SimpleStringProperty playlistUri;
    private ArrayList playlistTracks;
    private SimpleIntegerProperty playlistTracksCount;

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
        return this.playlistName.get();
    }

    public String getPlaylistId() {
        return this.playlistId.get();
    }

    public Integer getPlaylistTracksCount() {
        return this.playlistTracksCount.get();
    }

    public String getPlaylistUri() {
        return this.playlistUri.get();
    }

    public ArrayList getPlaylistTracks() {
        return this.playlistTracks;
    }

    /**
     * Setter methods for playlist instance vars
     */
    public void setPlaylistName(String playlistName) {
        this.playlistName.set(playlistName);
    }

    public void setPlaylistTracks(ArrayList playlistTracks) {
        this.playlistTracks = playlistTracks;
    }

    public void setPlaylistTracksCount(Integer playlistTracksCount) {
        this.playlistTracksCount.set(playlistTracksCount);
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId.set(playlistId);
    }

    public void setPlaylistUri(String playlistUri) {
        this.playlistUri.set(playlistUri);
    }
}
