package recommendify;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.HashMap;

public class AuthSuccessController {
    private Spotify spotify = new Spotify();

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
        HashMap<String, String> user_data = spotify.parseUserData();

        username.setText(user_data.get("username"));
        email.setText(user_data.get("email"));
        followerCt.setText(user_data.get("follower_count"));
        playlistCt.setText(user_data.get("playlist_count"));
    }

    public void pressViewPlaylistsBtn(ActionEvent e) throws Exception {
        System.out.println("Attempting to fetch playlists...");
        ArrayList playlists = spotify.grabPlaylists();
    }
}
