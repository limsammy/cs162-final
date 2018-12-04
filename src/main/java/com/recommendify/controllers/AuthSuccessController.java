package com.recommendify.controllers;

import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import com.recommendify.helpers.SpotifyApiHelper;
import com.recommendify.models.Playlist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Controller class for AuthSuccess view
 *
 * @author Sam Lim
 */
public class AuthSuccessController {
    private SpotifyApiHelper spotifyService = new SpotifyApiHelper();

    @FXML
    private Label username;
    @FXML
    private Label email;
    @FXML
    private Label followerCt;
    @FXML
    private Label playlistCt;

    @FXML
    private void initialize() {
        HashMap<String, String> user_data = spotifyService.parseUserData();

        username.setText(user_data.get("username"));
        email.setText(user_data.get("email"));
        followerCt.setText(user_data.get("follower_count"));
        playlistCt.setText(user_data.get("playlist_count"));
    }

    public void pressViewPlaylistsBtn(ActionEvent e) {
        System.out.println("Fetching playlists (doin lotsa magic)...");
        ArrayList<Playlist> playlists = buildPlaylistsList(spotifyService.grabPlaylists());
        System.out.println("# of playlists mined for data: " + playlists.size());

        System.out.println("Rendering playlist table view and closing other window...");
        ((Button)e.getTarget()).getScene().getWindow().hide();
        try {
            renderDataTable(e, playlists);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void renderDataTable(ActionEvent e, ArrayList<Playlist> playlistData) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent tableViewParent = loader.load(getClass().getResource("../resources/fxml/playlists_list.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        PlaylistsController pc = loader.getController();
        pc.setPlaylistsArray(playlistData);

        Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
        window.setResizable(false);
        window.setScene(tableViewScene);
        window.show();
    }

    private ArrayList<Playlist> buildPlaylistsList(ArrayList<PlaylistSimplified> rawPlaylists) {
        ArrayList<Playlist> results = new ArrayList<>();

        for (PlaylistSimplified playlist : rawPlaylists) {
            Playlist playlistObj = new Playlist();

            playlistObj.setPlaylistName(playlist.getName());
            playlistObj.setPlaylistId(playlist.getId());
            playlistObj.setPlaylistUri(playlist.getUri());
            playlistObj.setPlaylistTracksCount(playlist.getTracks().getTotal());

            // logic for grabbing list of tracks in playlist
            // TODO: perform this logic when user selects an individual playlist. no need to slow app down by preloading all tracks
            ArrayList<PlaylistTrack> tracks = spotifyService.grabPlaylistTracks(playlist.getId());
            playlistObj.setPlaylistTracks(tracks);

            results.add(playlistObj);
        }

        System.out.println("Extracted necessary elements from playlist objects...");
        System.out.println("Returning compiled array of playlist models!");

        return results;
    }
}
