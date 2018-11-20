package recommendify;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Properties;

public class LoginController {
    private Spotify spotify = new Spotify();
    private Properties configProps;

    @FXML
    private Label isLoaded;

    @FXML
    private void initialize() {
        if (configProps == null) {
            isLoaded.setText("Not loaded.");
        } else {
            isLoaded.setText("Loaded.");
        }
    }

    public void pressLoadConfigBtn(ActionEvent e) throws IOException {
        System.out.println("Loading config...");
        try {
            configProps = spotify.loadProperties();
            System.out.println("config.properties file loaded successfully.");
            isLoaded.setText("Loaded.");
        } catch (IOException ex) {
            System.out.println("The config.properties file does not exist, default properties loaded. " +
                    "Please set these values now.");
            renderConfigWindow();
        }
    }

    private void renderConfigWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("config.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("ABC");
        stage.setScene(new Scene(root1));
        stage.show();
    }
}
