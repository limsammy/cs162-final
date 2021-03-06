package com.recommendify.controllers;

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
import com.recommendify.helpers.SpotifyApiHelper;

import java.io.IOException;

/**
 * Controller class for Web view
 *
 * @author Sam Lim
 */
public class WebViewController {
    public SpotifyApiHelper spotifyService = new SpotifyApiHelper();

    private String refreshToken;

    @FXML
    private WebView loginView;

    @FXML
    private void initialize()
    {
        WebEngine engine = loginView.getEngine();
        engine.load(spotifyService.getAuthUri());
    }

    public void pressFinishBtn(ActionEvent e) {
        String url = loginView.getEngine().getLocation();
        String code = getQueryCode(url);

        spotifyService.requestAuth(code);
        spotifyService.getAccessToken();
        refreshToken = spotifyService.getRefreshToken();

        System.out.println("Got access token: " + code);
        System.out.println("Got refresh token: " + refreshToken);
        System.out.println("Closing login window...");

        ((Button)e.getTarget()).getScene().getWindow().hide();
        try {
            System.out.println("Rendering Successful Login Window...");

            renderLoginSuccess(e, refreshToken);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void renderLoginSuccess(ActionEvent event, String refreshToken) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent loginSuccessParent = loader.load(getClass().getResource("/fxml/auth_success.fxml"));
        Scene loginSuccessScene = new Scene(loginSuccessParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setResizable(false);

        window.setScene(loginSuccessScene);
        window.show();
    }

    private static String getQueryCode(String query) {
        String[] params = query.split("\\?");
        String code = params[1].split("=")[1];
        code = code.split("&")[0];

        return code;
    }
}
