package model;

import java.time.LocalDateTime;

public class Appointments {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String appointmentType;
    private LocalDateTime start;
    private LocalDateTime end;
    public int customerID;
    public int userID;
    public int contactID;

    public Appointments(int appointmentID, String title, String description, String location, String appointmentType,
                        LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.appointmentType = appointmentType;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    public Appointments() {

    }

    /**
     * @return appointmentID
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @return appointmentType
     */
    public String getAppointmentType() {
        return appointmentType;
    }

    /**
     * @return start
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * @return end
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * @return customerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * @return userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * @return contactID
     */
    public int getContactID() {
        return contactID;
    }
}
