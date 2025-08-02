module com.lab4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;

    opens com.lab4 to javafx.fxml;
    exports com.lab4;
}
