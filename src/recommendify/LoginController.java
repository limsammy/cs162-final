package recommendify;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Properties;

public class LoginController {
    private Spotify spotify = new Spotify();
    private ConfigHelper configHelper = new ConfigHelper();
    private Properties configProps;

    @FXML
    private Label isLoaded;
    @FXML
    private Label clientId;
    @FXML
    private Label clientSecret;
    @FXML
    private TextField idText;
    @FXML
    private TextField secretText;

    public void pressLoginBtn(ActionEvent e) throws Exception {
        System.out.println("Opening authorization WebView...");
        renderWebView();
    }

    public void pressLoadConfigBtn(ActionEvent e) throws IOException {
        System.out.println("Loading config...");
        try {
            configProps = configHelper.loadProperties();
            System.out.println("config.properties file loaded successfully.");
            isLoaded.setText("Loaded.");
            updateApiLabels();
            spotify.setApiConfig(configProps.getProperty("clientId"), configProps.getProperty("clientSecret"));
        } catch (IOException ex) {
            System.out.println("The config.properties file does not exist, default properties loaded. " +
                    "Please set these values now.");
            renderConfigWindow();
            System.out.println("Opened API key configuration window, waiting for user input...");
            updateApiLabels();
            spotify.setApiConfig(configProps.getProperty("clientId"), configProps.getProperty("clientSecret"));
        }
    }

    public void pressSaveBtn(ActionEvent e) {
        System.out.println("Attempting to save config...");
        try {
            configHelper.saveProperties(idText.getText(), secretText.getText());
            System.out.println("Successfully saved API tokens in file config.properties!");
            ((Button)e.getTarget()).getScene().getWindow().hide();
            System.out.println("Closed modal...");
            updateApiLabels();
        } catch (IOException ex) {
            System.out.println("Could not save for some unknown reason...");
        }
    }

    private void renderConfigWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("config.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Set API Keys Here");
        stage.setScene(new Scene(root1));
        stage.show();
    }

    private void renderWebView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("webview.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Login");
        stage.setScene(new Scene(root1));
        stage.show();
    }

    private void updateApiLabels() {
        clientId.setText(configProps.getProperty("clientId"));
        clientSecret.setText(configProps.getProperty("clientSecret"));
    }
}
