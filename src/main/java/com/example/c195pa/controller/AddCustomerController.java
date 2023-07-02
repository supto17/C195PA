package com.example.c195pa.controller;

import com.example.c195pa.dao.customerAccess;
import com.example.c195pa.main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {

    @FXML
    private Button cancelButton;

    @FXML
    private TextField customerAddressField;

    @FXML
    private ComboBox<String> customerCountryBox;

    @FXML
    private TextField customerIdField;

    @FXML
    private TextField customerNameField;

    @FXML
    private TextField customerPhoneNumberField;

    @FXML
    private TextField customerPostalCodeField;

    @FXML
    private ComboBox<String> divisionBox;

    @FXML
    private Button saveButton;

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
     * @throws IOException if unable to find Main Menu
     */
    @FXML
    void cancelButtonClicked(ActionEvent event) throws IOException {
        toMainMenu(event);
    }

    /**
     * When the user clicks the save button, the entered date is parsed and an if statement is created to ensure none of
     * the fields are blank. Due to how I built the boxes, this is the only error checking necessary to ensure data entered
     * is okay
     * @param actionEvent actionEvent
     * @throws SQLException if add customer does not work
     * @throws IOException if main menu is not found
     */
    @FXML
    public void saveButtonClick(ActionEvent actionEvent) throws SQLException, IOException {

        // check for null fields
        String name = customerNameField.getText();
        String address = customerAddressField.getText();
        String postalCode = customerPostalCodeField.getText();
        String phoneNumber = customerPhoneNumberField.getText();
        String country = customerCountryBox.getValue();
        String division = divisionBox.getValue();

        if (name.isBlank() || address.isBlank() || postalCode.isBlank() || phoneNumber.isBlank() ||
                country.isBlank() || division.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Error Parsing Data");
            alert.setContentText("Please ensure all fields are not blank.");
            alert.showAndWait();
        }
        // create boolean for if update worked
        boolean success = customerAccess.addCustomer(name, address, postalCode, phoneNumber, country, division);

        //report success
        if (success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Customer update successful");
            alert.showAndWait();
            toMainMenu(actionEvent);
        }
        // report failure to update
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Failed to add " + customerNameField.getText() + ".");
            alert.showAndWait();
        }
    }


    /**
     * Initialize populates the customer country box and includes a LAMBDA function that listens
     * for changes in the customerCountryBox
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            customerCountryBox.setItems(customerAccess.getAllCountries());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        /**
         * LAMBDA function that listens for entry on the customerCountryBox and populates the division box based
         * on their selection
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
