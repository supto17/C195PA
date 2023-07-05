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
import java.sql.Timestamp;
import java.time.*;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Add appointment controller
 *
 * @author Spencer Upton
 */
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
     * When user clicks the cancel button, the toMainMenu function is called and the user is returned to the Main Menu
     * @param event cancel button clicked
     * @throws IOException if main menu is not found
     */
    @FXML
    void cancelButtonClicked(ActionEvent event) throws IOException {
        toMainMenu(event);
    }

    /**
     * When the user clicks the save button, all entered data is parsed and sent to the addAppointment function.
     * Once there, the appointment is then sent to validateAppointment to ensure all information is entered correctly
     * and that there is no overlap with other appointments. If the update works, the user is returned to the Main Menu.
     * @param event save button clicked
     */
    @FXML
    void saveButtonClicked(ActionEvent event) {

        try {
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
            LocalDateTime l = LocalDateTime.now();

            int contactID = contactsAccess.getContactID(contact);

            Appointments a = new Appointments(title, description, location, type, startTime, endTime, startDate, endDate, customerID, userID,
                        contactID, contact, l);

            boolean success = appointmentAccess.addAppointment(a);

                if (success) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Appointment added successfully!");
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

    /**
     * Initialize method populates the appointment start and end time boxes with only dates that could be within business hours.
     * Also populates the contact box, customer ID box, and the user id box.
     * I have also written a lambda function to ensure the user cannot schedule an appointment before today.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<String> appointmentTimes = FXCollections.observableArrayList();

        LocalTime firstAppointment = LocalTime.MIN.plusHours(8);
        LocalTime lastAppointment = LocalTime.MAX.minusHours(1).minusMinutes(45);

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

        /**
         * LAMBDA function that prevents user from selecting start day before today as the user should not be scheduling
         * appointments before the current day
         */
        apptStartDatePicker.setDayCellFactory(apptStartDatePicker -> new DateCell() {
            public void updateItem(LocalDate apptStartDatePicker, boolean empty) {
                super.updateItem(apptStartDatePicker, empty);
                setDisable(
                      empty || apptStartDatePicker.isBefore(LocalDate.now()));
            }
        });

        /**
         * LAMBDA function that prevents user from selecting start day before today as the user should not be scheduling
         * appointments before the current day
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


