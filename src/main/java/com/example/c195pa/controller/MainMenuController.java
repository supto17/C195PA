package com.example.c195pa.controller;

import com.example.c195pa.dao.appointmentAccess;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.c195pa.model.Appointments;

import java.sql.SQLException;

public class MainMenuController {

    public TableView <Appointments> allAppointmentsTable;
    public TableColumn <?, ?> apptID;
    public TableColumn <?, ?> apptTitle;
    public TableColumn <?, ?> apptDescription;
    public TableColumn <?, ?> apptLocation;
    public TableColumn <?, ?> apptContact;
    public TableColumn<?, ?> apptType;
    public TableColumn <?, ?> apptStartTime;
    public TableColumn <?, ?> apptEndTime;
    public TableColumn <?, ?> apptStartDate;
    public TableColumn <?, ?> apptEndDate;
    public TableColumn <?, ?> apptCustomerID;
    public TableColumn <?, ?> apptUserID;
    public ToggleGroup viewSort;
    public Button addAppointment;
    public Button modifyAppointment;
    public Button customerButton;
    public Button reportsButton;

    public void initialize() throws SQLException {
        ObservableList<Appointments> allAppointments = appointmentAccess.getAllAppointments();

        for (Appointments a : allAppointments) {
            System.out.println(a.getAppointmentType());
        }

        apptID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        apptTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptContact.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        apptType.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        apptStartTime.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptEndTime.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        apptUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));


        allAppointmentsTable.setItems(allAppointments);

    }

    public void onActionViewByMonth(ActionEvent actionEvent) {
    }

    public void onActionViewByWeek(ActionEvent actionEvent) {
    }

    public void onActionViewAll(ActionEvent actionEvent) {
    }

    public void onActionAddAppointmentButton(ActionEvent actionEvent) {
    }

    public void onActionModifyAppointment(ActionEvent actionEvent) {
    }
    public void onCustomerButtonClick(ActionEvent actionEvent) {
    }

    public void onReportsButtonClick(ActionEvent actionEvent) {
    }
}
