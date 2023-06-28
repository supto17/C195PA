package com.example.c195pa.dao;

import com.example.c195pa.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.c195pa.model.Contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class contactsAccess {
    public static ObservableList<Contacts> getAllContacts() throws SQLException {

        ObservableList<Contacts> contactsObservableList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from contacts";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");
                Contacts c = new Contacts(contactID, contactName, contactEmail);
                contactsObservableList.add(c);
            }
        }
        catch (SQLException e) {
                e.printStackTrace();
            }
         return contactsObservableList;
    }

    public static ObservableList<String> getContactNames() throws SQLException {
        ObservableList<String> contacts = FXCollections.observableArrayList();
        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT Contact_Name FROM contacts;");
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            contacts.add(rs.getString("Contact_Name"));
        }
        return contacts;
    }
}
