package com.example.c195pa.dao;

import com.example.c195pa.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.c195pa.model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class usersAccess {

    public ObservableList<Users> getAllUsers() throws SQLException {

        ObservableList<Users> usersObservableList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM users";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int userID = rs.getInt("User_ID");
                String username = rs.getString("Username");
                String password = rs.getString("Password");
                Users u = new Users(userID, username, password);
                usersObservableList.add(u);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return usersObservableList;
    }

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
