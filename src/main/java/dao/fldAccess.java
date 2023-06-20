package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class fldAccess {

    public ObservableList<FirstLevelDivisions> fldObservableList = FXCollections.observableArrayList();

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
}
