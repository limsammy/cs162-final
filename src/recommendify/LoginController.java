package recommendify;

import javafx.event.ActionEvent;
import java.io.IOException;

public class LoginController {
    Spotify spotify = new Spotify();

    public void pressLoadConfigBtn(ActionEvent e) throws IOException {
        System.out.println("Loading config...");
        try {
            spotify.loadProperties();
            System.out.println("config.properties file loaded successfully.");
        } catch (IOException ex) {
            System.out.println("The config.properties file does not exist, default properties loaded. " +
                    "Please set these values manually");
        }
    }
}
