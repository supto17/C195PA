package com.example.c195pa.dao;

import com.example.c195pa.controller.ModifyAppointmentController;
import com.example.c195pa.helper.JDBC;
import com.example.c195pa.model.Users;
import com.mysql.cj.log.Log;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.c195pa.model.Appointments;
import javafx.scene.control.Alert;

import java.sql.*;
import java.time.*;
import java.time.chrono.ChronoZonedDateTime;
import java.time.format.DateTimeFormatter;

public class appointmentAccess {

    public static Alert createWarningAlert(String header, String context) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
        return alert;
    }

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
                Timestamp endTime = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                String contact = rs.getString("Contact_Name");

                LocalDateTime dateTime = startTime.toLocalDateTime();
                LocalDate startDate = startTime.toLocalDateTime().toLocalDate();
                LocalDate endDate = startTime.toLocalDateTime().toLocalDate();
                LocalTime start = startTime.toLocalDateTime().toLocalTime();
                LocalTime end = endTime.toLocalDateTime().toLocalTime();

                Appointments a = new Appointments(appointmentID, title, description, location, type,
                        start, end, startDate, endDate, customerID, userID, contactID, contact, dateTime);
                appointmentsObservableList.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentsObservableList;
    }

    public static boolean addAppointment(Appointments a) throws SQLException {

        LocalDate startDate = a.getStartDate();
        LocalDate endDate = a.getEndDate();
        LocalTime startTime = a.getStart();
        LocalTime endTime = a.getEnd();

        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        LocalDateTime start = LocalDateTime.of(startDate, startTime);
        LocalDateTime end = LocalDateTime.of(endDate, endTime);
        Users test = Users.getLoggedOnUser();
        String u = test.getUsername();

        Timestamp startTS = Timestamp.valueOf(start);
        Timestamp endTS = Timestamp.valueOf(end);

        boolean success = validateAppointments(a);

        String sql = "INSERT INTO appointments "
                + "(Title, Description, Location, Type, Start, End, Create_Date," +
                " Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setString(1, a.getTitle());
        ps.setString(2, a.getDescription());
        ps.setString(3, a.getLocation());
        ps.setString(4, a.getAppointmentType());
        ps.setTimestamp(5, startTS);
        ps.setTimestamp(6, endTS);
        ps.setString(7, ZonedDateTime.now(ZoneOffset.UTC).format(f));
        ps.setString(8, u);
        ps.setString(9, ZonedDateTime.now(ZoneOffset.UTC).format(f));
        ps.setString(10, u);
        ps.setInt(11, a.getCustomerID());
        ps.setInt(12, a.getUserID());
        ps.setInt(13, contactsAccess.getContactID(a.contact));
        System.out.println(ps);
        if (success) {
            try {
                ps.executeUpdate();
                ps.close();
                return true;
            } catch (SQLException err) {
                err.printStackTrace();
                return false;
            }
        }
        return true;
    }

    //TODO update appointment logic
    public static boolean updateAppointment(Appointments a) throws SQLException {

        LocalDate startDate = a.getStartDate();
        LocalDate endDate = a.getEndDate();
        LocalTime startTime = a.getStart();
        LocalTime endTime = a.getEnd();

        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        LocalDateTime start = LocalDateTime.of(startDate, startTime);
        LocalDateTime end = LocalDateTime.of(endDate, endTime);

        Timestamp startTS = Timestamp.valueOf(start);
        Timestamp endTS = Timestamp.valueOf(end);

        Users test = Users.getLoggedOnUser();
        String u = test.getUsername();

        boolean success = validateAppointments(a);

        if (success) {

            String sql = "UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, " +
                    " Last_Update=?, Last_Updated_By=?, Customer_ID=?," +
                    " User_ID=?, Contact_ID=? WHERE Appointment_ID=?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            try {
                ps.setString(1, a.getTitle());
                ps.setString(2, a.getDescription());
                ps.setString(3, a.getLocation());
                ps.setString(4, a.getAppointmentType());
                ps.setTimestamp(5, startTS);
                ps.setTimestamp(6, endTS);
                ps.setString(7, ZonedDateTime.now(ZoneOffset.UTC).format(f));
                ps.setString(8, u);
                ps.setInt(9, a.getCustomerID());
                ps.setInt(10, a.getUserID());
                ps.setInt(11, contactsAccess.getContactID(a.contact));
                ps.setInt(12, a.getAppointmentID());


                ps.executeUpdate();
                System.out.println("Update worked!");

                ps.close();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Try again nerd.");
                ps.close();
                return false;
            }
        }
        else {
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
        } catch (SQLException e) {
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
            Timestamp endTime = rs.getTimestamp("End");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");
            String contact = contactName;

            LocalDateTime dateTime = startTime.toLocalDateTime();
            LocalDate startDate = startTime.toLocalDateTime().toLocalDate();
            LocalDate endDate = endTime.toLocalDateTime().toLocalDate();
            LocalTime start = startTime.toLocalDateTime().toLocalTime();
            LocalTime end = endTime.toLocalDateTime().toLocalTime();


            Appointments a = new Appointments(appointmentID, title, description, location, type,
                    start, end, startDate, endDate, customerID, userID, contactID, contact , dateTime);
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

    public static ObservableList<Appointments> viewByWeek() throws SQLException {

        ObservableList<Appointments> viewByWeek = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT * FROM appointments WHERE WEEK(Start) = WEEK(current_date());");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp startTime = rs.getTimestamp("Start");
                Timestamp endTime = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                String contact = contactsAccess.getContactName(contactID);

                LocalDateTime dateTime = startTime.toLocalDateTime();
                LocalDate startDate = startTime.toLocalDateTime().toLocalDate();
                LocalDate endDate = endTime.toLocalDateTime().toLocalDate();
                LocalTime start = startTime.toLocalDateTime().toLocalTime();
                LocalTime end = endTime.toLocalDateTime().toLocalTime();

                Appointments a = new Appointments(appointmentID, title, description, location, type,
                        start, end, startDate, endDate, customerID, userID, contactID, contact, dateTime);
                viewByWeek.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return viewByWeek;
    }

    public static ObservableList<Appointments> viewByMonth() throws SQLException {

        ObservableList<Appointments> viewByMonth = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT * FROM appointments WHERE MONTH(Start) = MONTH(current_date());");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp startTime = rs.getTimestamp("Start");
                Timestamp endTime = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                String contact = contactsAccess.getContactName(contactID);

                LocalDateTime dateTime = startTime.toLocalDateTime();
                LocalDate startDate = startTime.toLocalDateTime().toLocalDate();
                LocalDate endDate = endTime.toLocalDateTime().toLocalDate();
                LocalTime start = startTime.toLocalDateTime().toLocalTime();
                LocalTime end = endTime.toLocalDateTime().toLocalTime();

                Appointments a = new Appointments(appointmentID, title, description, location, type,
                        start, end, startDate, endDate, customerID, userID, contactID, contact, dateTime);
                viewByMonth.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return viewByMonth;
    }

    //TODO verify validation
    public static boolean validateAppointments(Appointments a) throws SQLException {
        ObservableList<Appointments> allAppointments = appointmentAccess.getAllAppointments();

        LocalDate startDate = a.getStartDate();
        LocalDate endDate = a.getEndDate();
        LocalTime startTime = a.getStart();
        LocalTime endTime = a.getEnd();

        ZonedDateTime startZDT = ZonedDateTime.of(startDate, startTime, Users.getUserZoneID());
        ZonedDateTime endZDT = ZonedDateTime.of(endDate, endTime, Users.getUserZoneID());
        ZonedDateTime startBusinessHours = ZonedDateTime.of(a.getStartDate(), LocalTime.of(8, 0), ZoneId.of("America/New_York"));
        ZonedDateTime endBusinessHours = ZonedDateTime.of(a.getEndDate(), LocalTime.of(22, 0), ZoneId.of("America/New_York"));

        //TODO Check business hours
        if (startZDT.isBefore(startBusinessHours) || startZDT.isAfter(endBusinessHours) || endZDT.isBefore(startBusinessHours) || endZDT.isAfter(endBusinessHours)) {
            createWarningAlert("Due to business constraints, start time and end time must be in business hours of 8:00am - 10:00pm",
                    "Current EST: " + LocalTime.now(ZoneId.of("America/New_York")));
            return false;
        }

        /**
         * Logic for validating data against other appointments
         */
        for (Appointments appointments : allAppointments) {
            if (a.getAppointmentID() != appointments.getAppointmentID()) {
                if (a.getStartDate().compareTo(appointments.getStartDate()) == 0) {
                    if (appointments.getStart() == a.getStart() || appointments.getEnd() == a.getEnd()) {
                        createWarningAlert("Appointment Overlap", "Appointment " + a.getAppointmentID() +
                                " overlaps with existing appointment " + appointments.getAppointmentID());
                        System.out.println("appointment starts or ends equal");
                        return false;
                    }
                    if (a.getStart().isAfter(appointments.getStart()) && a.getStart().isBefore(appointments.getEnd())) {
                        createWarningAlert("Appointment Overlap", "Appointment " + a.getAppointmentID() +
                                " overlaps with existing appointment " + appointments.getAppointmentID());
                        System.out.println("appoint start is between another appointment");
                        return false;
                    }
                    if (a.getEnd().isAfter(appointments.getStart()) && a.getEnd().isBefore(appointments.getEnd())) {
                        createWarningAlert("Appointment Overlap", "Appointment " + a.getAppointmentID() +
                                " overlaps with existing appointment " + appointments.getAppointmentID());
                        System.out.println("appointment end is between another appointment");
                        return false;
                         }
                    }
                }
            }
        /**
         * Logic for validating user input
         */
        if (startDate.isAfter(endDate) || endDate.isAfter(startDate)) {
            createWarningAlert("Invalid Dates", "Due to business constraints, appointment start date and end date must be on the same day");
            return false;
        }
        if(endTime.isBefore(startTime)) {
            createWarningAlert("Invalid Times", "End time must be after start time.");
            return false;
        }
        return true;
    }
}
