package DBAccess;

import Database.DBConnection;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class DBUsers {
    //check to see if username and password match an entry in database
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


