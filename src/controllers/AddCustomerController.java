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

public class AddCustomerController implements Initializable {

    @FXML
    private TextField customerIdTextField;

    @FXML
    private TextField customerNameTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField postalCodeTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private ComboBox<Countries> countryComboBox;

    @FXML
    private ComboBox<FirstLevelDivisions> divisionComboBox;

    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

    /**
     * Displays all the first level divisions for a given country when a country is chosen in the country combo box and
     * allows the first level division combo box to be used.
     * @param event A country is chosen in the country combo box
     */
    @FXML
    void countryChosen(ActionEvent event) {
        int cId = countryComboBox.getSelectionModel().getSelectedItem().getId();
        divisionComboBox.setItems(DBFirstLevelDivisions.getFLDByCountry(cId));
        divisionComboBox.setDisable(false);
    }

    /**
     * Returns to the main menu without adding the new customer.
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
     * Attempts to save the entered data as a new customer.  Displays an error if any fields are empty.  Displays a
     * confirmation if successful and returns to the main menu or displays an error if not.
     * @param event The save button is clicked
     */
    @FXML
    void onActionSave(ActionEvent event) {
        if (!customerNameTextField.getText().isEmpty() && !addressTextField.getText().isEmpty() &&
                !countryComboBox.getSelectionModel().isEmpty() && !divisionComboBox.getSelectionModel().isEmpty() &&
                !postalCodeTextField.getText().isEmpty() && !phoneTextField.getText().isEmpty()) {
            String name = customerNameTextField.getText();
            String address = addressTextField.getText();
            String postalCode = postalCodeTextField.getText();
            String phone = phoneTextField.getText();
            String user = User.getUsername();
            ;
            int division = divisionComboBox.getSelectionModel().getSelectedItem().getId();
            boolean saved = DBCustomers.saveNewCustomer(name, address, postalCode, phone, user, division);
            if (saved) {
                Alert successAlert = new Alert(Alert.AlertType.CONFIRMATION, "Customer saved.", ButtonType.OK);
                successAlert.showAndWait();
                try {
                    SceneChange.sceneChange(event, "/view/MainMenu.fxml", "Main Menu");
                } catch (NullPointerException | IOException e) {
                    ExceptionHandlers.nextPageError(event);
                }
            } else {
                Alert failedAlert = new Alert(Alert.AlertType.ERROR, "Unable to save customer.");
                failedAlert.showAndWait();
            }
        } else {
            Alert missingFieldsAlert = new Alert(Alert.AlertType.ERROR, "Please fill all fields.", ButtonType.OK);
            missingFieldsAlert.showAndWait();
        }
    }

    /**
     * Fills the country combo box when initialized.
     * @param url The location
     * @param rb The resource bundle
     */
    public void initialize(URL url, ResourceBundle rb) {
         countryComboBox.setItems(DBCountries.getAllCountries());
    }
}


