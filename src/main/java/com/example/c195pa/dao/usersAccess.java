package com.example.c195pa.dao;

import com.example.c195pa.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.c195pa.model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class usersAccess {

    /**
     * Function that returns an observable list of userIDs
     * @return observableList of userIDs
     * @throws SQLException if error occurs during the query
     */
    public static ObservableList<Integer> getUserIDs() throws SQLException {
        ObservableList<Integer> i = FXCollections.observableArrayList();

        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT User_ID FROM users ORDER BY User_ID;");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            i.add(rs.getInt("User_ID"));
        }
        return i;
    }
}
