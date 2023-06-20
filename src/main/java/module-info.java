module com.example.c195pa {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens com.example.c195pa to javafx.fxml;
    exports com.example.c195pa;
    exports controller;
    opens controller to javafx.fxml;
    exports helper;
    opens helper to javafx.fxml;
}