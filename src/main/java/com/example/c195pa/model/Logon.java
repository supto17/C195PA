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

    public Logon() {
    }
    private static Users loggedOnUser;
    private static Locale userLocale;
    private static ZoneId userZoneID;

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
            loggedOnUser = new Users(rs.getInt("User_ID"), rs.getString("User_Name"),
                    rs.getString("Password"));
            userLocale = Locale.getDefault();
            userZoneID = ZoneId.systemDefault();
            ps.close();
            return true;
        }
    }



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
