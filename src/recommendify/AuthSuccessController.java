package recommendify;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.HashMap;

public class AuthSuccessController {
    private Spotify spotify = new Spotify();

    @FXML
    private Label username;
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
        playlistCt.setText(user_data.get("playlist_count"));
        currentTrack.setText(spotify.getCurrentTrack());
//        playBtn.setText();
    }
}
