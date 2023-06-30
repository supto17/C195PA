package com.example.c195pa.model;

public class Users {
    private static int userID;
    private static String username;
    private static String password;

    public Users(int userID, String username, String password) {
        Users.userID = userID;
        Users.username = username;
        Users.password = password;
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
}
