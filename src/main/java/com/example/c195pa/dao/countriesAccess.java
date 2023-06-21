package com.example.c195pa.dao;

import com.example.c195pa.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.c195pa.model.Countries;

import java.sql.*;
public class countriesAccess {

    public static ObservableList<Countries> getAllCountries() throws SQLException{
        ObservableList<Countries> countriesObservableList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Country_ID. Country FROM countries";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Countries c = new Countries(countryID, countryName);
                countriesObservableList.add(c);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countriesObservableList;
    }
}
