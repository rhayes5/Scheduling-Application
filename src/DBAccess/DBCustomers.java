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
    public static ObservableList<Customer> getAllCustomers() {
        String sql = "SELECT * from customers";
        return getMany(sql);
    }

    public static Customer getCustomerById(int cId) {
        String sql = "SELECT * FROM customers WHERE Customer_ID = " + cId;
        return getOne(sql);
    }


    public static ObservableList<Customer> getCustomerByName(String cName) {
        String sql = "SELECT * FROM customers WHERE Customer_Name LIKE '%" + cName + "%'";
        return getMany(sql);
    }

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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerList;
    }

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

            Customer c = new Customer(id, name, address, postalCode, phone, division);

            return c;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public static boolean deleteCustomer(int id) throws SQLException {
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
            }
        }
        return false;
    }

    //add user
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
}
