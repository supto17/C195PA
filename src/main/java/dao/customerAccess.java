package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class customerAccess {

    public ObservableList<Customers> getAllCustomers() throws SQLException {

        ObservableList<Customers> customersObservableList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from customers";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Customer_Address");
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
}
