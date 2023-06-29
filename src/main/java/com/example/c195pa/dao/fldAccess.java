package com.example.c195pa.dao;

import com.example.c195pa.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.c195pa.model.FirstLevelDivisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class fldAccess {

    public ObservableList<FirstLevelDivisions> fldObservableList = FXCollections.observableArrayList();

    public fldAccess() throws SQLException {
    }

    public ObservableList<FirstLevelDivisions> getAllFLD() throws SQLException {

        try {
            String sql = "SELECT * FROM first_level_divisions";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryID = rs.getInt("Country_ID");
                FirstLevelDivisions fld = new FirstLevelDivisions(divisionID, divisionName, countryID);
                fldObservableList.add(fld);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return fldObservableList;
    }

    public static ObservableList<FirstLevelDivisions> getCustomersByDivision() throws SQLException {

        ObservableList<FirstLevelDivisions> customersByDivision = FXCollections.observableArrayList();

        String sql = "SELECT Division, COUNT(Customer_ID) FROM first_level_divisions f " +
                "JOIN customers c ON f.Division_ID = c.Division_ID GROUP BY Division ORDER BY COUNT(CUSTOMER_ID) DESC;";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            String s = rs.getString("Division");
            int i = rs.getInt("Count(Customer_ID)");
            FirstLevelDivisions f = new FirstLevelDivisions(s, i);
            customersByDivision.add(f);
        }
        return customersByDivision;
    }
}
