package recommendify;

import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.model_objects.miscellaneous.CurrentlyPlaying;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.model_objects.specification.User;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import com.wrapper.spotify.requests.data.library.CheckUsersSavedTracksRequest;
import com.wrapper.spotify.requests.data.player.GetUsersCurrentlyPlayingTrackRequest;
import com.wrapper.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;
import com.wrapper.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class Spotify {
    private static ConfigHelper configHelper = new ConfigHelper();
    private static Properties configProps;

    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://www.movemypaper.com/images/sucess.png");

    private static SpotifyApi spotifyApi;
    private static AuthorizationCodeUriRequest authorizationCodeUriRequest;
    private static AuthorizationCodeRequest authorizationCodeRequest;
    private static AuthorizationCodeCredentials authorizationCodeCredentials;
    private static AuthorizationCodeRefreshRequest authorizationCodeRefreshRequest;

    private static String accessToken;
    private static String refreshToken;

    private static GetCurrentUsersProfileRequest getCurrentUsersProfileRequest;

    public Spotify() {
        try {
            configProps = configHelper.loadProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
        spotifyApi = new SpotifyApi.Builder()
                .setClientId(configProps.getProperty("clientId"))
                .setClientSecret(configProps.getProperty("clientSecret"))
                .setRedirectUri(redirectUri)
                .build();
        authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
                .state("x4xkmn9pu3j6ukrs8n")
                .scope("user-read-email,playlist-read-private,user-read-private,user-follow-read")
                .show_dialog(true)
                .build();
    }

    public static ArrayList grabPlaylists() {
        ArrayList results = new ArrayList();

        return results;
    }

    public static void refreshAccess(String refreshToken) {
        spotifyApi = new SpotifyApi.Builder()
                .setClientId(configProps.getProperty("clientId"))
                .setClientSecret(configProps.getProperty("clientSecret"))
                .setRefreshToken(refreshToken)
                .build();
        authorizationCodeRefreshRequest = spotifyApi.authorizationCodeRefresh()
                .build();

        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRefreshRequest.execute();

            // Set access and refresh token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static String getRefreshToken() {
        return authorizationCodeCredentials.getRefreshToken();
    }

    public static String getCurrentTrack() {
        final GetUsersCurrentlyPlayingTrackRequest getUsersCurrentlyPlayingTrackRequest = spotifyApi
                .getUsersCurrentlyPlayingTrack()
                .market(CountryCode.NA)
                .build();
        String track = null;

        try {
            final CurrentlyPlaying currentlyPlaying = getUsersCurrentlyPlayingTrackRequest.execute();
            track = currentlyPlaying.getItem().getName();
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return track;
    }

    public static HashMap<String, String> parseUserData() {
        User current_user = getCurrentUser(accessToken);
        HashMap<String, String> user_data =  new HashMap<>();

        user_data.put("username", current_user.getDisplayName());
        user_data.put("email", current_user.getEmail());
        user_data.put("follower_count", current_user.getFollowers().getTotal().toString());
        user_data.put("playlist_count", getPlaylistCount());

        System.out.println("Successfully mapped user data for rendering...");

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
            System.out.println("Successfully retrieved current user list of playlists!");
            count = playlistSimplifiedPaging.getTotal().toString();
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return count;
    }

    private static User getCurrentUser(String accessToken) {
        spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(accessToken)
                .build();
        getCurrentUsersProfileRequest = spotifyApi.getCurrentUsersProfile()
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
        spotifyApi = new SpotifyApi.Builder()
                .setClientId(configProps.getProperty("clientId"))
                .setClientSecret(configProps.getProperty("clientSecret"))
                .setRedirectUri(redirectUri)
                .build();
        try {
            authorizationCodeCredentials = authorizationCodeRequest.execute();
            // Set access and refresh token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            accessToken = authorizationCodeCredentials.getAccessToken();

            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
            refreshToken = authorizationCodeCredentials.getRefreshToken();

            System.out.println("Set token instance variables");
            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException e) {
            e.printStackTrace();
        }
    }

    public static String getAuthUri() {
        final URI uri = authorizationCodeUriRequest.execute();

        return uri.toString();
    }
}
