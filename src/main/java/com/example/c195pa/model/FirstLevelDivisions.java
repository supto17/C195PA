package com.example.c195pa.model;

/**
 * Class for creating fld objects
 *
 * @author Spencer Upton
 */

public class FirstLevelDivisions {
    private int divisionID;
    private String divisionName;
    public int countryID;
    private int totalCustomers;

    /**
     * Constructor used in the reports page
     * @param divisionName name of the division
     * @param totalCustomers total number of customers in that division
     */
    public FirstLevelDivisions (String divisionName, int totalCustomers) {
        this.divisionName = divisionName;
        this.totalCustomers = totalCustomers;
    }

    /**
     * @return divisionName
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * @return total customers
     */
    public int getTotalCustomers() {return totalCustomers;}
}


