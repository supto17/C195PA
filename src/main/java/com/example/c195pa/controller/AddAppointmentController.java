package com.example.c195pa.controller;

import com.example.c195pa.dao.appointmentAccess;
import com.example.c195pa.dao.contactsAccess;
import com.example.c195pa.dao.customerAccess;
import com.example.c195pa.dao.usersAccess;
import com.example.c195pa.main;
import javafx.collections.FXCollections;
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
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {

    @FXML
    private ComboBox<String> appointmentContactBox;

    @FXML
    private TextField appointmentDescriptionField;

    @FXML
    private TextField appointmentIDField;

    @FXML
    private TextField appointmentLocationField;

    @FXML
    private TextField appointmentTitleField;

    @FXML
    private TextField appointmentTypeField;

    @FXML
    private ComboBox<Integer> apptCustomerID;

    @FXML
    private DatePicker apptEndDatePicker;

    @FXML
    private ComboBox<String> apptEndTimeBox;

    @FXML
    private DatePicker apptStartDatePicker;

    @FXML
    private ComboBox<String> apptStartTimeBox;

    @FXML
    private ComboBox<Integer> apptUserID;

    public void toMainMenu (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(main.class.getResource("MainMenu.fxml")));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void cancelButtonClicked(ActionEvent event) throws IOException {
        toMainMenu(event);
    }

    @FXML
    void saveButtonClicked(ActionEvent event) {

        try {
            String title = appointmentTitleField.getText();
            String description = appointmentDescriptionField.getText();
            String location = appointmentLocationField.getText();
            String contact = appointmentContactBox.getSelectionModel().getSelectedItem().toString();
            String type = appointmentTypeField.getText();
            LocalDate startDate = apptStartDatePicker.getValue();
            LocalDate endDate = apptEndDatePicker.getValue();
            LocalTime startTime = LocalTime.parse(apptStartTimeBox.getValue());
            LocalTime endTime = LocalTime.parse(apptEndTimeBox.getValue());
            Integer customerID = apptCustomerID.getSelectionModel().getSelectedItem();
            Integer userID = apptUserID.getSelectionModel().getSelectedItem();

            int contactID = contactsAccess.getContactID(contact);

            boolean success = appointmentAccess.addAppointment(title, description, location, type, startDate, endDate, startTime, endTime , customerID, userID, contactID);

                if (success) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Appointment update successful");
                    alert.showAndWait();
                    toMainMenu(event);
                }
                else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("Failed to add " + appointmentTitleField.getText() + ".");
                        alert.showAndWait();
                }
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Invalid Entry");
            alert.setContentText("Please ensure all fields are filled out correctly.");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<String> appointmentTimes = FXCollections.observableArrayList();

        LocalTime firstAppointment = LocalTime.MIN.plusHours(8);
        LocalTime lastAppointment = LocalTime.MAX.minusHours(1).minusMinutes(45);

        //if statement fixed issue with infinite loop
            while (firstAppointment.isBefore(lastAppointment)) {
                appointmentTimes.add(String.valueOf(firstAppointment));
                firstAppointment = firstAppointment.plusMinutes(15);
            }
        apptStartTimeBox.setItems(appointmentTimes);
        apptEndTimeBox.setItems(appointmentTimes);
        try {
            appointmentContactBox.setItems(contactsAccess.getContactNames());
            apptCustomerID.setItems(customerAccess.getCustomerIDs());
            apptUserID.setItems(usersAccess.getUserIDs());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        // disables the weekend boxes on datepickers
        apptStartDatePicker.setDayCellFactory(dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(empty || item.getDayOfWeek() == DayOfWeek.SATURDAY || item.getDayOfWeek() == DayOfWeek.SUNDAY);
            }
        });
        apptEndDatePicker.setDayCellFactory(dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(empty || item.getDayOfWeek() == DayOfWeek.SATURDAY || item.getDayOfWeek() == DayOfWeek.SUNDAY);
            }
        });
    }
}


