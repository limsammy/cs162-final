package recommendify.controllers;

import javafx.event.ActionEvent;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.IOException;

public class WelcomeController {
    public void pressStartBtn(ActionEvent e) throws IOException {
        System.out.println("Start button has been pressed...");
        System.out.println("Switching to login scene...");
        changeScreen(e);
    }

    public void pressQuitBtn(ActionEvent e) {
        System.out.println("Quitting application...");
        Platform.exit();
    }

    public void changeScreen(ActionEvent e) throws IOException {
        Parent loginViewParent = FXMLLoader.load(getClass().getResource("../resources/login.fxml"));
        Scene loginViewScene = new Scene(loginViewParent);

        Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();

        window.setResizable(false);

        window.setScene(loginViewScene);
        window.show();
    }
}
