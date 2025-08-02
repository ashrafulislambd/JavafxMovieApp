package com.lab4;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.Node;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MovieController {
    Connection conn;
    Boolean flag = true;

    @FXML
    TilePane tileMovies;

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

        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM MOVIE");
        ArrayList<Integer> movieIds = new ArrayList<Integer>();

        while(rs.next()) {
            movieIds.add(rs.getInt("ID"));
        }

        //if(1==0)
        for(int movieId : movieIds ) {
            System.out.println(rs.getString("TITLE"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("card.fxml"));
            VBox card = loader.load();
            CardController controller = loader.getController();
            controller.loadData(movieId, conn);
            tileMovies.getChildren().add(card);
        }
    }

    @FXML
    void btnConnect_Click() throws Exception {
        System.out.println("Hello");
    }
}
