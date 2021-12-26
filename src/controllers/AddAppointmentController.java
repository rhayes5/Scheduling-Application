package controllers;

import DBAccess.DBAppointments;
import DBAccess.DBContacts;
import DBAccess.DBCustomers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {

    Parent root;
    Stage stage;

    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField idField;

    @FXML
    private TextField titleField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField locationField;

    @FXML
    private ComboBox<String> typeCB;

    @FXML
    private DatePicker dateField;

    @FXML
    private TextField startHourField;

    @FXML
    private TextField startMinuteField;

    @FXML
    private ComboBox<String> startAmPmCombo;

    @FXML
    private TextField endHourField;

    @FXML
    private TextField endMinuteField;

    @FXML
    private ComboBox<String> endAmPmCombo;

    @FXML
    private ComboBox<Contact> contactComboBox;

    @FXML
    private ComboBox<Customer> customerComboBox;

    /**
     * Returns to the main menu without adding the new appointment.
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
     * Checks all values entered are the valid type, there are no overlapping appointments, and the times are within
     * business hours and saves the new appointment to the database.
     * @param event The save button is clicked
     */
    @FXML
    void onActionSave(ActionEvent event) {
        //check that all values are correct
        if (CheckValues.isInteger(startHourField.getText()) &&
                CheckValues.isInteger(startMinuteField.getText()) &&
                CheckValues.isInteger(endHourField.getText()) &&
                CheckValues.isInteger(endMinuteField.getText())) {
            if (CheckValues.isAllowableTimeValue(startHourField.getText(), startMinuteField.getText()) &&
                    CheckValues.isAllowableTimeValue(endHourField.getText(), endMinuteField.getText()))
            {
                if(!titleField.getText().isEmpty() && !descriptionField.getText().isEmpty() && !locationField.getText().isEmpty() &&
                    !typeCB.getSelectionModel().isEmpty() && !startAmPmCombo.getSelectionModel().isEmpty() &&
                    !endAmPmCombo.getSelectionModel().isEmpty() && !contactComboBox.getSelectionModel().isEmpty() &&
                    !customerComboBox.getSelectionModel().isEmpty() && dateField.getValue() != null) {
                    try {
                        String title = titleField.getText();
                        String description = descriptionField.getText();
                        String location = locationField.getText();
                        String type = typeCB.getSelectionModel().getSelectedItem();
                        LocalDate startDate = dateField.getValue();
                        LocalDate endDate = dateField.getValue();

                        int startHour = Integer.parseInt(startHourField.getText());
                        int startMin = Integer.parseInt(startMinuteField.getText());
                        String startAmPm = startAmPmCombo.getValue();
                        LocalTime startTime = TimeValues.setTime(startHour, startMin, startAmPm);

                        int endHour = Integer.parseInt(endHourField.getText());
                        int endMin = Integer.parseInt(endMinuteField.getText());
                        String endAmPm = endAmPmCombo.getValue();
                        LocalTime endTime = TimeValues.setTime(endHour, endMin, endAmPm);

                        //check for overlap
                        LocalDateTime start = LocalDateTime.of(startDate, startTime);
                        Timestamp startTimestamp = Timestamp.valueOf(start);
                        LocalDateTime end = LocalDateTime.of(endDate, endTime);
                        Timestamp endTimestamp = Timestamp.valueOf(end);

                        boolean endAfterBeginning = CheckValues.endAfterBegin(start, end);
                        if (endAfterBeginning) {
                            boolean inBusinessHours = CheckValues.isInBusinessHours(start, end);
                            if (inBusinessHours) {
                                int contactId = contactComboBox.getSelectionModel().getSelectedItem().getId();
                                int customerId = customerComboBox.getSelectionModel().getSelectedItem().getId();
                                if (CheckValues.checkCustomerAppointmentOverlap(customerId, start, end)) {

                                    boolean saved = DBAppointments.addNewAppointment(title, description, location, type,
                                            startTimestamp, endTimestamp,
                                            User.getUsername(), customerId, User.getId(), contactId);
                                    if (saved) {
                                        Alert successAlert = new Alert(Alert.AlertType.CONFIRMATION, "New appointment saved.", ButtonType.OK);
                                        successAlert.showAndWait();

                                        SceneChange.sceneChange(event, "/view/MainMenu.fxml", "Main Menu");
                                        /*
                                        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                                        root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                                        stage.setTitle("Main Menu");
                                        stage.setScene(new Scene(root));
                                        stage.show();
                                         */

                                    } else {
                                        Alert failedAlert = new Alert(Alert.AlertType.ERROR, "Unable to save new appointment.");
                                        failedAlert.showAndWait();
                                    }
                                } else {
                                    Alert overlapAlert = new Alert(Alert.AlertType.ERROR, "This appointment overlaps another appointment for this customer.  Please choose a different time.", ButtonType.OK);
                                    overlapAlert.showAndWait();
                                }
                            } else {
                                Alert outsideBusinessHoursAlert = new Alert(Alert.AlertType.ERROR, "Please schedule the appointment during business hours: 8:00 a.m. to 10:00 p.m. EST", ButtonType.OK);
                                outsideBusinessHoursAlert.showAndWait();
                            }
                        }

                    } catch (NullPointerException | IOException e) {
                        ExceptionHandlers.nextPageError(event);
                    }
                }
                else {//if null
                    Alert failedAlert = new Alert(Alert.AlertType.ERROR, "Please enter all fields.");
                    failedAlert.showAndWait();
                }
            }else {
                Alert invalidTimes = new Alert(Alert.AlertType.ERROR, "Please enter an hour between 1 and 12 and minutes between 00 and 60.");
                invalidTimes.showAndWait();
            }
        } else
        {
            Alert notIntegerAlert = new Alert(Alert.AlertType.ERROR, "Please enter integer values for start and end time hours and minutes.");
            notIntegerAlert.showAndWait();
        }
    }

    /**
     * Fills combo boxes when the page initializes.
     * @param url The location used
     * @param rb The resources used
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        typeCB.setItems(AppointmentTypes.getTypes());
        contactComboBox.setItems(DBContacts.getAllContacts());
        startAmPmCombo.setItems(TimeValues.amPm);
        endAmPmCombo.setItems(TimeValues.amPm);
        customerComboBox.setItems(DBCustomers.getAllCustomers());
    }
}
