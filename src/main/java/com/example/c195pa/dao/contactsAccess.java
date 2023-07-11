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

/**
 * Class for accessing the contacts table and performing CRUD on contacts objects
 *
 * @author Spencer Upton
 */

public class contactsAccess {

    /**
     * Simple query to get contact names from the contacts table in the database
     * @return observable list of contact names
     * @throws SQLException if error occurs during the query
     */
    public static ObservableList<String> getContactNames() throws SQLException {
        ObservableList<String> contacts = FXCollections.observableArrayList();
        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT Contact_Name FROM contacts;");
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            contacts.add(rs.getString("Contact_Name"));
        }
        return contacts;
    }

    /**
     * Function that returns a contact name based on contactID
     * @param contactID parameter to set the contactID in the query
     * @return contact name in a string
     * @throws SQLException if an error occurs during the query
     */
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

    /**
     * Function that returns a contact ID name based on contact name
     * @param contactName parameter to set the contactName in the query
     * @return contactID in an int
     * @throws SQLException if an error occurs during the query
     */
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
