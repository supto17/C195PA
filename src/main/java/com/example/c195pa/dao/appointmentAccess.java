package com.example.c195pa.dao;

import com.example.c195pa.helper.JDBC;
import com.example.c195pa.model.Logon;
import com.example.c195pa.model.Users;
import com.mysql.cj.log.Log;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.c195pa.model.Appointments;
import javafx.scene.control.Alert;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class appointmentAccess {

    public static ObservableList<Appointments> getAllAppointments() throws SQLException {

        ObservableList<Appointments> appointmentsObservableList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT a.Appointment_ID, a.Type, a.Title, a.Description, a.Location, a.Type, a.Start," +
                    "a.End, a.Create_Date, a.Created_By, a.Last_Update, a.Last_Updated_By, " +
                    "a.Customer_ID, a.Contact_ID, a.User_ID, c.Contact_Name FROM client_schedule.appointments a " +
                    "INNER JOIN contacts c ON a.contact_ID = c.Contact_ID ORDER BY Appointment_ID;";
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

    public static boolean addAppointment(String title, String description, String location,
                                         String type, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, Integer customerID, Integer userID, Integer contactID) throws SQLException {

        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        LocalDateTime start = LocalDateTime.of(startDate, startTime);
        LocalDateTime end = LocalDateTime.of(endDate, endTime);


        String sql = "INSERT INTO appointments "
                + "(Title, Description, Location, Type, Start, End, Create_Date," +
                " Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?);";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setString(1,title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setString(5, String.valueOf(start));
        ps.setString(6, String.valueOf(end));
        ps.setString(7, ZonedDateTime.now(ZoneOffset.UTC).format(f));
        ps.setString(8, Logon.getLoggedOnUser().getUsername());
        ps.setString(9, ZonedDateTime.now(ZoneOffset.UTC).format(f));
        ps.setString(10, Logon.getLoggedOnUser().getUsername());
        ps.setInt(11, customerID);
        ps.setInt(12, userID);
        ps.setInt(13, contactID);

        try {
            ps.executeUpdate();
            ps.close();
            return true;
        }
        catch (SQLException err) {
            err.printStackTrace();
            return false;
        }
    }

    //TODO update appointment logic
    public static boolean updateAppointment(Appointments a) throws SQLException{

        LocalDate startDate = a.getLocalDate();
        LocalDate endDate = a.getLocalDate();
        LocalTime startTime = a.getStart();
        LocalTime endTime = a.getEnd();

        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        LocalDateTime start = LocalDateTime.of(startDate, startTime);
        LocalDateTime end = LocalDateTime.of(endDate, endTime);

        System.out.println(start);
        System.out.println(end);
        Users test = Logon.getLoggedOnUser();
        String u = test.getUsername();
        System.out.println(u);

        String sql = "UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, " +
                " Last_Update=?, Last_Updated_By=?, Customer_ID=?," +
                " User_ID=?, Contact_ID=? WHERE Appointment_ID=?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);


        try {
            ps.setString(1, a.getTitle());
            ps.setString(2, a.getDescription());
            ps.setString(3, a.getLocation());
            ps.setString(4, a.getType());
            ps.setString(5, String.format(String.valueOf(start), f));
            ps.setString(6, String.format(String.valueOf(end), f));
            ps.setString(7, String.format(String.valueOf(LocalDateTime.now()), f));
            ps.setString(8, String.valueOf(Logon.getLoggedOnUser()));
            ps.setInt(9, a.getCustomerID());
            ps.setInt(10, a.getUserID());
            ps.setInt(11, contactsAccess.getContactID(a.contact));
            ps.setInt(12, a.getAppointmentID());

            ps.executeUpdate();
            ps.close();
            System.out.println("Update worked!");

            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Try again nerd.");
            return false;
        }
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

    public static boolean deleteAppointment(int appointmentID) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID=?;";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, appointmentID);

        try {
            ps.executeUpdate();
            ps.close();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            ps.close();
            return false;
        }
    }

    public static ObservableList<Appointments> getAppointmentsByContact(String contactName) throws SQLException {

        ObservableList<Appointments> appointmentsByContact = FXCollections.observableArrayList();

        Integer cID = contactsAccess.getContactID(contactName);
        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT * FROM appointments WHERE Contact_ID=?");
        ps.setInt(1, cID);
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
            String contact = contactName;
            LocalDateTime dateTime = rs.getTimestamp("Start").toLocalDateTime();

            Appointments a = new Appointments(appointmentID, title, description, location, type,
                    start, end, localDate, customerID, userID, contactID, contact, dateTime);
            appointmentsByContact.add(a);
        }
        return appointmentsByContact;
    }

    public static ObservableList<Appointments> populateAppointmentsByMonth() throws SQLException {

        ObservableList<Appointments> s = FXCollections.observableArrayList();
        String sql = "SELECT MONTHNAME(Start), Type, COUNT(*) FROM Appointments" +
                " GROUP BY MONTHNAME(Start), Type;";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String month = rs.getString("MONTHNAME(Start)");
            String type = rs.getString("Type");
            int count = rs.getInt("Count(*)");
            Appointments a = new Appointments(month, type, count);
            s.add(a);
        }
        return s;
    }
}
