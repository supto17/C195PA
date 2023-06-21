package com.example.c195pa.model;

public class Contacts {
    public int contactID;
    private String contactName;
    private String contactEmail;

    public Contacts(int contactID, String contactName, String contactEmail) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * @return contactID
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * @return contactName
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * @return contactEmail
     */
    public String getContactEmail() {
        return contactEmail;
    }
}
