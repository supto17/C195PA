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

    /**
     * Constructor to create an appointment with an existing appointmentID
     * @param appointmentID id of the appointment
     * @param title title of the appointment
     * @param description description of the appointment
     * @param location location of the appointment
     * @param appointmentType type of the appointment
     * @param start start time of the appointment
     * @param end end time of the appointment
     * @param startDate start date of the appointment
     * @param endDate end date of the appointment
     * @param customerID id for the customer of the appointment
     * @param userID id for the user of the appointment
     * @param contactID id for the contact of the appointment
     * @param contact name for the contact of the appointment
     * @param dateTime dateTime
     */
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

    /**
     * Constructor to create an appointment without an existing appointmentID
     * @param title title of the appointment
     * @param description description of the appointment
     * @param location location of the appointment
     * @param appointmentType type of the appointment
     * @param start start time of the appointment
     * @param end end time of the appointment
     * @param startDate start date of the appointment
     * @param endDate end date of the appointment
     * @param customerID id for the customer of the appointment
     * @param userID id for the user of the appointment
     * @param contactID id for the contact of the appointment
     * @param contact name for the contact of the appointment
     * @param dateTime dateTime
     */
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

    /**
     * Constructor for user in the reports page
     * @param month month of the appointments
     * @param type types of the appointments
     * @param count number of the appointments
     */
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

    /**
     * @return startDate
     */
    public LocalDate getStartDate() {return startDate;}

    /**
     * @return endDate
     */
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

    /**
     *
     * @return contact
     */
    public String getContact() {return contact;}

    /**
     *
     * @return dateTime
     */
    public LocalDateTime getDateTime() { return dateTime; }

    /**
     * @return count
     */
    public int getCount() {return count;}

    /**
     * @return type
     */
    public String getType() {return type;}

    /**
     * @return month
     */
    public String getMonth() {return month;}
}
