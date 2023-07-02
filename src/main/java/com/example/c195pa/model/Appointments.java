package com.example.c195pa.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Appointments {
    private String month;
    private String type;
    private int count;
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String appointmentType;
    private LocalDateTime dateTime;
    private LocalTime start;
    private LocalTime end;
    private LocalDate startDate;
    private LocalDate endDate;
    public String contact;
    public int customerID;
    public int userID;
    public int contactID;

    public Appointments(int appointmentID, String title, String description, String location, String appointmentType,
                        LocalTime start, LocalTime end, LocalDate startDate, LocalDate endDate, int customerID, int userID, int contactID,
                        String contact, LocalDateTime dateTime) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.appointmentType = appointmentType;
        this.start = start;
        this.end = end;
        this.startDate = startDate;
        this.endDate = endDate;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
        this.contact = contact;
        this.dateTime = dateTime;
    }
    public Appointments(String title, String description, String location, String appointmentType,
                        LocalTime start, LocalTime end, LocalDate startDate, LocalDate endDate, int customerID, int userID, int contactID,
                        String contact, LocalDateTime dateTime) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.appointmentType = appointmentType;
        this.start = start;
        this.end = end;
        this.startDate = startDate;
        this.endDate = endDate;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
        this.contact = contact;
        this.dateTime = dateTime;
    }

    public Appointments(String month, String type, int count) {
        this.month = month;
        this.type = type;
        this.count = count;
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

    public LocalDate getStartDate() {return startDate;}

    public LocalDate getEndDate() {return endDate;}

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

    public String getContact() {return contact;}

    public LocalDateTime getDateTime() { return dateTime; }
    public String getMonth() {return month;}

    public String getType() {return type;}

    public int getCount() {return count;}
}
