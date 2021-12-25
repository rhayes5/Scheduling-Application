package DBAccess;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivisions;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBFirstLevelDivisions {

    public static ObservableList<FirstLevelDivisions> getAllFLDs(){
        ObservableList<FirstLevelDivisions> fldList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from First_Level_Divisions";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int id = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");
                FirstLevelDivisions d = new FirstLevelDivisions(id, division, countryId);
                fldList.add(d);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return fldList;
    }

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
        catch (SQLException throwables) {
        throwables.printStackTrace();
    }

        return FLDByCountryList;
    }

    public static FirstLevelDivisions getDivisionById(int dId) {
        try {
            String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = " + dId;

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            rs.next();
            int id = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            int countryId = rs.getInt("COUNTRY_ID");


            FirstLevelDivisions d = new FirstLevelDivisions(id, division, countryId);

            return d;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}
