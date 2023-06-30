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

    public void toMainMenu (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(main.class.getResource("MainMenu.fxml")));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    public void sendAppointment(Appointments selected) throws SQLException {
        appointmentIDField.setText(Integer.toString(selected.getAppointmentID()));
        appointmentTitleField.setText(selected.getTitle());
        appointmentDescriptionField.setText(selected.getDescription());
        appointmentLocationField.setText(selected.getLocation());
        appointmentContactBox.setItems(contactsAccess.getContactNames());
        appointmentContactBox.getSelectionModel().select(selected.getContact());
        appointmentTypeField.setText(selected.getAppointmentType());
        apptStartDatePicker.setValue(selected.getLocalDate());
        apptEndDatePicker.setValue(selected.getLocalDate());
        apptStartTimeBox.setValue(selected.getStart().toString());
        apptEndTimeBox.setValue(selected.getEnd().toString());
        apptCustomerID.setItems(customerAccess.getCustomerIDs());
        apptCustomerID.setValue(selected.getCustomerID());
        apptUserID.setItems(usersAccess.getUserIDs());
        apptUserID.setValue(selected.getUserID());
    }

    @FXML
    void cancelButtonClicked(ActionEvent event) throws IOException {
        toMainMenu(event);
    }

    @FXML
    void saveButtonClicked(ActionEvent event) throws SQLException {

        try {
            int id = Integer.parseInt(appointmentIDField.getText());
            String title = appointmentTitleField.getText();
            String description = appointmentDescriptionField.getText();
            String location = appointmentLocationField.getText();
            String contact = appointmentContactBox.getSelectionModel().getSelectedItem();
            String type = appointmentTypeField.getText();
            LocalDate date = apptStartDatePicker.getValue();
            LocalTime startTime = LocalTime.parse(apptStartTimeBox.getSelectionModel().getSelectedItem());
            LocalTime endTime = LocalTime.parse(apptEndTimeBox.getSelectionModel().getSelectedItem());
            int customerID = apptCustomerID.getSelectionModel().getSelectedItem();
            int userID = apptUserID.getSelectionModel().getSelectedItem();
            LocalDateTime l = LocalDateTime.now();

            Appointments a = new Appointments(id, title, description,location,type,startTime,endTime,
                              date,customerID,userID,contactsAccess.getContactID(contact),contact,l);

            boolean success = appointmentAccess.updateAppointment(a);
            if (success) {
                toMainMenu(event);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
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

