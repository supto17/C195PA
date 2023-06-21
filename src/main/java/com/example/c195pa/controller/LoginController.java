package com.example.c195pa.controller;

import com.example.c195pa.main;
import com.example.c195pa.helper.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    public TextField loginUsername;
    public TextField loginPassword;
    public Button loginButton;
    public Button cancelButton;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void loginButton(ActionEvent actionEvent) {
    }

    public void cancelButton(ActionEvent actionEvent) {
    }

    public void onActionLoginButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(main.class.getResource("MainMenu.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    public void onActionCancelButton(ActionEvent actionEvent) {
        JDBC.closeConnection();
        System.exit(0);
    }
}