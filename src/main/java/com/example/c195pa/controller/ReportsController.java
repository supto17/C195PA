package com.example.c195pa.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportsController  implements Initializable {

    @FXML
    private TableView<?> appointmentByMonth;

    @FXML
    private TableColumn<?, ?> appointmentMonth;

    @FXML
    private TableColumn<?, ?> appointmentTypeByMonth;

    @FXML
    private TableView<?> appointmentView;

    @FXML
    private TableColumn<?, ?> apptCustID;

    @FXML
    private TableColumn<?, ?> apptDescription;

    @FXML
    private TableColumn<?, ?> apptEnd;

    @FXML
    private TableColumn<?, ?> apptID;

    @FXML
    private TableColumn<?, ?> apptLocation;

    @FXML
    private TableColumn<?, ?> apptStart;

    @FXML
    private TableColumn<?, ?> apptTitle;

    @FXML
    private TableColumn<?, ?> apptType;

    @FXML
    private Button backButton;

    @FXML
    private ComboBox<?> customerComboBox;

    @FXML
    private TableColumn<?, ?> divisionColumn;

    @FXML
    private Button logoutButton;

    @FXML
    private TableColumn<?, ?> totalAppointments;

    @FXML
    private TableColumn<?, ?> totalCustomers;

    @FXML
    void backButtonClicked(ActionEvent event) {

    }

    @FXML
    void customerComboBoxSelection(ActionEvent event) {

    }

    @FXML
    void logoutButtonClicked(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

