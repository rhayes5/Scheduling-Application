package DBAccess;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBContacts {
    /**
     * Gets and returns a list of all contacts from the database
     * @return An observable list of all contacts from the database
     */
    public static ObservableList<Contact> getAllContacts()
    {
        ObservableList<Contact> contactsList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM contacts";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int id = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                String email = rs.getString("Email");

                Contact c = new Contact(id, name, email);
                contactsList.add(c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contactsList;
    }

    /**
     * Gets and returns a contact with a specific contact ID.
     * @param cId the Contact ID int
     * @return A contact with a specific id
     */
    public static Contact getContactById(int cId) {
        try {
            String sql = "SELECT * FROM contacts WHERE Contact_ID = " + cId;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            rs.next();
            int id = rs.getInt("Contact_ID");
            String name = rs.getString("Contact_Name");
            String email = rs.getString("Email");

            Contact c = new Contact(id, name, email);
            return c;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}
