package recommendify;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class WebViewController {
    private Spotify spotify = new Spotify();

    @FXML
    private WebView loginView;

    @FXML
    private void initialize()
    {
        WebEngine engine = loginView.getEngine();
        engine.load(spotify.getAuthUri());
    }
}
