package recommendify;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class LoginController {
    Spotify spotify = new Spotify();

    public void setClientIdLabel(ActionEvent e) throws IOException {

    }

    public void setClientSecretLabel(ActionEvent e) throws IOException {

    }

    public void pressLoadConfigBtn(ActionEvent e) throws IOException {
        System.out.println("Loading config...");
        try {
            spotify.loadProperties();
            System.out.println("config.properties file loaded succesfully");
        } catch (IOException ex) {
            System.out.println("The config.properties file does not exist, default properties loaded." +
                    "Please set these values manually");
        }
    }
}
