package com.example.c195pa.model;

import com.example.c195pa.helper.JDBC;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Locale;

public class Users {
    private int userID;
    private String username;
    private String password;
    private static Users loggedOnUser;
    private static Locale userLocale;
    private static ZoneId userZoneID;
    private static final String logPath = "login_audit.txt";

    public Users(int userID, String username, String password) {
        this.userID = userID;
        this.username = username;
        this.password = password;
    }



    public static boolean loginAttempt(String username, String password) throws SQLException, IOException {

        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT User_ID, User_name, Password  " +
                "FROM users WHERE User_Name = ? AND Password = ?;");
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();

        boolean found = false;

        while (rs.next()) {
            int userID = rs.getInt("User_ID");
            username = rs.getString("User_Name");
            password = rs.getString("Password");
            loggedOnUser = new Users(userID, username, password);
            auditLogin(username, true);
            found = true;
        }
        if (!found) {
            ps.close();
            auditLogin(username, false);
            found = false;
        }
        userLocale = Locale.getDefault();
        userZoneID = ZoneId.systemDefault();
        return found;
    }

    public static void auditLogin(String userName, Boolean b) throws IOException {
        String s = ZoneId.systemDefault() + " LOGIN ATTEMPT-USERNAME: " + userName + " LOGIN SUCCESSFUL: " + (b.toString() + "\n");
        try {
            FileWriter logger = new FileWriter(logPath, true);
            PrintWriter pw = new PrintWriter(logger);
            pw.write(s);
            pw.flush();
            pw.close();
            System.out.println("Wrote to file!");
        }
        catch (IOException error) {
            error.printStackTrace();
        }
    }

    /**
     * @return userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
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
