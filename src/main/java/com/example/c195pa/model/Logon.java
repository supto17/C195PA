package com.example.c195pa.model;

import com.example.c195pa.helper.JDBC;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Logon {

    public static boolean loginAttempt(String username, String password) throws SQLException {

        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT * FROM users WHERE User_Name = ? AND Password = ?");
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();

        if(!rs.next()) {
            ps.close();
            return false;
            //TODO log failed in attempt
        }
        else {
            //TODO log successful user lo in
            return true;
        }
    }
}
