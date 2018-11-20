package recommendify;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.io.*;
import java.net.URI;
import java.util.Properties;

public class Spotify {
    private static ConfigHelper configHelper = new ConfigHelper();
    private static Properties configProps;

    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://www.movemypaper.com/images/sucess.png");

    private static SpotifyApi spotifyApi;
    private static AuthorizationCodeUriRequest authorizationCodeUriRequest;
    private static AuthorizationCodeRequest authorizationCodeRequest;

    public Spotify() {
        try {
            configProps = configHelper.loadProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
        buildApiObjs();
    }

    public static void requestAuth(String code) {
        authorizationCodeRequest = spotifyApi.authorizationCode(code)
                .build();
    }

    public static void getAccessToken() {
        try {
            AuthorizationCodeCredentials authorizationCodeCredentials;

            try {
                authorizationCodeCredentials = authorizationCodeRequest.execute();
                // Set access and refresh token for further "spotifyApi" object usage
                spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
                spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

                System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void buildApiObjs() {
        spotifyApi = new SpotifyApi.Builder()
                .setClientId(configProps.getProperty("clientId"))
                .setClientSecret(configProps.getProperty("clientSecret"))
                .setRedirectUri(redirectUri)
                .build();

        authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
                .show_dialog(true)
                .build();
    }

    public static String getAuthUri() {
        final URI uri = authorizationCodeUriRequest.execute();

        return uri.toString();
    }
}
