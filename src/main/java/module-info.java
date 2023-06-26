module com.example.c195pa {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens com.example.c195pa to javafx.fxml;
    exports com.example.c195pa;
    exports com.example.c195pa.controller;
    opens com.example.c195pa.controller to javafx.fxml;
    exports com.example.c195pa.helper;
    opens com.example.c195pa.helper to javafx.fxml;
<<<<<<< Updated upstream
    exports com.example.c195pa.model;
    opens com.example.c195pa.model to javafx.fxml;
=======
>>>>>>> Stashed changes
}