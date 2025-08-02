package com.lab4;

import java.sql.Connection;
import java.sql.ResultSet;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class CardController {
    @FXML
    Label lblTitle;

    @FXML
    ImageView imgThumbnail;

    void loadData(int movieId, Connection conn) throws Exception {
        String query = "SELECT * FROM MOVIE WHERE ID=" + movieId;
        ResultSet rs = conn.createStatement().executeQuery(query);

        if(rs.next()) {
            String title = rs.getString("TITLE");
            lblTitle.setText(title);
        }
    }
}
