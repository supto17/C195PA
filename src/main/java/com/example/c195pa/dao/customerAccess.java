package com.example.c195pa.dao;

import com.example.c195pa.helper.JDBC;
import com.mysql.cj.jdbc.JdbcConnection;
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
            String sql = "SELECT * from customers";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phoneNumber = rs.getString("Phone");
                int divisionID = rs.getInt("Division_ID");
                Customers c = new Customers(id, name, address, postalCode, phoneNumber, divisionID);
                customersObservableList.add(c);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return customersObservableList;
    }

    public static String getCountry(int divisionID) throws SQLException {
        String country = null;
        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT DIVISION from client_schedule.first_level_divisions WHERE Division_ID = ?;");
        ps.setString(1, Integer.toString(divisionID));
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            country = rs.getString("Division");
        }
        return country;
    }
}
