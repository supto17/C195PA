package com.example.c195pa.model;

public class FirstLevelDivisions {
    private int divisionID;
    private String divisionName;
    public int countryID;
    private int totalCustomers;

    public FirstLevelDivisions(int divisionID, String divisionName, int countryID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryID = countryID;
    }
    public FirstLevelDivisions (String divisionName, int totalCustomers) {
        this.divisionName = divisionName;
        this.totalCustomers = totalCustomers;
    }

    /**
     * @return divisionID
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * @return divisionName
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * @return countryID
     */
    public int getCountryID() {
        return countryID;
    }

    public int getTotalCustomers() {return totalCustomers;}
}


