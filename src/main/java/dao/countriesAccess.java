package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;

import javax.xml.transform.Result;
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
