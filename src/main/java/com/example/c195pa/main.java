package com.example.c195pa;

import com.example.c195pa.dao.customerAccess;
import com.example.c195pa.helper.JDBC;
import com.example.c195pa.model.Appointments;
import com.example.c195pa.model.Customers;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;

public class main extends Application {
    /**
     * Loads the login screen
     * @param stage login screen
     * @throws IOException if login screen is not found
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 428, 368);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Starts the program
     * @param args args
     */
    public static void main(String[] args) {

        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();

    }
}