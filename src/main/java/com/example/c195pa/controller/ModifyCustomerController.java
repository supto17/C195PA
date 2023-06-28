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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

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

    public void onActionSaveButton(ActionEvent actionEvent) {
    }

    public void onActionCancelButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(main.class.getResource("MainMenu.fxml")));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
