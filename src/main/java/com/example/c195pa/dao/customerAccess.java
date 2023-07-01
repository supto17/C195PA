package com.example.c195pa.dao;

import com.example.c195pa.helper.JDBC;
import com.example.c195pa.model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.c195pa.model.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class customerAccess {

    public static ObservableList<Customers> getAllCustomers() throws SQLException {

        ObservableList<Customers> customersObservableList = FXCollections.observableArrayList();

        try {
            String sql =  "SELECT cu.Customer_ID, cu.Customer_Name, cu.Address, cu.Postal_Code, cu.Phone, cu.Division_ID, " +
                    "f.Division, f.COUNTRY_ID, co.Country FROM customers as cu INNER JOIN first_level_divisions " +
                    "as f on cu.Division_ID = f.Division_ID INNER JOIN countries as co ON f.COUNTRY_ID = co.Country_ID ORDER BY Customer_ID";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phoneNumber = rs.getString("Phone");
                int divisionID = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                String country = rs.getString("Country");
                Customers c = new Customers(id, name, address, postalCode, phoneNumber, divisionID, division, country);
                customersObservableList.add(c);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return customersObservableList;
    }

    //todo complete update logic
    public static boolean updateCustomer(Integer customerID, String name, String address,
                                         String postalCode, String phoneNumber, String country,
                                         String division) throws SQLException {

        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Users test = Users.getLoggedOnUser();
        String u = test.getUsername();
        System.out.println(u);

        PreparedStatement ps = JDBC.getConnection().prepareStatement("UPDATE customers " +
                " SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Last_Update=?," +
                " Last_Updated_By=?, Division_ID=? WHERE Customer_ID=?");

        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phoneNumber);
        ps.setString(5, ZonedDateTime.now(ZoneOffset.UTC).format(f));
        ps.setString(6, u);
        ps.setInt(7, customerAccess.getUserDivisionID(division));
        ps.setInt(8, customerID);

        try {
            System.out.println(ps.toString());
            ps.executeUpdate();
            ps.close();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addCustomer(String name, String address,
                                      String postalCode, String phoneNumber, String country,
                                      String division) throws SQLException {

        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        Users test = Users.getLoggedOnUser();
        String u = test.getUsername();
        System.out.println(u);

        PreparedStatement ps = JDBC.getConnection().prepareStatement("INSERT INTO customers "
                + "(Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update," +
                " Last_Updated_By, Division_ID)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");

        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phoneNumber);
        ps.setString(5, ZonedDateTime.now(ZoneOffset.UTC).format(f));
        ps.setString(6, u);
        ps.setString(7, ZonedDateTime.now(ZoneOffset.UTC).format(f));
        ps.setString(8, u);
        ps.setInt(9, customerAccess.getUserDivisionID(division));

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

    public static void deleteCustomer(int customerID, String customerName) throws SQLException {

        PreparedStatement ps = JDBC.getConnection().prepareStatement("DELETE FROM customers WHERE Customer_ID=? AND Customer_Name=?");
        ps.setInt(1, customerID);
        ps.setString(2, customerName);

        try {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getState(int divisionID) throws SQLException {
        String country = null;
        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT Division from client_schedule.first_level_divisions WHERE Division_ID = ?;");
        ps.setString(1, Integer.toString(divisionID));
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            country = rs.getString("Division");
        }
        return country;
    }

    public static ObservableList<String> getDivisionByCountry (String country) throws SQLException {
        ObservableList<String> divisionByCountry= FXCollections.observableArrayList();

        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT c.Country, c.Country_ID,  d.Division_ID, d.Division FROM countries as c RIGHT OUTER JOIN " +
                "first_level_divisions AS d ON c.Country_ID = d.Country_ID WHERE c.Country = ?");

        ps.setString(1, country);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            divisionByCountry.add(rs.getString("Division"));
        }

        rs.close();
        return divisionByCountry;
    }

    public static ObservableList<String> getAllCountries() throws SQLException {
        ObservableList<String> countries = FXCollections.observableArrayList();

        PreparedStatement ps =JDBC.getConnection().prepareStatement("SELECT DISTINCT Country from countries");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            countries.add(rs.getString("Country"));
        }
        ps.close();
        return countries;
    }

    public static int getUserDivisionID(String division) throws SQLException {
        int divID = 0;

        String sql = "SELECT Division_ID FROM first_level_divisions WHERE Division = ?;";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1,division);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            divID = rs.getInt("Division_ID");
        }

        ps.close();
        return divID;
    }

    public static ObservableList<Integer> getCustomerIDs() throws SQLException {
        ObservableList<Integer> i = FXCollections.observableArrayList();
        // customer ID was not showing in correct order without order by
        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT Customer_ID FROM " +
                "customers ORDER BY Customer_ID");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            i.add(rs.getInt("Customer_ID"));
        }
        return i;
    }

    //todo weird error ASK INSTRUCTOR
    public static int checkForAppointment(int customerID) throws SQLException {
        int appointments = 0;
        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT COUNT(Customer_ID) FROM appointments WHERE Customer_ID=?");
        ps.setInt(1, customerID);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            appointments = rs.getInt(1);
        }
        ps.close();
        return appointments;
    }
}
