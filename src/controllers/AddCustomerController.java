package controllers;

import DBAccess.DBCountries;
import DBAccess.DBCustomers;
import DBAccess.DBFirstLevelDivisions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
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

    @FXML
    void onActionSave(ActionEvent event) {
        String name = customerNameTextField.getText();
        String address = addressTextField.getText();
        String postalCode = postalCodeTextField.getText();
        String phone = phoneTextField.getText();
        String user = User.getUsername();;
        int division = divisionComboBox.getSelectionModel().getSelectedItem().getId();
        boolean saved = DBCustomers.saveNewCustomer(name, address, postalCode, phone, user, division);
        if (saved) {
            Alert successAlert = new Alert(Alert.AlertType.CONFIRMATION, "Customer saved.");
            successAlert.showAndWait();
            try {
                SceneChange.sceneChange(event, "/view/MainMenu.fxml", "Main Menu");
                /*
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                stage.setTitle("Main Menu");
                stage.setScene(new Scene(root));
                stage.show();

                 */
            } catch (NullPointerException | IOException e) {
                ExceptionHandlers.nextPageError(event);
            }
        } else {
            Alert failedAlert = new Alert(Alert.AlertType.ERROR, "Unable to save customer.");
            failedAlert.showAndWait();
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


