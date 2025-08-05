package com.lab4;

import java.sql.Connection;
import java.sql.ResultSet;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

public class CardController {
    @FXML
    Label lblTitle, lblYear, lblVotes, lblRating;

    @FXML
    ImageView imgThumbnail;

    @FXML
    TilePane tileGenres;

    void loadData(int movieId, Connection conn) throws Exception {
        String query = "SELECT * FROM MOVIE WHERE ID=" + movieId;
        ResultSet rs = conn.createStatement().executeQuery(query);

        if(rs.next()) {
            String title = rs.getString("TITLE");
            String imgPath = rs.getString("IMG_PATH");
            int year = rs.getInt("YEAR");
            float rating = rs.getFloat("RATING");
            int votes = rs.getInt("VOTE");

            imgThumbnail.setImage(new Image(getClass().getResourceAsStream(imgPath)));
            lblTitle.setText(title);
            lblYear.setText(String.valueOf(year));
            lblRating.setText("⭐ " + String.format("%.1f", rating));
            lblVotes.setText(votes + " votes");

            String tagQuery = "SELECT g.GENRE_NAME FROM MOVIE_GENRE mg, GENRE g WHERE g.ID=mg.GENRE_ID AND mg.MOVIE_ID=" + movieId;
            ResultSet rsTags = conn.createStatement().executeQuery(tagQuery);

            tileGenres.getChildren().clear();

            while(rsTags.next()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("tag.fxml"));
                Label tag = loader.load();
                tag.setText(rsTags.getString("GENRE_NAME"));
                tileGenres.getChildren().add(tag);
            }
        }
    }
}
