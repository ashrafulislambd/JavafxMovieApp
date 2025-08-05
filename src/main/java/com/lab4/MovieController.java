package com.lab4;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MovieController {
    Connection conn;
    Boolean flag = true;

    @FXML
    TilePane tileMovies;

    @FXML
    TextField txtSearch;

    @FXML
    void initialize() throws Exception {
        if(flag == false) {
            return;
        }
        flag = true;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:movie.db");
            System.out.println("Connection was successful");
        } catch(Exception e) {
            System.out.println("Connection failed");
        }

        loadResult("SELECT * FROM MOVIE");

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            txtSearch_Change();
        });
    }

    private void loadResult(String query) throws Exception {
        ResultSet rs = conn.createStatement().executeQuery(query);
        ArrayList<Integer> movieIds = new ArrayList<Integer>();

        while(rs.next()) {
            movieIds.add(rs.getInt("ID"));
        }

        tileMovies.getChildren().clear();
        for(int movieId : movieIds) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("card.fxml"));
            VBox card = loader.load();
            CardController controller = loader.getController();
            card.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                try {
                    System.out.println(movieId);
                    FXMLLoader detailsLoader = new FXMLLoader(getClass().getResource("details.fxml"));
                    Scene detailsPage = new Scene(detailsLoader.load());
                    DetailsController detailsController = detailsLoader.getController();
                    detailsController.setParentScene(card.getScene());
                    detailsController.loadData(conn, movieId);
                    Stage windowStage = (Stage)card.getScene().getWindow();
                    windowStage.setScene(detailsPage);
                } catch(Exception e) {

                }
            });
            controller.loadData(movieId, conn);
            tileMovies.getChildren().add(card);
        }
    }

    private void loadResultBySearch(String query) throws Exception {
        ResultSet rs;
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM MOVIE WHERE TITLE LIKE ?");
        stmt.setString(1, "%" + query + "%");
        ArrayList<Integer> movieIds = new ArrayList<Integer>();

        rs = stmt.executeQuery();
        while(rs.next()) {
            movieIds.add(rs.getInt("ID"));
        }

        tileMovies.getChildren().clear();
        for(int movieId : movieIds) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("card.fxml"));
            VBox card = loader.load();
            CardController controller = loader.getController();
            card.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                try {
                    System.out.println(movieId);
                    FXMLLoader detailsLoader = new FXMLLoader(getClass().getResource("details.fxml"));
                    Scene detailsPage = new Scene(detailsLoader.load());
                    DetailsController detailsController = detailsLoader.getController();
                    detailsController.setParentScene(card.getScene());
                    detailsController.loadData(conn, movieId);
                    Stage windowStage = (Stage)card.getScene().getWindow();
                    windowStage.setScene(detailsPage);
                } catch(Exception e) {

                }
            });
            controller.loadData(movieId, conn);
            tileMovies.getChildren().add(card);
        }
    }

    @FXML
    void btnConnect_Click() throws Exception {
        System.out.println("Hello");
    }

    @FXML
    void txtSearch_Change() {
        String queryText = txtSearch.getText();
        if(queryText.isEmpty()) {
            try {
                loadResult("SELECT * FROM MOVIE");
            } catch(Exception e) {
                System.err.println(e.getMessage());
            }
        } else {
            try {
                loadResultBySearch(queryText);
            } catch(Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
