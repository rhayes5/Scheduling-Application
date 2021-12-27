package controllers;

import DBAccess.DBCountries;
import DBAccess.DBCustomers;
import DBAccess.DBFirstLevelDivisions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateCustomerController implements Initializable {

    @FXML
    private TextField customerIdTextField;

    @FXML
    private TextField customerNameTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private ComboBox<Countries> countryComboBox;

    @FXML
    private ComboBox<FirstLevelDivisions> divisionComboBox;

    @FXML
    private TextField postalCodeTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

    /**
     * Sets the first level division combo box with items corresponding to the country chosen in the country combo box.
     * @param event A country is chosen in the country combo box
     */
    @FXML
    void countryChosen(ActionEvent event) {
        int cId = countryComboBox.getSelectionModel().getSelectedItem().getId();
        divisionComboBox.setItems(DBFirstLevelDivisions.getFLDByCountry(cId));
        divisionComboBox.setValue(null);
    }

    /**
     * Returns to the main menu when the cancel button is clicked.
     * @param event The cancel button is clicked
     */
    @FXML
    void onActionCancel(ActionEvent event) {
        try {
            SceneChange.sceneChange(event, "/view/MainMenu.fxml", "Main Menu");
        } catch (NullPointerException | IOException e) {
            ExceptionHandlers.nextPageError(event);
        }
    }

    /**
     * Updates the customer in the database when the save button is clicked.  Displays an error if any fields are empty.
     * Displays a confirmation if successful and returns to the main menu or displays an error if not.
     * @param event The save button is clicked.
     */
    @FXML
    void onActionSave(ActionEvent event) {
        if (!customerNameTextField.getText().isEmpty() && !addressTextField.getText().isEmpty() &&
                !countryComboBox.getSelectionModel().isEmpty() && !divisionComboBox.getSelectionModel().isEmpty() &&
                !postalCodeTextField.getText().isEmpty() && !phoneTextField.getText().isEmpty()) {
            int id = Integer.parseInt(customerIdTextField.getText());
            String name = customerNameTextField.getText();
            String address = addressTextField.getText();
            String postalCode = postalCodeTextField.getText();
            String phone = phoneTextField.getText();
            String user = User.getUsername();
            ;
            int division = divisionComboBox.getSelectionModel().getSelectedItem().getId();
            boolean saved = DBCustomers.updateCustomer(id, name, address, postalCode, phone, user, division);
            if (saved) {
                Alert successAlert = new Alert(Alert.AlertType.CONFIRMATION, "Customer updated.");
                successAlert.showAndWait();
                try {
                    SceneChange.sceneChange(event, "/view/MainMenu.fxml", "Main Menu");
                } catch (NullPointerException | IOException e) {
                    ExceptionHandlers.nextPageError(event);
                }
            } else {
                Alert failedAlert = new Alert(Alert.AlertType.ERROR, "Unable to save updates.");
                failedAlert.showAndWait();
            }
        } else {
            Alert missingFieldsAlert = new Alert(Alert.AlertType.ERROR, "Please fill all fields.", ButtonType.OK);
            missingFieldsAlert.showAndWait();
        }
    }

    /**
     * Loads the current customer information when the page loads.
     * @param customer The customer being updated
     */
    public void getCurrentCustomerData(Customer customer)
    {
        customerIdTextField.setText(String.valueOf(customer.getId()));
        customerNameTextField.setText(customer.getName());
        addressTextField.setText(customer.getAddress());
        int division = customer.getDivision();
        int countryId = DBFirstLevelDivisions.getDivisionById(division).getCountryId();
        countryComboBox.setValue(DBCountries.getCountryById(countryId));
        divisionComboBox.setItems(DBFirstLevelDivisions.getFLDByCountry(countryId));
        divisionComboBox.setValue(DBFirstLevelDivisions.getDivisionById(customer.getDivision()));
        postalCodeTextField.setText(customer.getPostalCode());
        phoneTextField.setText(customer.getPhone());
    }

    /**
     * Initializes the page and sets items in the countries combo box.
     * @param url The location used
     * @param rb The resources used
     */
    public void initialize(URL url, ResourceBundle rb) {
        countryComboBox.setItems(DBCountries.getAllCountries());
    }
}