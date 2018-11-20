package recommendify;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.model_objects.specification.User;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import com.wrapper.spotify.requests.data.library.CheckUsersSavedTracksRequest;
import com.wrapper.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;
import com.wrapper.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

import java.io.*;
import java.net.URI;
import java.util.HashMap;
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

    public static HashMap<String, String> parseUserData() {
        User current_user = getCurrentUser();
        HashMap<String, String> user_data =  new HashMap<>();

        user_data.put("username", current_user.getDisplayName());
        user_data.put("email", current_user.getEmail());
        user_data.put("follower_count", current_user.getFollowers().getTotal().toString());
        user_data.put("playlist_count", getPlaylistCount());

        return user_data;
    }

    private static String getPlaylistCount() {
        final GetListOfCurrentUsersPlaylistsRequest getListOfCurrentUsersPlaylistsRequest = spotifyApi
                .getListOfCurrentUsersPlaylists()
                .offset(0)
                .build();
        String count = null;

        try {
            final Paging<PlaylistSimplified> playlistSimplifiedPaging = getListOfCurrentUsersPlaylistsRequest.execute();
            System.out.println("Succesfully retrieved current user list of playlists!");
            count = playlistSimplifiedPaging.getTotal().toString();
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return count;
    }

    private static User getCurrentUser() {
        final GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = spotifyApi.getCurrentUsersProfile()
                .build();
        User user = null;

        try {
            user = getCurrentUsersProfileRequest.execute();
            System.out.println("Successfully grabbed current user object!");
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return user;
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
