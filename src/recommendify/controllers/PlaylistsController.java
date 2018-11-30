package recommendify.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import recommendify.models.Playlist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller class for Playlists List view
 *
 * @author Sam Lim
 */
public class PlaylistsController {
    private HashMap data;
    private ArrayList<Playlist> playlistsArray;

    @FXML
    private TableView<Object> playlistTable;
    @FXML
    private TableColumn<Object, String> playlistCol;
    @FXML
    private TableColumn trackCtCol;
    @FXML
    private TableColumn actionsCol;

    @FXML
    private void initialize() {
        populateTable(data);
    }

    private void populateTable(HashMap data) {

    }

    public void setPlaylistsArray(ArrayList<Playlist> playlistsArray) {
        this.playlistsArray = playlistsArray;
    }
}
