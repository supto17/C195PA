package com.example.c195pa.dao;

import com.example.c195pa.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.c195pa.model.Appointments;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class appointmentAccess {

    public static ObservableList<Appointments> getAllAppointments() throws SQLException {

        ObservableList<Appointments> appointmentsObservableList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT a.Appointment_ID, a.Type, a.Title, a.Description, a.Location, a.Type, a.Start," +
                    "a.End, a.Create_Date, a.Created_By, a.Last_Update, a.Last_Updated_By, " +
                    "a.Customer_ID, a.Contact_ID, a.User_ID, c.Contact_Name FROM client_schedule.appointments a " +
                    "INNER JOIN contacts c ON a.contact_ID = c.Contact_ID;";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp startTime = rs.getTimestamp("Start");
                LocalTime end = rs.getTimestamp("End").toLocalDateTime().toLocalTime();
                LocalDate localDate = startTime.toLocalDateTime().toLocalDate();
                LocalTime start = startTime.toLocalDateTime().toLocalTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                String contact = rs.getString("Contact_Name");
                LocalDateTime dateTime = rs.getTimestamp("Start").toLocalDateTime();

                Appointments a = new Appointments(appointmentID, title, description, location, type,
                        start, end, localDate, customerID, userID, contactID, contact, dateTime);
                appointmentsObservableList.add(a);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentsObservableList;
    }

    public static boolean addAppointment(String title, String description, String location, String contact,
                                         String type, Timestamp t, Integer customerID, Integer userID) {

        return true;
    }

    public static ObservableList<Integer> getUserIDs() throws SQLException {
        ObservableList<Integer> i = FXCollections.observableArrayList();

        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT Customer_ID FROM appointments");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            i.add(rs.getInt("Customer_ID"));
        }
        return i;
    }
}
