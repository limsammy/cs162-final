package recommendify;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.io.*;
import java.net.URI;
import java.util.Properties;

public class Spotify {
    private static ConfigHelper configHelper = new ConfigHelper();
    private static Properties configProps;

    private static String clientId = new String();
    private static String clientSecret = new String();
    private static final URI redirectUri = SpotifyHttpManager.makeUri("https://example.com/spotify-redirect");

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(configProps.getProperty("clientId"))
            .setClientSecret(configProps.getProperty("clientSecret"))
            .setRedirectUri(redirectUri)
            .build();

    private static final AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
            .show_dialog(true)
            .build();

    public Spotify() {
        try {
            configHelper.loadProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setApiConfig(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public static String getAuthUri() {
        final URI uri = authorizationCodeUriRequest.execute();

        return uri.toString();
    }
}
