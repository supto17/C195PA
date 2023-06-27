package com.example.c195pa.controller;
import com.example.c195pa.dao.customerAccess;
import com.example.c195pa.dao.appointmentAccess;
import com.example.c195pa.main;
import com.example.c195pa.model.Customers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.c195pa.model.Appointments;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.time.LocalDate;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;

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
    public TableColumn<Appointments, LocalDate> apptDate;
    public TableColumn <?, ?> apptCustomerID;
    public TableColumn <?, ?> apptUserID;
    public Button addAppointment;
    public Button reportsButton;
    public TableView<Customers> allCustomersTable;
    public TableColumn<Object, Object> custID;
    public TableColumn<Object, Object> custName;
    public TableColumn<Object, Object> custAddress;
    public TableColumn<Object, Object> custPhoneNumber;
    public TableColumn<Object, Object> custState;
    public TableColumn<Object, Object> custPostalCode;
    public Button deleteAppointment;
    public Button addCustomer;
    public Button modifyCustomer;
    public Button deleteCustomer;
    public Button logoutButton;
    public Button updateAppointment;
    public Button exitButton;

    public void initialize() throws SQLException {

        // Create observable list for tableviews
        ObservableList<Appointments> allAppointments = appointmentAccess.getAllAppointments();
        ObservableList<Customers> allCustomers = customerAccess.getAllCustomers();

        // Load values from Appointments into appointments table view
        apptID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        apptTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptContact.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        apptType.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        apptStartTime.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptEndTime.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptDate.setCellValueFactory(new PropertyValueFactory<Appointments, LocalDate>("localDate"));
        apptCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        apptUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));

        // Load values from Customers into appointments table view
        custID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        custName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        custAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        custPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
        custState.setCellValueFactory(new PropertyValueFactory<>("Division_ID"));
        custPostalCode.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));


        // set items in table views
        allCustomersTable.setItems(allCustomers);
        allAppointmentsTable.setItems(allAppointments);

        // if appointments are within 15 minutes, notify the user
    }

    public void onReportsButtonClick(ActionEvent actionEvent) {
    }

    public void onActionAddAppointment(ActionEvent actionEvent) {
    }

    public void onActionUpdateAppointment(ActionEvent actionEvent) {
    }

    public void onActionAddCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(main.class.getResource("AddCustomer.fxml")));
        Stage stage = new Stage();
        stage.setTitle("Add Customer");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(addCustomer.getScene().getWindow());
        stage.showAndWait();
    }

    public void onActionModifyCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(main.class.getResource("ModifyCustomer.fxml")));
        Stage stage = new Stage();
        stage.setTitle("Modify Customer");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(addCustomer.getScene().getWindow());
        stage.showAndWait();
    }

    public void onActionDeleteCustomer(ActionEvent actionEvent) {
    }

    public void onLogoutButtonClick(ActionEvent actionEvent) {
    }

    public void onActionDeleteAppointment(ActionEvent actionEvent) {
    }

    public void onExitButton(ActionEvent actionEvent) {
    }
}
