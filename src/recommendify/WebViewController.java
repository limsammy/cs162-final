package recommendify;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.util.HashMap;
import java.util.Map;

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
        String url = loginView.getEngine().getLocation();
        String code = getQueryCode(url);

        spotify.getAccessToken();
        System.out.println("Got access token: " + code);
        System.out.println("Closing login window...");
        ((Button)e.getTarget()).getScene().getWindow().hide();
    }

    private static String getQueryCode(String query) {
        String[] params = query.split("\\?");
        String code = params[1].split("=")[1];
        return code;
    }
}
