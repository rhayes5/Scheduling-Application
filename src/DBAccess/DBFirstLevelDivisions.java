package DBAccess;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivisions;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBFirstLevelDivisions {
    /**
     * Gets all first level divisions with a specific country id from the database and returns them in an observable list.
     * @param cId The country id integer
     * @return An observable list of first level divisions
     */
    public static ObservableList<FirstLevelDivisions> getFLDByCountry(int cId)
    {
        ObservableList<FirstLevelDivisions> FLDByCountryList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM first_level_divisions WHERE country_id = " + cId;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

        while (rs.next())
        {
            int id = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            int countryId = rs.getInt("Country_ID");

            FirstLevelDivisions d = new FirstLevelDivisions(id, division, countryId);
            FLDByCountryList.add(d);
        }
    }
        catch (SQLException e) {
        e.printStackTrace();
    }
        return FLDByCountryList;
    }

    /**
     * Finds a specific first level division by id and returns that first level division object.
     * @param dId The division id integer to find
     * @return a first level division object with the matching id or null if none
     */
    public static FirstLevelDivisions getDivisionById(int dId) {
        try {
            String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = " + dId;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            rs.next();
            int id = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            int countryId = rs.getInt("COUNTRY_ID");

            return new FirstLevelDivisions(id, division, countryId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
