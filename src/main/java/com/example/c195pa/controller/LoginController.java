package com.example.c195pa.controller;

import com.example.c195pa.dao.appointmentAccess;
import com.example.c195pa.dao.contactsAccess;
import com.example.c195pa.main;
import com.example.c195pa.helper.JDBC;
import com.example.c195pa.model.Appointments;
import com.example.c195pa.model.Users;
import javafx.collections.ObservableList;
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
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Login controller
 *
 * @author Spencer Upton
 */

public class LoginController implements Initializable {
    @FXML
    public TextField loginUsername;
    @FXML
    public TextField loginPassword;
    @FXML
    public Button loginButton;
    @FXML
    public Button cancelButton;
    @FXML
    public Label zoneLabel;
    @FXML
    public RadioButton frenchButton;
    @FXML
    public ToggleGroup languageGroup;
    @FXML
    public RadioButton englishButton;
    @FXML
    public Label loginLogo;
    @FXML
    public Label zoneLabelText;

    /**
     * Simple function to get userLocale based on their radio button selection
     * @return userLocale
     */
    public Locale getLocale() {
        Locale userLocale = null;
        if (frenchButton.isSelected()) {
            userLocale = Locale.FRENCH;
        }
        else {
            userLocale = Locale.ENGLISH;
        }
        return userLocale;
    }

    /**
     * When the user clicks the login button, the login and username text boxes are parsed and sent in a query to the database.
     * If the username and password do not match an entry in the database, a null query is returned and the user is not able to logon.
     * Otherwise, the user is logged in. In either case, the login attempt is written to login_audit.txt
     * @param actionEvent login button clicked
     * @throws IOException if main menu is not found
     * @throws SQLException if username and password are not founf
     */
    public void onActionLoginButton(ActionEvent actionEvent) throws IOException, SQLException {

        ResourceBundle rb = ResourceBundle.getBundle("language", getLocale());

        String username = loginUsername.getText();
        String password = loginPassword.getText();

        boolean logon = Users.loginAttempt(username, password);

        if (logon) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(main.class.getResource("MainMenu.fxml")));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Main Menu");
            stage.setScene(scene);
            stage.show();

            // pull current time
            LocalDateTime now = LocalDateTime.now();

            // pull all appointments
            ObservableList<Appointments> allAppointments = appointmentAccess.getAllAppointments();

            boolean found = false;
            for (Appointments a : allAppointments) {

                // alert user if appointment is within 15 minutes
                // otherwise inform the user if there are no upcoming appointments

                if (a.getDateTime().isAfter(now) && a.getDateTime().isBefore(now.plusMinutes(15))) {
                    found = true;
                    System.out.println("Data " + a.getAppointmentID() + " is in time.");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Appointment Alert");
                    alert.setHeaderText("Upcoming Appointment");
                    alert.setContentText("Appointment with " + contactsAccess.getContactName(a.getContactID()) + " at " + a.getStart());
                    alert.showAndWait();

                    }
                }
            if (!found) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appointment Alert");
                alert.setHeaderText("No upcoming appointments");
                alert.setContentText("You have no upcoming appointments");
                alert.showAndWait();
            }
        }   else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(rb.getString("ERROR"));
                alert.setHeaderText(rb.getString("INVALID") + " " + (rb.getString("LOGIN")));
                alert.setContentText(rb.getString("PLEASE") + " " + rb.getString("TRY") + " " + (rb.getString("AGAIN")));
                alert.showAndWait();
            }
        }

    /**
     * If the user clicks cancel, the database connection is closed and the program exits
     * @param actionEvent cancel button clicked
     */
    public void onActionCancelButton(ActionEvent actionEvent) {
        JDBC.closeConnection();
        System.exit(0);
    }

    /**
     * All text on the login screen is changed to french, to include alerts
     * @param actionEvent french radio button pressed
     */
    public void onActionFrenchButtonPressed(ActionEvent actionEvent) {
        ResourceBundle rb = ResourceBundle.getBundle("language", Locale.FRENCH);
        loginButton.setText(rb.getString("LOGIN"));
        frenchButton.setText(rb.getString("FRENCH"));
        englishButton.setText(rb.getString("ENGLISH"));
        loginLogo.setText(rb.getString("LOGIN"));
        zoneLabelText.setText(rb.getString("TIMEZONE"));
        loginUsername.setPromptText(rb.getString("USERNAME"));
        loginPassword.setPromptText(rb.getString("PASSWORD"));
        cancelButton.setText(rb.getString("CANCEL"));
    }

    /**
     * All text on the login screen is changed to english, to include alerts
     * @param actionEvent english radio button pressed
     */
    public void onActionEnglishButtonPressed(ActionEvent actionEvent) {
        ResourceBundle rb = ResourceBundle.getBundle("language", Locale.ENGLISH);
        loginButton.setText(rb.getString("LOGIN"));
        frenchButton.setText(rb.getString("FRENCH"));
        englishButton.setText(rb.getString("ENGLISH"));
        loginLogo.setText(rb.getString("LOGIN"));
        zoneLabelText.setText(rb.getString("TIMEZONE"));
        loginUsername.setPromptText(rb.getString("USERNAME"));
        loginPassword.setPromptText(rb.getString("PASSWORD"));
        cancelButton.setText(rb.getString("CANCEL"));
    }

    /**
     * On initialize, userLocale is retrieved and the language is populated according to which locale
     * the user has set/ is in.
     * @param location location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Locale userLocale = Locale.getDefault();
        zoneLabel.setText(ZoneId.systemDefault().toString());

                ResourceBundle rb = ResourceBundle.getBundle("language", userLocale);
                loginButton.setText(rb.getString("LOGIN"));
                frenchButton.setText(rb.getString("FRENCH"));
                englishButton.setText(rb.getString("ENGLISH"));
                loginLogo.setText(rb.getString("LOGIN"));
                zoneLabelText.setText(rb.getString("TIMEZONE"));
                loginUsername.setPromptText(rb.getString("USERNAME"));
                loginPassword.setPromptText(rb.getString("PASSWORD"));
                cancelButton.setText(rb.getString("CANCEL"));

                if (userLocale == Locale.FRENCH) {
                    frenchButton.setSelected(true);
                }
                else {
                    englishButton.setSelected(true);
                }

        ZonedDateTime z = ZonedDateTime.now(ZoneId.systemDefault());
        ZonedDateTime z2 = z.withZoneSameInstant(ZoneOffset.UTC);
    }
}