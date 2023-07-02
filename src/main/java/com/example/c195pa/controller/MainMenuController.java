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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    @FXML
    public RadioButton viewByWeek;
    @FXML
    public ToggleGroup viewSort;
    @FXML
    public RadioButton viewByMonth;
    @FXML
    public RadioButton viewAll;

    /**
     * @param event user attempts to switch screens
     * @param path path to the screen
     * @param title title of the screen
     * @throws IOException if the screen user is attempting to go to is not found
     */
    public void switchScreen(ActionEvent event, String path, String title) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(main.class.getResource(path)));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Simple method to create a warning alert
     * @param title title of the alert
     * @param  header text of the alert
     * @param context context text of the alert
     */
    public void createWarningAlert(String title, String header, String context) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
    }

    /**
     * when the user clicks the report button, the function attempts to load the reports screen.
     * @param actionEvent reports button clicked
     * @throws IOException if reports screen is not found
     */
    public void onReportsButtonClick(ActionEvent actionEvent) throws IOException {
        switchScreen(actionEvent, "Reports.fxml", "Reports");
    }

    /**
     * when the user clicks the add appointment button, the function attempts to load the Add Appointment screen.
     * @param actionEvent add appointment button clicked
     * @throws IOException if add appointment screen is not found
     */
    public void onActionAddAppointment(ActionEvent actionEvent) throws IOException {
        switchScreen(actionEvent, "AddAppointment.fxml", "Add Appointment");
    }

    /**
     * when the user clicks the edit appointment button, the function attempts to load the modify appointment screen.
     * @param actionEvent edit appointment button clicked
     * @throws IOException if modify appointment screen is not found
     */
    public void onActionUpdateAppointment(ActionEvent actionEvent) throws IOException, SQLException {
        Appointments selected = allAppointmentsTable.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(main.class.getResource("ModifyAppointment.fxml"));
        loader.load();

        if (selected == null) {
            createWarningAlert("Invalid Selection", "Please select something", "Select an appointment you wish to edit");
        } else {

            ModifyAppointmentController m = loader.getController();
            m.sendAppointment(selected);


            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setTitle("Modify Appointment");
            stage.setScene(new Scene(scene));
            stage.show();;
        }
    }

    /**
     * Verifies the user has selected an appointment from the tableView, and deletes the appointment
     * @param actionEvent delete button clicked
     * @throws SQLException if error occurs during the deletion.
     */
    public void onActionDeleteAppointment(ActionEvent actionEvent) throws SQLException {
        Appointments selected = allAppointmentsTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            createWarningAlert("Invalid Selection", "Please select something", "Select an appointment you wish to delete");
        }
        else {
            int apptID = selected.getAppointmentID();
            Alert del = new Alert(Alert.AlertType.CONFIRMATION);
            del.setHeaderText("Delete Customer");
            del.setContentText("Are you sure you want to delete appointment " + apptID + "?");
            Optional<ButtonType> result = del.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                boolean success = appointmentAccess.deleteAppointment(apptID);
                if (success) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Customer Delete");
                    alert.setContentText("Appointment ID " + apptID + " of type " + selected.getAppointmentType() + " was successfully deleted!");
                    alert.showAndWait();
                    allAppointmentsTable.setItems(appointmentAccess.getAllAppointments());
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Unable to delete appointment " + apptID);
                    alert.showAndWait();
                }
            } else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Appointment Delete");
                alert.setContentText("Appointment " + apptID + " was not deleted.");
                alert.showAndWait();
            }
        }
    }

    /**
     * when the user clicks the add customer button, the function attempts to load the add customer screen.
     * @param actionEvent add customer button clicked
     * @throws IOException if add customer screen is not found
     */
    public void onActionAddCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(main.class.getResource("AddCustomer.fxml")));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * when the user clicks the edit customer button, the function attempts to load the modify customer screen.
     * @param actionEvent edit customer button clicked
     * @throws IOException if modify customer screen is not found
     */
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

            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setTitle("Modify Customer");
            stage.setScene(new Scene(scene));
            stage.show();;
        }
    }

    /**
     * Verifies the user has selected a customer from the tableView, and deletes the customer only if they have no existing appointments
     * @param actionEvent delete button clicked
     * @throws SQLException if error occurs during the deletion.
     */
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

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // verify customer has no appointments
            int numAppointments = customerAccess.checkForAppointment(id);
            if (numAppointments > 0)  {
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
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Customer Delete");
                alert.setContentText(name + " was not deleted.");
                alert.showAndWait();
            }
        }
    }

    /**
     * User is returned to the login screen and must login again
     * @param actionEvent logout button clicked
     * @throws IOException if login screen is not found
     */
    public void onLogoutButtonClick(ActionEvent actionEvent) throws IOException {
        switchScreen(actionEvent, "login.fxml", "Login");
    }

    /**
     * Connection to the database is closed, and the program exits
     * @param actionEvent exit button clicked
     */
    public void onExitButton(ActionEvent actionEvent) {
        JDBC.closeConnection();
        System.exit(0);
    }

    /**
     * Queries the database with a query to only show appointments within the week.
     * Sets items in the tableView based on what the query returns
     * @param actionEvent view by week radio button selected
     * @throws SQLException if error occurs during the query
     */
    public void viewByWeekSelected(ActionEvent actionEvent) throws SQLException {
        ObservableList<Appointments> a = appointmentAccess.viewByWeek();
        allAppointmentsTable.setItems(a);
    }

    /**
     * Queries the database with a query to only show appointments within the month.
     * Sets items in the tableView based on what the query returns
     * @param actionEvent view by month radio button selected
     * @throws SQLException if error occurs during the query
     */
    public void viewByMonthSelected(ActionEvent actionEvent) throws SQLException {
        ObservableList<Appointments> a = appointmentAccess.viewByMonth();
        allAppointmentsTable.setItems(a);
    }

    /**
     * returns the appointment tableView to display all appointments
     * @param actionEvent view all radio button selected
     * @throws SQLException if error occurs during the query
     */
    public void viewAllSelected(ActionEvent actionEvent) throws SQLException {
        allAppointmentsTable.setItems(appointmentAccess.getAllAppointments());
    }

    /**
     * Initializes the screen and populates the tableviews appointment and customer respectively
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
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
        apptDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
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
        viewAll.setSelected(true);
    }
}
