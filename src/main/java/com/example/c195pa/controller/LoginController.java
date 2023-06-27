package com.example.c195pa.controller;

import com.example.c195pa.dao.appointmentAccess;
import com.example.c195pa.main;
import com.example.c195pa.helper.JDBC;
import com.example.c195pa.model.Appointments;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;

public class LoginController {
    public TextField loginUsername;
    public TextField loginPassword;
    public Button loginButton;
    public Button cancelButton;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void loginButton(ActionEvent actionEvent) {
    }

    public void cancelButton(ActionEvent actionEvent) {
    }

    public void onActionLoginButton(ActionEvent actionEvent) throws IOException, SQLException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(main.class.getResource("MainMenu.fxml")));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();

        // pull current time
        LocalDateTime now = LocalDateTime.now();

        // pull all appointments
        ObservableList<Appointments> allAppointments = appointmentAccess.getAllAppointments();

        for (Appointments a : allAppointments) {

            // alert user if appointment is within 15 minutes
            // otherwise inform the user if there are no upcoming appointments

            if (a.getDateTime().isAfter(now) && a.getDateTime().isBefore(now.plusMinutes(15))) {
                System.out.println("Data " + a.getAppointmentID() + " is in time.");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Appointment Alert");
                alert.setContentText("Appointment with " + a.getCustomerID() + " at " + a.getStart());
                alert.showAndWait();
                break;
            }
            else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Appointment Alert");
                alert.setContentText("No upcoming appointments");
                alert.showAndWait();
            }
        }
    }

    public void onActionCancelButton(ActionEvent actionEvent) {
        JDBC.closeConnection();
        System.exit(0);
    }
}