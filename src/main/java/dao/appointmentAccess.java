package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import model.Contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class appointmentAccess {

    public static ObservableList<Appointments> getAllAppointments() throws SQLException {

        ObservableList<Appointments> appointmentsObservableList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");

                Appointments a = new Appointments(appointmentID, title, description, location, type,
                        start, end, customerID, userID, contactID);
                appointmentsObservableList.add(a);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentsObservableList;
    }
}
