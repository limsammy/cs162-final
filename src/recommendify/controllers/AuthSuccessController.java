package recommendify.controllers;

import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import recommendify.helpers.SpotifyApiHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Controller class for AuthSuccess view
 *
 * @author Sam Lim
 */
public class AuthSuccessController {
    private SpotifyApiHelper spotifyService = new SpotifyApiHelper();

    @FXML
    private Label username;
    @FXML
    private Label email;
    @FXML
    private Label followerCt;
    @FXML
    private Label playlistCt;

    @FXML
    private void initialize() {
        HashMap<String, String> user_data = spotifyService.parseUserData();

        username.setText(user_data.get("username"));
        email.setText(user_data.get("email"));
        followerCt.setText(user_data.get("follower_count"));
        playlistCt.setText(user_data.get("playlist_count"));
    }

    public void pressViewPlaylistsBtn(ActionEvent e) {
        System.out.println("Fetching playlists (doin lotsa magic)...");
        HashMap<String, Object> playlists = buildPlaylistsList(spotifyService.grabPlaylists());
        System.out.println("# of playlists mined for data: " + playlists.size());

        System.out.println("Rendering playlist table view and closing other window...");
        ((Button)e.getTarget()).getScene().getWindow().hide();
        try {
            renderDataTable(e, playlists);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void renderDataTable(ActionEvent e, HashMap<String, Object> data) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent tableViewParent = loader.load(getClass().getResource("../resources/playlists_list.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();

        window.setResizable(false);

        window.setScene(tableViewScene);
        window.show();
    }

    private HashMap<String, Object> buildPlaylistsList(ArrayList<PlaylistSimplified> rawPlaylists) {
        HashMap<String, Object> results = new HashMap<>();

        for (PlaylistSimplified playlist : rawPlaylists) {
            HashMap<String, Object> dataBundle = new HashMap<>();

            dataBundle.put("name", playlist.getName());
            dataBundle.put("id", playlist.getId());
            dataBundle.put("tracksList", playlist.getTracks());
            dataBundle.put("tracksCount", playlist.getTracks().getTotal());
            dataBundle.put("images", playlist.getImages());
            dataBundle.put("uri", playlist.getUri());

            results.put(playlist.getName(), dataBundle);
        }

        System.out.println("Extracted necessary elements from playlist objects...");
        System.out.println("Returning compiled hashmap of data!");

        return results;
    }
}
