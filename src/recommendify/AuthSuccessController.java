package recommendify;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.HashMap;

public class AuthSuccessController {
    private Spotify spotify;

    @FXML
    private Label username;
    @FXML
    private Label email;
    @FXML
    private Label playlistCt;
    @FXML
    private Label currentTrack;
    @FXML
    private Button playBtn;

    @FXML
    private void initialize() {
        HashMap<String, String> user_data = spotify.parseUserData();

        username.setText(user_data.get("username"));
        email.setText(user_data.get("email"));
        playlistCt.setText(user_data.get("playlist_count"));
//        currentTrack.setText();
//        playBtn.setText();
    }
}
