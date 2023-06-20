package model;

public class Users {
    private int userID;
    private String username;
    private String password;

    public Users(int userID, String username, String password) {
        this.userID = userID;
        this.username = username;
        this.password = password;
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
