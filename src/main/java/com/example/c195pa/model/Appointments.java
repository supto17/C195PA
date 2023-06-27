package com.example.c195pa.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Appointments {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String appointmentType;
    private LocalDateTime dateTime;
    private LocalTime start;
    private LocalTime end;
    private LocalDate localDate;
    public int customerID;
    public int userID;
    public int contactID;

    public Appointments(int appointmentID, String title, String description, String location, String appointmentType,
                        LocalTime start, LocalTime end, LocalDate localDate, int customerID, int userID, int contactID,
                        LocalDateTime dateTime) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.appointmentType = appointmentType;
        this.start = start;
        this.end = end;
        this.localDate = localDate;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
        this.dateTime = dateTime;
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
    public LocalTime getStart() {
        return start;
    }

    /**
     * @return end
     */
    public LocalTime getEnd() {
        return end;
    }

    /**
     * @return lDate
     */
    public LocalDate getLocalDate() {return localDate;}

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

    public LocalDateTime getDateTime() { return dateTime; }
}
