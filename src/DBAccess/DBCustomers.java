package DBAccess;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

public class DBCustomers {
    /**
     * Returns an observable list of all customers in the database
     * @return An observable list of all customers in the database
     */
    public static ObservableList<Customer> getAllCustomers() {
        String sql = "SELECT * from customers";
        return getMany(sql);
    }

    /**
     * Finds a customer with a specific customer id in the database and returns that customer if it exists or returns null if not.
     * @param cId The customer id integer
     * @return The customer object with the matching id if it exists or null if not
     */
    public static Customer getCustomerById(int cId) {
        String sql = "SELECT * FROM customers WHERE Customer_ID = " + cId;
        return getOne(sql);
    }

    /**
     * Finds customers with a specific string within their name and returns a list of those customers
     * @param cName The string to search for in customer names
     * @return An observable list of customers
     */
    public static ObservableList<Customer> getCustomerByName(String cName) {
        String sql = "SELECT * FROM customers WHERE Customer_Name LIKE '%" + cName + "%'";
        return getMany(sql);
    }

    /**
     * Finds customers using a given sql statement, adds them to an observable list, and returns the list
     * @param sql The sql statement string to use
     * @return An observable list of customers
     */
    public static ObservableList<Customer> getMany(String sql) {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int division = rs.getInt("Division_ID");

                Customer c = new Customer(id, name, address, postalCode, phone, division);
                customerList.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }

    /**
     * Finds a single customer using a given sql statement and returns that customer
     * @param sql The sql statement string to use
     * @return a Customer object or null if none
     */
    public static Customer getOne(String sql) {
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            rs.next();
            int id = rs.getInt("Customer_ID");
            String name = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            int division = rs.getInt("Division_ID");

            return new Customer(id, name, address, postalCode, phone, division);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Deletes a customer from the database by ID after confirming that the user wants to delete.  Won't allow a customer
     * to be deleted if they have a scheduled appointment.
     * @param id The customer id integer of the customer to delete
     * @return true if the customer is deleted or false if not
     */
    public static boolean deleteCustomer(int id) {
        Alert areYouSureAlert = new Alert(Alert.AlertType.WARNING, "Are you sure you want to delete this customer?", ButtonType.YES, ButtonType.CANCEL);

        Optional<ButtonType> clickButton = areYouSureAlert.showAndWait();

        if (clickButton.isPresent() && clickButton.get() == ButtonType.YES) {
            try {
                String sql = "DELETE FROM customers WHERE Customer_Id = ?";
                PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
                ps.setInt(1, id);
                ps.execute();
                return true;
            } catch (SQLIntegrityConstraintViolationException e) {
                Alert cannotDeleteAlert = new Alert(Alert.AlertType.ERROR, "Cannot delete a customer with an appointment scheduled.  " +
                        "Delete appointment and then delete the customer.");
                cannotDeleteAlert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Saves a new customer to the database.
     * @param name The customer name string
     * @param address The customer address string
     * @param postalCode The customer postal code string
     * @param phone The customer phone string
     * @param user The user string
     * @param division The division id integer
     * @return True if saved successfully or false if not
     */
    public static boolean saveNewCustomer(String name, String address, String postalCode, String phone, String user, int division)
    {
        try {
            String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, " +
                    "Created_By, Last_Update, Last_Updated_By, Division_ID)" +
                    " VALUES (?, ?, ?, ?, NOW(), ?, NOW(), ?, ?)";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setString(5, user);
            ps.setString(6, user);
            ps.setInt(7, division);
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates an existing customer in the database.
     * @param id The customer's id int
     * @param name The customer name string
     * @param address The customer address string
     * @param postalCode The customer postal code string
     * @param phone The customer phone string
     * @param user The user string
     * @param division The division id integer
     * @return True if updated successfully or false if not
     */
    public static boolean updateCustomer(int id, String name, String address, String postalCode, String phone, String user, int division)
    {
        try {
            String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, " +
                    " Last_Update = NOW(), Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setString(5, user);
            ps.setInt(6, division);
            ps.setInt(7, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
