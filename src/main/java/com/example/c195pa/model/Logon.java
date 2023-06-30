package com.example.c195pa.model;

import com.example.c195pa.helper.JDBC;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

public class Logon {

    private static Users loggedOnUser;
    private static Locale userLocale;
    private static ZoneId userZoneID;
    private int userID;
    private String username;
    private String password;

    public Logon(int userID, String username,  String password) {
        this.userID = userID;
        this.username = username;
        this.password = password;
    }

    public static boolean loginAttempt(String username, String password) throws SQLException {

        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT User_ID, User_name, Password  " +
                "FROM users WHERE User_Name = ? AND Password = ?");
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();

        if(!rs.next()) {
            ps.close();
            return false;
            //TODO log failed in attempt
        }
        else {
            while (rs.next()) {
                int userID = rs.getInt("User_ID");
                username = rs.getString("User_Name");
                password = rs.getString("Password");
                loggedOnUser = new Users(userID, username, password);
            }
            userLocale = Locale.getDefault();
            userZoneID = ZoneId.systemDefault();
            ps.close();
            return true;
        }
    }
    public int getUserID() {return userID;}

    public String getUsername() {return username;}

    public String getPassword() {return password;}

    public static Users getLoggedOnUser() {
        return loggedOnUser;
    }

    public static Locale getUserLocale() {
        return userLocale;
    }

    public static ZoneId getUserZoneID() {
        return userZoneID;
    }
}
