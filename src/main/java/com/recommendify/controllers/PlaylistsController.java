package com.recommendify.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.recommendify.models.Playlist;

import java.util.ArrayList;

/**
 * Controller class for Playlists List view
 *
 * @author Sam Lim
 */
public class PlaylistsController {
    private ArrayList<Playlist> playlistsArray;

    @FXML
    private TableView<Playlist> playlistTable;
    @FXML
    private TableColumn<Playlist, String> playlistCol;
    @FXML
    private TableColumn<Playlist, String> trackCtCol;
    @FXML
    private TableColumn<Playlist, String> actionsCol;

    @FXML
    private void initialize() {
        populateTable();
    }

    private void populateTable() {
        playlistCol.setCellValueFactory(new PropertyValueFactory<>("playlistName"));
        trackCtCol.setCellValueFactory(new PropertyValueFactory<>("playlistTracksCount"));

        ObservableList<Playlist> playlistsOL = FXCollections.observableArrayList(playlistsArray);
        playlistTable.setItems(playlistsOL);
    }

    public void setPlaylistsArray(ArrayList<Playlist> playlistsArray) {
        this.playlistsArray = playlistsArray;
    }
}
