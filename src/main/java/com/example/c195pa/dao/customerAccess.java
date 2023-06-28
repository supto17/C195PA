package com.example.c195pa.dao;

import com.example.c195pa.helper.JDBC;
import com.example.c195pa.model.Countries;
import com.mysql.cj.jdbc.JdbcConnection;
import javafx.beans.binding.When;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.c195pa.model.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class customerAccess {

    public static ObservableList<Customers> getAllCustomers() throws SQLException {

        ObservableList<Customers> customersObservableList = FXCollections.observableArrayList();

        try {
            String sql =  "SELECT cu.Customer_ID, cu.Customer_Name, cu.Address, cu.Postal_Code, cu.Phone, cu.Division_ID, " +
                    "f.Division, f.COUNTRY_ID, co.Country FROM customers as cu INNER JOIN first_level_divisions " +
                    "as f on cu.Division_ID = f.Division_ID INNER JOIN countries as co ON f.COUNTRY_ID = co.Country_ID";

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

    public static boolean updateCustomer(Integer customerID, String name, String address, String postalCode, String phoneNumber, String divsionID) {
        //todo update customer
        return false;
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
}
