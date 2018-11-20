package recommendify;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;

public class WebViewController {
    private Spotify spotifyApi = new Spotify();

    @FXML
    private WebView loginView;

    @FXML
    private void initialize()
    {
        WebEngine engine = loginView.getEngine();
        engine.load(spotifyApi.getAuthUri());
    }

    public void pressFinishBtn(ActionEvent e) {
        String url = loginView.getEngine().getLocation();
        String code = getQueryCode(url);
        spotifyApi.requestAuth(code);
        spotifyApi.getAccessToken();
        System.out.println("Got access token: " + code);
        System.out.println("Closing login window...");
        ((Button)e.getTarget()).getScene().getWindow().hide();
        try {
            System.out.println("Rendering Succesful Login Window...");
            renderLoginSuccess(e, spotifyApi);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void renderLoginSuccess(ActionEvent event, Spotify spotifyApi) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent loginSuccessParent = loader.load(getClass().getResource("auth_success.fxml"));
        Scene loginSuccessScene = new Scene(loginSuccessParent);

        // pass spotify object to next controller to maintain state
        AuthSuccessController asc = loader.getController();
        asc.setSpotifyApi(spotifyApi);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(loginSuccessScene);
        window.show();
    }

    private static String getQueryCode(String query) {
        String[] params = query.split("\\?");
        String code = params[1].split("=")[1];
        return code;
    }
}
