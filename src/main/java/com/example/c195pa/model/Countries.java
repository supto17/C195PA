package com.example.c195pa.model;

/**
 * Class for creating country objects
 *
 * @author Spencer Upton
 */

public class Countries {
    private int countryID;
    private String countryName;

    /**
     *
     * @param countryID
     * @param countryName
     */
    public Countries(int countryID, String countryName){
        this.countryID = countryID;
        this.countryName = countryName;
    }

    /**
     * @return countryID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * @return countryName
     */
    public String getCountryName() {
        return countryName;
    }
}
