package recommendify;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Spotify {
    private static File configFile = new File("config.properties");
    private static Properties configProps;
    private static final String clientId = new String();
    private static final String clientSecret = new String();

    public Spotify() {
        // load config file into memory
//        try {
//            loadProperties();
//        } catch (IOException ex) {
//            System.out.println("The config.properties file does not exist, default properties loaded.");
//        }
//
//        // set instance variables
//        this.clientId = configProps.getProperty("clientId");
//        this.clientSecret = configProps.getProperty("clientSecret");
    }

    public Properties loadProperties() throws IOException {
        Properties defaultProps = new Properties();

        // set default properties
        defaultProps.setProperty("clientId", "PLEASE_SET_THIS");
        defaultProps.setProperty("clientSecret", "PLEASE_SET_THIS");

        configProps = new Properties(defaultProps);

        // load properties from config file
        InputStream inputStream = new FileInputStream(configFile);
        configProps.load(inputStream);
        inputStream.close();

        return configProps;
    }
}
