package DBAccess;

import Database.DBConnection;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class DBUsers {

    /**
     * Checks for the entered username and password in the database and returns true if it exists or false if not.
     * @param username The user's entered username
     * @param password The user's entered password
     * @return true if the username and password exist in the database or false if not
     */
    public static boolean checkUser(String username, char[] password) {
        try {
            String sql = "SELECT User_ID, User_Name FROM users WHERE User_Name = ? AND Password = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, String.valueOf(password));
            ResultSet rs = ps.executeQuery();

            Arrays.fill(password, (char)0);

            if (rs.next()) {
                User.setUsername(username);
                User.setId((rs.getInt("User_ID")));
                return true;
            } else {
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }


}


