package recommendify;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.io.*;
import java.net.URI;
import java.util.Properties;

public class Spotify {
    private static File configFile = new File("config.properties");
    private static Properties configProps;

    private static final String clientId = new String();
    private static final String clientSecret = new String();
    private static final URI redirectUri = SpotifyHttpManager.makeUri("https://example.com/spotify-redirect");

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .build();

    private static final AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
            .show_dialog(true)
            .build();

    public static void authorizationCodeUri_Sync() {
        final URI uri = authorizationCodeUriRequest.execute();

        System.out.println("URI: " + uri.toString());
    }

    public Spotify() {
        authorizationCodeUri_Sync();
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

    public void saveProperties(String clientId, String clientSecret) throws IOException {
        configProps.setProperty("clientId", clientId);
        configProps.setProperty("clientSecret", clientSecret);
        OutputStream outputStream = new FileOutputStream(configFile);
        configProps.store(outputStream, "API keys");
        outputStream.close();
    }
}
