package DBAccess;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCountries {
    //copied from "Getting the DB Connection Class Project Ready"
    public static ObservableList<Countries> getAllCountries(){
        ObservableList<Countries> countryList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from countries";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Countries c = new Countries(countryID, countryName);
                countryList.add(c);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return countryList;
    }

    public static Countries getCountryById(int cId) {
        try {
            String sql = "SELECT * FROM countries WHERE Country_ID = " + cId;

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            rs.next();
            int countryID = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            Countries c = new Countries(countryID, countryName);

            return c;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

}
