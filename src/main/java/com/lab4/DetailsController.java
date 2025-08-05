package com.lab4;

import java.sql.Connection;
import java.sql.ResultSet;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class DetailsController {
    Scene parentScene;

    Connection conn;

    @FXML
    Button btnBack;

    @FXML
    ToggleButton btnWatchLater;

    @FXML
    Label lblTitle, lblDesc;

    @FXML
    ImageView imgThumbnail;

    int movieId;

    public void setParentScene(Scene scene) {
        this.parentScene = scene;
    }

    public void loadData(Connection conn, int movieId) throws Exception {
        this.conn = conn;
        this.movieId = movieId;

        String query = "SELECT * FROM MOVIE WHERE ID=" + movieId;
        ResultSet rs = conn.createStatement().executeQuery(query);

        if(rs.next()) {
            String title = rs.getString("TITLE");
            String desc = "Description:\n";
            String imgPath = rs.getString("IMG_PATH");
            Boolean watchLater = rs.getBoolean("WATCH_LATER");

            lblTitle.setText(title);
            String queryCast = "SELECT * FROM CAST WHERE MOVIE_ID=" + movieId;
            ResultSet rsCast = conn.createStatement().executeQuery(queryCast);

            desc += "\nCast: \n";
            while(rsCast.next()) {
                desc += rsCast.getString("ORIGINAL_NAME")
                    + " as " + rsCast.getString("CAST_NAME") + "\n";
            }

            imgThumbnail.setImage(new Image(getClass().getResourceAsStream(imgPath)));
            lblDesc.setText(desc);
            btnWatchLater.setSelected(watchLater);
        }
    }

    @FXML
    void btnBack_Click() {
        Stage thisStage = (Stage)btnBack.getScene().getWindow();
        thisStage.setScene(parentScene);
    }

    @FXML
    void btnWatchLater_Click() {
        int watchLater = btnWatchLater.isSelected() ? 1 : 0;
        String query = "UPDATE MOVIE SET WATCH_LATER=" + watchLater + 
            " WHERE ID=" + movieId;
        try {
            conn.createStatement().executeUpdate(query);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
