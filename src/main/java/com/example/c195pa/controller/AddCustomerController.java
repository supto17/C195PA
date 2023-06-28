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

    public void toMainMenu (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(main.class.getResource("MainMenu.fxml")));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void cancelButtonClicked(ActionEvent event) throws IOException {
        toMainMenu(event);
    }

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
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Appointment update successful");
            alert.showAndWait();
            toMainMenu(actionEvent);
        }
        // report failure to update
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Failed to update " + customerNameField.getText() + ".");
            alert.showAndWait();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            customerCountryBox.setItems(customerAccess.getAllCountries());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
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
