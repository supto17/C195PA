package com.example.c195pa.controller;

import com.example.c195pa.dao.appointmentAccess;
import com.example.c195pa.main;
import com.example.c195pa.helper.JDBC;
import com.example.c195pa.model.Appointments;
import com.example.c195pa.model.Logon;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public TextField loginUsername;
    public TextField loginPassword;
    public Button loginButton;
    public Button cancelButton;
    public Label zoneLabel;
    public RadioButton frenchButton;
    public ToggleGroup languageGroup;
    public RadioButton englishButton;
    @FXML
    private Label welcomeText;

    public void onActionLoginButton(ActionEvent actionEvent) throws IOException, SQLException {


        String username = loginUsername.getText();
        String password = loginPassword.getText();

        boolean logon = Logon.loginAttempt(username, password);
        System.out.println(logon);

        if (logon) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(main.class.getResource("MainMenu.fxml")));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Main Menu");
            stage.setScene(scene);
            stage.show();

            // pull current time
            LocalDateTime now = LocalDateTime.now();

            // pull all appointments
            ObservableList<Appointments> allAppointments = appointmentAccess.getAllAppointments();

            boolean found = false;
            for (Appointments a : allAppointments) {

                // alert user if appointment is within 15 minutes
                // otherwise inform the user if there are no upcoming appointments

                if (a.getDateTime().isAfter(now) && a.getDateTime().isBefore(now.plusMinutes(15))) {
                    found = true;
                    System.out.println("Data " + a.getAppointmentID() + " is in time.");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Appointment Alert");
                    alert.setHeaderText("Upcoming Appointment");
                    alert.setContentText("Appointment with " + a.getCustomerID() + " at " + a.getStart());
                    alert.showAndWait();

                    }
                }
            if (!found) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appointment Alert");
                alert.setHeaderText("No upcoming appointments");
                alert.setContentText("You have no upcoming appointments");
                alert.showAndWait();
            }
        }   else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Logon");
            alert.setHeaderText("Invalid Username or Password");
            alert.setContentText("Please try again.");
            alert.showAndWait();
        }


    }

    public void onActionCancelButton(ActionEvent actionEvent) {
        JDBC.closeConnection();
        System.exit(0);
    }

    public void onActionFrenchButtonPressed(ActionEvent actionEvent) {

    }

    public void onActionEnglishButtonPressed(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Locale userLocale = Locale.getDefault();
        zoneLabel.setText(ZoneId.systemDefault().toString());
    }

}