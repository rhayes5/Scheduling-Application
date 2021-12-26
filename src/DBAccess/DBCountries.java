package DBAccess;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCountries {

    /**
     * Gets an observable list of all countries from the database.
     * Citation: Kinkead, Mark (02-13-2021) "Getting the DB Connection Class Project Ready" webinar.
     *     https://wgu.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=1be32ba5-76c6-47f3-8816-accf0002109b
     * @return an Observable list of all countries from the database
     */
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
        catch (SQLException e) {
            e.printStackTrace();
        }
        return countryList;
    }

    /**
     * Finds a country with a specific country id in the database and returns that country if it exists or returns null if not.
     * @param cId The country id integer
     * @return A country object with the matching country id
     */
    public static Countries getCountryById(int cId) {
        try {
            String sql = "SELECT * FROM countries WHERE Country_ID = " + cId;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            rs.next();
            int countryID = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            return new Countries(countryID, countryName);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
