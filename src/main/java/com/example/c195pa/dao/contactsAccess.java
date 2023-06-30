package com.example.c195pa.dao;

import com.example.c195pa.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.c195pa.model.Contacts;

import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.PropertyResourceBundle;

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

    public static String getContactName(int contactID) throws SQLException{
        String contact = "";
        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT Contact_Name FROM contacts WHERE Contact_ID=?;");
        ps.setInt(1, contactID);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            contact = rs.getString("Contact_Name");
        }
        return contact;
    }

    public static int getContactID(String contactName) throws SQLException {
        int contactID = 0;
        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT Contact_ID FROM contacts WHERE Contact_Name=?;");
        ps.setString(1, contactName);

        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                contactID = rs.getInt("Contact_ID");
            }
            ps.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return contactID;
    }
}
