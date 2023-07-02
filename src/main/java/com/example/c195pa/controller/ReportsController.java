package com.example.c195pa.controller;

import com.example.c195pa.dao.appointmentAccess;
import com.example.c195pa.dao.contactsAccess;
import com.example.c195pa.dao.fldAccess;
import com.example.c195pa.main;
import com.example.c195pa.model.Appointments;
import com.example.c195pa.model.FirstLevelDivisions;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class ReportsController  implements Initializable {
    @FXML
    public TableColumn<Object, Object> apptStartDate;
    @FXML
    public TableColumn apptEndDate;
    @FXML
    public TableColumn<Object, Object> apptStartTime;
    @FXML
    public TableColumn<Object, Object> apptEndTime;
    @FXML
    public TableColumn<Object, Object> apptUserID;
    public TableView<FirstLevelDivisions> customersByDivisionTable;
    @FXML
    private TableView<Appointments> appointmentByMonth;

    @FXML
    private TableColumn<?, ?> appointmentMonth;

    @FXML
    private TableColumn<?, ?> appointmentTypeByMonth;

    @FXML
    private TableView<Appointments> appointmentView;

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
    private ComboBox<String> customerComboBox;

    @FXML
    private TableColumn<?, ?> divisionColumn;

    @FXML
    private Button logoutButton;

    @FXML
    private TableColumn<?, ?> totalAppointments;

    @FXML
    private TableColumn<?, ?> totalCustomers;

    public void switchScreen (ActionEvent event, String path, String title) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(main.class.getResource(path)));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void backButtonClicked(ActionEvent event) throws IOException {
        switchScreen(event, "MainMenu.fxml", "Main Menu");
    }

    @FXML
    void customerComboBoxSelection(ActionEvent event) {

    }

    @FXML
    void logoutButtonClicked(ActionEvent event) throws IOException {
        switchScreen(event, "login.fxml", "Login");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Appointments> appointmentsByMonth = null;
        ObservableList<FirstLevelDivisions> customersByDivision = null;
        try {
            customerComboBox.setItems(contactsAccess.getContactNames());
            appointmentsByMonth = appointmentAccess.populateAppointmentsByMonth();
            customersByDivision = fldAccess.getCustomersByDivision();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        /**
         * LAMBDA function that updates the appointment view when a new contact is selected.
         */
        customerComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) {
                appointmentView.getItems().clear();
            }
            else {
                try {
                    ObservableList<Appointments> appointmentByContact = appointmentAccess.getAppointmentsByContact(customerComboBox.getValue());
                    apptID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
                    apptTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
                    apptDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
                    apptLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
                    apptType.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
                    apptStartTime.setCellValueFactory(new PropertyValueFactory<>("start"));
                    apptEndTime.setCellValueFactory(new PropertyValueFactory<>("end"));
                    apptStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
                    apptCustID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
                    apptUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
                    appointmentView.setItems(appointmentByContact);

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
            // Set table for appointments by month
            appointmentMonth.setCellValueFactory(new PropertyValueFactory<>("Month"));
            appointmentTypeByMonth.setCellValueFactory(new PropertyValueFactory<>("Type"));
            totalAppointments.setCellValueFactory(new PropertyValueFactory<>("Count"));
            appointmentByMonth.setItems(appointmentsByMonth);

        /**
         * Custom report for total customers by division
         */

            divisionColumn.setCellValueFactory(new PropertyValueFactory<>("DivisionName"));
            totalCustomers.setCellValueFactory(new PropertyValueFactory<>("TotalCustomers"));
            customersByDivisionTable.setItems(customersByDivision);
    }
}

