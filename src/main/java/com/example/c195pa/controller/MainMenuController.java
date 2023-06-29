package com.example.c195pa.controller;
import com.example.c195pa.dao.customerAccess;
import com.example.c195pa.dao.appointmentAccess;
import com.example.c195pa.helper.JDBC;
import com.example.c195pa.main;
import com.example.c195pa.model.Customers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.c195pa.model.Appointments;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    @FXML
    public TableView <Appointments> allAppointmentsTable;
    @FXML
    public TableColumn <Appointments, Integer> apptID;
    @FXML
    public TableColumn <Appointments, String> apptTitle;
    @FXML
    public TableColumn <Appointments, String> apptDescription;
    @FXML
    public TableColumn <Appointments, String> apptLocation;
    @FXML
    public TableColumn <Appointments, String> apptContact;
    @FXML
    public TableColumn<Appointments, String> apptType;
    @FXML
    public TableColumn <Appointments, LocalTime> apptStartTime;
    @FXML
    public TableColumn <Appointments, LocalTime> apptEndTime;
    @FXML
    public TableColumn<Appointments, LocalDate> apptDate;
    @FXML
    public TableColumn <Appointments, Integer> apptCustomerID;
    @FXML
    public TableColumn <Appointments, Integer> apptUserID;
    @FXML
    public Button addAppointment;
    @FXML
    public Button reportsButton;
    @FXML
    public TableView<Customers> allCustomersTable;
    @FXML
    public TableColumn<Customers, Integer> custID;
    @FXML
    public TableColumn<Customers, String> custName;
    @FXML
    public TableColumn<Customers, String> custAddress;
    @FXML
    public TableColumn<Customers, String> custPhoneNumber;
    @FXML
    public TableColumn<Customers, String> custState;
    @FXML
    public TableColumn<Customers, String> custCountry;
    @FXML
    public TableColumn<Customers, String> custPostalCode;
    @FXML
    public Button deleteAppointment;
    @FXML
    public Button addCustomer;
    @FXML
    public Button modifyCustomer;
    @FXML
    public Button deleteCustomer;
    @FXML
    public Button logoutButton;
    @FXML
    public Button updateAppointment;
    @FXML
    public Button exitButton;


    public void switchScreen(ActionEvent event, String path, String title) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(main.class.getResource(path)));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public void createWarningAlert(String title, String header, String context) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
    }



    public void onReportsButtonClick(ActionEvent actionEvent) throws IOException {
        switchScreen(actionEvent, "Reports.fxml", "Reports");
    }

    public void onActionAddAppointment(ActionEvent actionEvent) throws IOException {
        switchScreen(actionEvent, "AddAppointment.fxml", "Add Appointment");
    }

    public void onActionUpdateAppointment(ActionEvent actionEvent) throws IOException, SQLException {
        Appointments selected = allAppointmentsTable.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(main.class.getResource("ModifyAppointment.fxml"));
        loader.load();

        if (selected == null) {
            createWarningAlert("Invalid Selection", "Please select something", "Select an appointment you wish to edit");
        } else {

            ModifyAppointmentController m = loader.getController();
            m.sendAppointment(selected);

            Parent root = FXMLLoader.load(Objects.requireNonNull(main.class.getResource("ModifyAppointment.fxml")));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Appointment");
            stage.setScene(scene);
            stage.show();;
        }
    }
    public void onActionDeleteAppointment(ActionEvent actionEvent) {
        Appointments selected = allAppointmentsTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            createWarningAlert("Invalid Selection", "Please select something", "Select an appointment you wish to delete");
        }
        else {
            //TODO write delete appointment logic
        }
    }

    public void onActionAddCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(main.class.getResource("AddCustomer.fxml")));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    public void onActionModifyCustomer(ActionEvent actionEvent) throws IOException, SQLException {

        Customers selected = allCustomersTable.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(main.class.getResource("ModifyCustomer.fxml"));
        loader.load();

        if (selected == null) {
            createWarningAlert("Invalid Selection", "Please select something", "Select a customer you wish to edit");
        }
        else {

            ModifyCustomerController m = loader.getController();
            m.sendCustomer(selected);

            Parent root = FXMLLoader.load(Objects.requireNonNull(main.class.getResource("ModifyCustomer.fxml")));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Customer");
            stage.setScene(scene);
            stage.show();
        }
    }

    public void onActionDeleteCustomer(ActionEvent actionEvent) throws SQLException {
        Customers selected = allCustomersTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            createWarningAlert("Invalid Selection", "Please select something", "Select a customer you wish to delete");
        }
        else {
            int id = selected.getCustomerID();
            String name = selected.getCustomerName();

            Alert del = new Alert(Alert.AlertType.CONFIRMATION);
            del.setHeaderText("Delete Customer");
            del.setContentText("Are you sure you want to delete " + name + "?");
            Optional<ButtonType> result = del.showAndWait();

        if (result.get() == ButtonType.OK) {
            // verify customer has no appointments
            int numAppointments = customerAccess.checkForAppointment(id);
            if (numAppointments> 0)  {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Must delete " + name + " appointments before they can be removed");
                alert.showAndWait();
            }
            else {
                customerAccess.deleteCustomer(id, name);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Customer Delete");
                alert.setContentText("Customer " + name + " was successfully deleted!");
                alert.showAndWait();
                allCustomersTable.setItems(customerAccess.getAllCustomers());
                allAppointmentsTable.setItems(appointmentAccess.getAllAppointments());
            }
            }
            else if (result.get() == ButtonType.CANCEL){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Customer Delete");
                alert.setContentText("Unable to delete " + name);
                alert.showAndWait();
            }
        }
    }

    public void onLogoutButtonClick(ActionEvent actionEvent) throws IOException {
        switchScreen(actionEvent, "login.fxml", "Login");
    }



    public void onExitButton(ActionEvent actionEvent) {
        JDBC.closeConnection();
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Create observable list for tableviews
        ObservableList<Appointments> allAppointments = null;
        ObservableList<Customers> allCustomers = null;
        try {
            allAppointments = appointmentAccess.getAllAppointments();
            allCustomers = customerAccess.getAllCustomers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        // Load values from Appointments into appointments table view
        apptID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        apptTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        apptType.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        apptStartTime.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptEndTime.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptDate.setCellValueFactory(new PropertyValueFactory<>("localDate"));
        apptCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        apptUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));

        // Load values from Customers into appointments table view
        custID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        custName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        custPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
        custAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        custState.setCellValueFactory(new PropertyValueFactory<>("Division"));
        custCountry.setCellValueFactory(new PropertyValueFactory<>("Country"));
        custPostalCode.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));


        // set items in table views
        allCustomersTable.setItems(allCustomers);
        allAppointmentsTable.setItems(allAppointments);
    }
}
