package com.example.c195pa.dao;

import com.example.c195pa.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.c195pa.model.FirstLevelDivisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for accessing the fld table and performing CRUD on fld objects
 *
 * @author Spencer Upton
 */
public class fldAccess {

    /**
     * Function that queries the database for my custom report on the reports page
     * @return observable list of customers by division
     * @throws SQLException if an error occurs during the query
     */
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
