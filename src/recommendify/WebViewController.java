package recommendify;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

    public void pressFinishBtn(ActionEvent e) {
        spotify.getAccessToken();
        System.out.println("Got access token!");
        System.out.println("Closing login window...");
        ((Button)e.getTarget()).getScene().getWindow().hide();
    }
}
