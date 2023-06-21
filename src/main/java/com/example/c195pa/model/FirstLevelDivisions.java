package com.example.c195pa.model;

public class FirstLevelDivisions {
    private int divisionID;
    private String divisionName;
    public int countryID;

    public FirstLevelDivisions(int divisionID, String divisionName, int countryID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryID = countryID;
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
}
