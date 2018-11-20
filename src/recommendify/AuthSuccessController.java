package recommendify;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AuthSuccessController {
    @FXML
    private Label username;
    @FXML
    private Label trackCt;
    @FXML
    private Label albumCt;
    @FXML
    private Label playlistCt;
    @FXML
    private Label currentTrack;
    @FXML
    private Button playBtn;

    @FXML
    private void initialize() {
        username.setText();
        trackCt.setText();
        albumCt.setText();
        playlistCt.setText();
        currentTrack.setText();
        playBtn.setText();
    }
}
