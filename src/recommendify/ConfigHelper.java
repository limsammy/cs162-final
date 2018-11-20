package recommendify;


import java.io.*;
import java.util.Properties;

public class ConfigHelper {
    private static File configFile = new File("config.properties");
    private static Properties configProps;

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

    public void saveProperties(String clientId, String clientSecret) throws IOException {
        configProps.setProperty("clientId", clientId);
        configProps.setProperty("clientSecret", clientSecret);
        OutputStream outputStream = new FileOutputStream(configFile);
        configProps.store(outputStream, "API keys");
        outputStream.close();
    }
}
