package com.example.c195pa.controller;

import com.example.c195pa.dao.customerAccess;
import com.example.c195pa.helper.JDBC;
import com.example.c195pa.main;
import com.example.c195pa.model.Customers;
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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Modify customer controller
 *
 * @author Spencer Upton
 */

public class ModifyCustomerController implements Initializable {
    @FXML
    public TextField customerID;
    @FXML
    public TextField customerName;
    @FXML
    public TextField customerAddress;
    @FXML
    public TextField customerPostalCode;
    @FXML
    public TextField customerPhoneNumber;
    @FXML
    public ComboBox <String> customerCountryBox;
    @FXML
    public ComboBox<String> divisionBox;
    @FXML
    public Button saveButton;
    @FXML
    public Button cancelButton;


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
     * send the selected customer from the main screen and populates modify appointment
     * with the data.
     * @param selectedCustomer appointment selected in the main menu appointment table view
     * @throws SQLException if any query called returns an error
     */
    public void sendCustomer(Customers selectedCustomer) throws SQLException {
        customerCountryBox.setItems(customerAccess.getAllCountries());
        customerCountryBox.getSelectionModel().select(selectedCustomer.getCountry());
        divisionBox.setItems(customerAccess.getDivisionByCountry(selectedCustomer.getDivision()));
        divisionBox.getSelectionModel().select(selectedCustomer.getDivision());
        customerID.setText(Integer.toString(selectedCustomer.getCustomerID()));
        customerName.setText(selectedCustomer.getCustomerName());
        customerAddress.setText(selectedCustomer.getCustomerAddress());
        customerPostalCode.setText(selectedCustomer.getCustomerPostalCode());
        customerPhoneNumber.setText(selectedCustomer.getCustomerPhoneNumber());

    }

    /**
     * Gets the text from the text fields, and send the information to the updateCustomer function.
     * If that function returns true, the customer is updated and the user is returned to the main screen.
     * @param actionEvent save button clicked
     * @throws SQLException if the customer is not able to be added to the database
     */
    public void onActionSaveButton(ActionEvent actionEvent) throws SQLException, IOException {

        // check for null fields
        Integer id = Integer.parseInt(customerID.getText());
        String name = customerName.getText();
        String address = customerAddress.getText();
        String postalCode = customerPostalCode.getText();
        String phoneNumber = customerPhoneNumber.getText();
        String country = customerCountryBox.getValue();
        String division = divisionBox.getSelectionModel().getSelectedItem();

        if (name.isEmpty() || address.isEmpty() || postalCode.isEmpty() || phoneNumber.isEmpty() ||
            country.isEmpty() || division.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Error Parsing Data");
            alert.setContentText("Please ensure all fields are not blank.");
            alert.showAndWait();
        }
        // create boolean for if update worked
        boolean success = customerAccess.updateCustomer(id, name, address, postalCode, phoneNumber, country, division);

        //report success
        if (success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Appointment update successful");
            alert.showAndWait();
            toMainMenu(actionEvent);
        }
        // report failure to update
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Failed to update " + customerName.getText() + ".");
            alert.showAndWait();
        }
    }

    /**
     * Returns the user to the main screen
     * @param actionEvent cancel button clicked
     * @throws IOException if main menu screen is not found
     */
    public void onActionCancelButton(ActionEvent actionEvent) throws IOException {
        toMainMenu(actionEvent);
    }

    /**
     *  Initializes the modify customer screen
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /**
         *  LAMBDA function to listen for country combo box selection
         */
        customerCountryBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) {
                divisionBox.getItems().clear();
                divisionBox.setDisable(true);
            }
            else {
                divisionBox.setDisable(false);
                try {
                    divisionBox.setItems(customerAccess.getDivisionByCountry(customerCountryBox.getValue()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
