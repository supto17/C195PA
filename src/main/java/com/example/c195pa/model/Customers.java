package com.example.c195pa.model;

import com.example.c195pa.dao.customerAccess;

import java.sql.SQLException;

/**
 * Class for creating customer objects
 *
 * @author Spencer Upton
 */

public class Customers {
    private int customerID;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhoneNumber;
    public int divisionID;
    public String division;
    public String country;


    public Customers(Integer customerID, String customerName, String customerAddress, String customerPostalCode,
                     String customerPhoneNumber, int divisionID, String division, String country) throws SQLException {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhoneNumber = customerPhoneNumber;
        this.divisionID = divisionID;
        this.division = division;
        this.country = country;
    }


    /**
     * @return customerID
     */
    public Integer getCustomerID() {
        return customerID;
    }

    /**
     * @return customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @return customerAddress
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * @return customerPostalCode
     */
    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    /**
     * @return customerPhoneNumber
     */
    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    /**
     * @return divisionID
     */
    public int getDivisionID() {
        return divisionID;
    }

    public String getDivision() {return division;}

    public String getCountry() {return country;}
}
