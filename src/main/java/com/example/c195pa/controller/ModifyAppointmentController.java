package com.example.c195pa.controller;

import com.example.c195pa.dao.appointmentAccess;
import com.example.c195pa.dao.contactsAccess;
import com.example.c195pa.dao.customerAccess;
import com.example.c195pa.dao.usersAccess;
import com.example.c195pa.main;
import com.example.c195pa.model.Appointments;
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
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Modify appointment controller
 *
 * @author Spencer Upton
 */

public class ModifyAppointmentController implements Initializable {
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

    /**
     * Simple function to return the user to the main menu
     * @param event user attempts to go to main menu
     * @throws IOException if main menu is not found
     */
    public void toMainMenu (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(main.class.getResource("MainMenu.fxml")));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * send the selected appointment from the main screen and populates modify appointment
     * with the data.
     * @param selected appointment selected in the main menu appointment table view
     * @throws SQLException if any query called returns an error
     */
    public void sendAppointment(Appointments selected) throws SQLException {
        appointmentIDField.setText(Integer.toString(selected.getAppointmentID()));
        appointmentTitleField.setText(selected.getTitle());
        appointmentDescriptionField.setText(selected.getDescription());
        appointmentLocationField.setText(selected.getLocation());
        appointmentContactBox.setItems(contactsAccess.getContactNames());
        appointmentContactBox.getSelectionModel().select(selected.getContact());
        appointmentTypeField.setText(selected.getAppointmentType());
        apptStartDatePicker.setValue(selected.getStartDate());
        apptEndDatePicker.setValue(selected.getEndDate());
        apptStartTimeBox.setValue(selected.getStart().toString());
        apptEndTimeBox.setValue(selected.getEnd().toString());
        apptCustomerID.setItems(customerAccess.getCustomerIDs());
        apptCustomerID.setValue(selected.getCustomerID());
        apptUserID.setItems(usersAccess.getUserIDs());
        apptUserID.setValue(selected.getUserID());
    }

    /**
     * Returns the user to main menu screen
     * @param event cancel button clicked
     * @throws IOException if main menu screen is not found
     */
    @FXML
    void cancelButtonClicked(ActionEvent event) throws IOException {
        toMainMenu(event);
    }

    /**
     * Gets the text from the text fields, and send the information to the updateAppointment function.
     * If that function returns true, the appointment is updated and the user is returned to the main screen.
     * @param event save button clicked
     * @throws SQLException if the appointment is not able to be added to the database
     */
    @FXML
    void saveButtonClicked(ActionEvent event) throws SQLException {

        try {
            int id = Integer.parseInt(appointmentIDField.getText());
            String title = appointmentTitleField.getText();
            String description = appointmentDescriptionField.getText();
            String location = appointmentLocationField.getText();
            String contact = appointmentContactBox.getSelectionModel().getSelectedItem();
            String type = appointmentTypeField.getText();
            LocalDate startDate = apptStartDatePicker.getValue();
            LocalDate endDate = apptEndDatePicker.getValue();
            LocalTime startTime = LocalTime.parse(apptStartTimeBox.getSelectionModel().getSelectedItem());
            LocalTime endTime = LocalTime.parse(apptEndTimeBox.getSelectionModel().getSelectedItem());
            int customerID = apptCustomerID.getSelectionModel().getSelectedItem();
            int userID = apptUserID.getSelectionModel().getSelectedItem();
            LocalDateTime dateTime = LocalDateTime.of(startDate,startTime);

            Appointments a = new Appointments(id, title, description,location,type,startTime,endTime,
                              startDate, endDate, customerID,userID,contactsAccess.getContactID(contact),contact , dateTime);

            boolean success = appointmentAccess.updateAppointment(a);

            if (success) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Appointment ID " + a.getAppointmentID() + " was successfully modified!");
                alert.showAndWait();
                toMainMenu(event);
            }    else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Failed to add " + appointmentTitleField.getText() + ".");
                alert.showAndWait();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialize method populates the appointment start and end time boxes with only dates that could be within business hours.
     * Also populates the contact box, customer ID box, and the user id box.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
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

        /**
         * LAMBDA function that prevents user from selecting start day before today
         */
        apptStartDatePicker.setDayCellFactory(apptStartDatePicker -> new DateCell() {
            public void updateItem(LocalDate apptStartDatePicker, boolean empty) {
                super.updateItem(apptStartDatePicker, empty);
                setDisable(
                        empty || apptStartDatePicker.isBefore(LocalDate.now()));
            }
        });

        /**
         * Lambda function that prevents user from selecting end day before today
         */
        apptEndDatePicker.setDayCellFactory(apptEndDatePicker -> new DateCell() {
            public void updateItem(LocalDate apptEndDatePicker, boolean empty) {
                super.updateItem(apptEndDatePicker, empty);
                setDisable(
                        empty || apptEndDatePicker.isBefore(LocalDate.now()));
            }
        });

    }
}

