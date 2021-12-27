package controllers;

import DBAccess.DBAppointments;
import DBAccess.DBCustomers;
import Database.DBConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    Parent root;
    Stage stage;

    @FXML
    private TableView<Customer> customersTable;

    @FXML
    private TableColumn<Customer, Integer> custIdCol;

    @FXML
    private TableColumn<Customer, String> nameCol;

    @FXML
    private TableColumn<Customer, String> addressCol;

    @FXML
    private TableColumn<Customer, String> postalCodeCol;

    @FXML
    private TableColumn<Customer, String> phoneCol;

    @FXML
    private TableColumn<Customer, Integer> divisionCol;

    @FXML
    private Button newCustomerBtn;

    @FXML
    private Button updateCustomerBtn;

    @FXML
    private Button deleteCustomerBtn;

    @FXML
    private TextField customerSearch;

    @FXML
    private Button clearSelectedCustomerBtn;

    @FXML
    private Button newAppointmentBtn;

    @FXML
    private Button updateAppointmentBtn;

    @FXML
    private Button deleteAppointmentBtn;

    @FXML
    private TableView<Appointments> appointmentsTable;

    @FXML
    private TableColumn<Appointments, Integer> apptIdCol;

    @FXML
    private TableColumn<Appointments, String> titleCol;

    @FXML
    private TableColumn<Appointments, String> descriptionCol;

    @FXML
    private TableColumn<Appointments, String> typeCol;

    @FXML
    private TableColumn<Appointments, String> locationCol;

    @FXML
    private TableColumn<Appointments, LocalDate> startDateCol;

    @FXML
    private TableColumn<Appointments, LocalTime> startTimeCol;

    @FXML
    private TableColumn<Appointments, LocalDate> endDateCol;

    @FXML
    private TableColumn<Appointments, LocalTime> endTimeCol;

    @FXML
    private TableColumn<Appointments, Integer> contactCol;

    @FXML
    private RadioButton allDatesRB;

    @FXML
    private ToggleGroup whenTG;

    @FXML
    private RadioButton thisMonthRB;

    @FXML
    private RadioButton thisWeekRB;

    @FXML
    private Label whoLbl;

    @FXML
    private Label welcomeLbl;

    @FXML
    private Button viewReportsBtn;

    @FXML
    private Button signOutBtn;

    @FXML
    private Button exitBtn;

    /**
     * Searches through appointment by customer name.
     * @param event Characters entered in search box
     */
    @FXML
    void onKeyTypedSearchName(KeyEvent event) {
        String nameSearch = customerSearch.getText();
        if (nameSearch != null)
        {
            if (!findName(nameSearch).isEmpty()) {
                customersTable.setItems(findName(nameSearch));
            }
        }
    }

    /**
     * Searches for a string in a customer's name and returns an observable list of all customers whose names contain that string.
     * @param name The string to search for in the customer name
     * @return The list of customers whose name contains that string
     */
    private ObservableList<Customer> findName(String name) {
        ObservableList<Customer> c = DBCustomers.getCustomerByName(name);
        return c;
    }

    /**
     * Opens a page to add a new customer.
     * @param event The new customer button is clicked
     */
    @FXML
    void onActionNewCustomer(ActionEvent event) {
            try {
                SceneChange.sceneChange(event, "/view/AddCustomer.fxml", "Add Customer");
            } catch (NullPointerException | IOException e) {
                ExceptionHandlers.nextPageError(event);
            }
    }

    /**
     * Opens a page to update the selected customer if one is selected or gives an error if not.
     * @param event The update customer button is clicked
     */
    @FXML
    void onActionUpdateCustomer(ActionEvent event) {
        if (customersTable.getSelectionModel().getSelectedItem() != null) {
            try {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/UpdateCustomer.fxml"));
                loader.load();

                UpdateCustomerController UCC = loader.getController();
                UCC.getCurrentCustomerData(customersTable.getSelectionModel().getSelectedItem());

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.setTitle("Update Customer");
                root = loader.getRoot();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (NullPointerException | IOException e) {
               ExceptionHandlers.nextPageError(event);
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a customer to update.", ButtonType.OK);
            alert.setTitle("No customer selected");
            alert.showAndWait();
        }
    }

    /**
     * Deletes the selected customer if a customer is selected or gives an error if not.
     * @param event The delete customer button is clicked.
     */
    @FXML
    void onActionDeleteCustomer(ActionEvent event) {
        if (customersTable.getSelectionModel().getSelectedItem() != null) {
            int id = customersTable.getSelectionModel().getSelectedItem().getId();

            try {
                boolean deleted = DBCustomers.deleteCustomer(id);
                if (deleted) {
                    Alert deletedAlert = new Alert(Alert.AlertType.ERROR, "Customer deleted.", ButtonType.OK);
                    deletedAlert.showAndWait();
                    whoLbl.setText("All Customers");
                    appointmentsTable.setItems(DBAppointments.getAllAppointments());
                }
            } catch (SQLException e) {
                e.printStackTrace();
                Alert unableToDeleteAlert = new Alert(Alert.AlertType.ERROR, "Unable to delete customer.", ButtonType.OK);
                unableToDeleteAlert.showAndWait();
            }
            customersTable.setItems(DBCustomers.getAllCustomers());
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a customer to delete.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Opens the add new appointment page.
     * @param event The add appointment button is clicked
     */
    @FXML
    void onActionNewAppointment(ActionEvent event) {
        try {
            SceneChange.sceneChange(event, "/view/AddAppointment.fxml", "Add Appointment");
        } catch (NullPointerException | IOException e) {
           ExceptionHandlers.nextPageError(event);
        }
    }

    /**
     * If an appointment is selected,
     * @param event The appointment update button is clicked
     */
    @FXML
    void onActionUpdateAppointment(ActionEvent event) {
        if (appointmentsTable.getSelectionModel().getSelectedItem() != null) {
            try {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/UpdateAppointment.fxml"));
                loader.load();

                UpdateAppointmentController UAC = loader.getController();
                UAC.setAppointment(appointmentsTable.getSelectionModel().getSelectedItem());

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.setTitle("Update Appointment");
                root = loader.getRoot();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (NullPointerException | IOException e) {
                ExceptionHandlers.nextPageError(event);
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select an appointment to update.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Deletes appointment with a confirmation message if an appointment is selected or gives error message if not.
     * @param event The appointment delete button is clicked
     */
    @FXML
    void onActionDeleteAppointment(ActionEvent event) {
    if (appointmentsTable.getSelectionModel().getSelectedItem() != null) {
        int id = appointmentsTable.getSelectionModel().getSelectedItem().getId();

        DBAppointments.deleteAppointment(id);
        //updates tables
            appointmentsTable.setItems(DBAppointments.getAllAppointments());
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select an appointment to delete.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Loads the View Reports page.
     * @param event The View Reports button is clicked
     */
    @FXML
    void onActionViewReports(ActionEvent event) {
        try {
            SceneChange.sceneChange(event, "/view/Reports.fxml", "Reports");
        } catch (NullPointerException | IOException e) {
            ExceptionHandlers.nextPageError(event);
        }
    }

    /**
     * Returns to the login page.
     * @param event The Sign Out button is clicked
     */
    @FXML
    void onActionSignOut(ActionEvent event) {
        //also sign out
        Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure you want to sign out?", ButtonType.YES, ButtonType.CANCEL);
        alert.setTitle("Sign Out");
        Optional<ButtonType> clickButton = alert.showAndWait();
        if (clickButton.isPresent() && clickButton.get() == ButtonType.YES) {
            try {
                SceneChange.sceneChange(event, "/view/Login.fxml", "Login");
            } catch (NullPointerException | IOException e) {
                ExceptionHandlers.nextPageError(event);
            }
        }
    }

    /**
     * Closes the database connection and the application.
     * @param event The exit button is clicked
     */
    @FXML
    void onActionExit(ActionEvent event) {
        DBConnection.closeConnection();
        System.exit(0);
    }

    /**
     * When customer is selected in the customer table, displays their appointments.
     * @param event Customer clicked in customer table
     */
    @FXML
    void onMouseClickedCustomer(MouseEvent event) {
        //when customer clicked, view their appointments
        try {
            int cId = customersTable.getSelectionModel().getSelectedItem().getId();

            whoLbl.setText(customersTable.getSelectionModel().getSelectedItem().getName());
            appointmentsTable.setItems(DBAppointments.getApptsByCustomer(cId));

        } catch (NullPointerException e)
        {
            whoLbl.setText("All Customers");
            appointmentsTable.setItems(DBAppointments.getAllAppointments());
        }
    }

    /**
     * Clears the selected customer and displays all appointments.
     * @param event The clear selection button is clicked
     */
    @FXML
    void clearSelectedCustomer(ActionEvent event) {
        customersTable.getSelectionModel().clearSelection();
        whoLbl.setText("All Customers");
        appointmentsTable.setItems(DBAppointments.getAllAppointments());
    }

    /**
     * Displays all appointments for all customers.
     * @param event The All Dates radio button is clicked
     */
    @FXML
    void onActionAllDatesRB(ActionEvent event) {
        customersTable.getSelectionModel().clearSelection();
        whoLbl.setText("All Customers");
        appointmentsTable.setItems(DBAppointments.getAllAppointments());
    }

    /**
     * Displays appointments for all customers in the current month.
     * @param event The This Month radio button is clicked
     */
    @FXML
    void onActionThisMonthRB(ActionEvent event) {
        customersTable.getSelectionModel().clearSelection();
        whoLbl.setText("All Customers");
        String month = LocalDate.now().getMonth().toString().toLowerCase();
        String mFirstLetter = month.substring(0,1).toUpperCase();
        month = mFirstLetter + month.substring(1);
        appointmentsTable.setItems(DBAppointments.getApptsByMonth(LocalDate.now().getYear(), month));
    }

    /**
     * Displays appointments for all customers in a week beginning on the current day.
     * @param event The This Week radio button is clicked
     */
    @FXML
    void onActionThisWeekRB(ActionEvent event) {
        customersTable.getSelectionModel().clearSelection();
        whoLbl.setText("All Customers");
        appointmentsTable.setItems(DBAppointments.appointmentsByDates(LocalDate.now(), LocalDate.now().plusWeeks(1)));
    }

    /**
     * Fills the customers and appointments tables with data from database
     * @param url The location used
     * @param rb The resources used
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        //fill customers table
        customersTable.setItems(DBCustomers.getAllCustomers());
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));

        //fill appointments table
        appointmentsTable.setItems(DBAppointments.getAllAppointments());
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endDateCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));

        welcomeLbl.setText("Welcome, " + User.getUsername());
    }

}
