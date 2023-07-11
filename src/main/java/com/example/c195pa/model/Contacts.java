package com.example.c195pa.model;

/**
 * Class for creating contact objects
 *
 * @author Spencer Upton
 */

public class Contacts {
    public int contactID;
    private String contactName;
    private String contactEmail;

    /**
     * Constructor to create a contact object
     * @param contactID id of the contact
     * @param contactName name of the contact
     * @param contactEmail email of the contact
     */
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
