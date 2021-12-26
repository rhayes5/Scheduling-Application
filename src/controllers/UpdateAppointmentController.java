package controllers;

import DBAccess.DBAppointments;
import DBAccess.DBContacts;
import DBAccess.DBCustomers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class UpdateAppointmentController implements Initializable {

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
     * Updates the appointment in the database when the save button is clicked.  Checks that fields are filled and
     * time data is valid and displays error message if not.
     * @param event The save button is clicked.
     */
    @FXML
    void onActionSave(ActionEvent event) {
        if (CheckValues.isInteger(startHourField.getText()) &&
                CheckValues.isInteger(startMinuteField.getText()) &&
                CheckValues.isInteger(endHourField.getText()) &&
                CheckValues.isInteger(endMinuteField.getText())) {
            if (CheckValues.isAllowableTimeValue(startHourField.getText(), startMinuteField.getText()) &&
                    CheckValues.isAllowableTimeValue(endHourField.getText(), endMinuteField.getText()))
            {
                if(!titleField.getText().isEmpty() && !descriptionField.getText().isEmpty() && !locationField.getText().isEmpty() &&
                        !typeCB.getSelectionModel().isEmpty() && !startAmPmCombo.getSelectionModel().isEmpty() &&
                        !endAmPmCombo.getSelectionModel().isEmpty() && dateField.getValue() != null) {
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

                        LocalDateTime start = LocalDateTime.of(startDate, startTime);
                        LocalDateTime end = LocalDateTime.of(endDate, endTime);

                        boolean endAfterBeginning = CheckValues.endAfterBegin(start, end);
                        if (endAfterBeginning) {
                            boolean inBusinessHours = CheckValues.isInBusinessHours(start, end);
                            if (inBusinessHours) {
                                int contactId = contactComboBox.getSelectionModel().getSelectedItem().getId();
                                int customerId = customerComboBox.getSelectionModel().getSelectedItem().getId();
                                int appointmentId = Integer.parseInt(idField.getText());
                                if (CheckValues.checkCustomerAppointmentOverlap(customerId, appointmentId, start, end)) {
                                    boolean saved = DBAppointments.updateAppointment(appointmentId, title, description, location, type,
                                            start, end, User.getUsername(), customerId, User.getId(), contactId);
                                    System.out.println(saved);
                                    if (saved) {
                                        Alert successAlert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment updated.", ButtonType.OK);
                                        successAlert.showAndWait();
                                        SceneChange.sceneChange(event, "/view/MainMenu.fxml", "Main Menu");
                                    } else {
                                        Alert failedAlert = new Alert(Alert.AlertType.ERROR, "Unable to save updates.");
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
     * Loads the current appointment information when the page loads.
     * @param a The appointment being updated
     */
    public void setAppointment(Appointments a)
    {
        idField.setText(String.valueOf(a.getId()));
        titleField.setText(a.getTitle());
        descriptionField.setText(a.getDescription());
        locationField.setText(a.getLocation());
        typeCB.setValue(a.getType());
        dateField.setValue(a.getStartDate());
        customerComboBox.setValue(DBCustomers.getCustomerById(a.getCustomerId()));
        contactComboBox.setValue(DBContacts.getContactById(a.getContactId()));

        if (a.getStartTime().getHour() < 12)
        {
            startAmPmCombo.setValue("AM");
            startHourField.setText(String.valueOf(a.getStartTime().getHour()));
        }
        else if (a.getStartTime().getHour() == 12) {
            startAmPmCombo.setValue("PM");
            startHourField.setText(String.valueOf(a.getStartTime().getHour()));
        }
        else {
            startAmPmCombo.setValue("PM");
            startHourField.setText(String.valueOf(a.getStartTime().getHour() - 12));
        }
        startMinuteField.setText(String.format("%02d", a.getStartTime().getMinute()));

        if (a.getEndTime().getHour() < 12) {
            endAmPmCombo.setValue("AM");
            endHourField.setText(String.valueOf(a.getEndTime().getHour()));
        }
        else if (a.getEndTime().getHour() == 12) {
            endAmPmCombo.setValue("PM");
            endHourField.setText(String.valueOf(a.getEndTime().getHour()));
        }
        else {
            endAmPmCombo.setValue("PM");
            endHourField.setText(String.valueOf(a.getEndTime().getHour() - 12));
        }
        endMinuteField.setText(String.format("%02d", a.getEndTime().getMinute()));
    }

    /**
     * Initializes the page and sets the items in the combo boxes.
     * @param url The location used
     * @param rb The resources used
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        //initialize combo boxes
        typeCB.setItems(AppointmentTypes.getTypes());
        contactComboBox.setItems(DBContacts.getAllContacts());
        startAmPmCombo.setItems(TimeValues.amPm);
        endAmPmCombo.setItems(TimeValues.amPm);
        customerComboBox.setItems(DBCustomers.getAllCustomers());
    }
}
