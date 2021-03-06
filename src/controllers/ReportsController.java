package controllers;

import DBAccess.DBAppointments;
import DBAccess.DBContacts;
import DBAccess.DBCountries;
import SchedulingApplication.FileIO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    @FXML
    private TableView<Appointments> appointmentsByTypeTableView;

    @FXML
    private TableColumn<Appointments, Integer> apptIdColByType;

    @FXML
    private TableColumn<Appointments, String> titleColByType;

    @FXML
    private TableColumn<Appointments, String> typeColByType;

    @FXML
    private TableColumn<Appointments, String> descriptionColByType;

    @FXML
    private TableColumn<Appointments, String> locationColByType;

    @FXML
    private TableColumn<Appointments, LocalDate> dateColByType;

    @FXML
    private TableColumn<Appointments, LocalTime> startTimeColByType;

    @FXML
    private TableColumn<Appointments, LocalTime> endTimeColByType;

    @FXML
    private TableColumn<Appointments, Integer> custIdColByType;

    @FXML
    private TableView<Appointments> appointmentByMonthTableView;

    @FXML
    private TableColumn<Appointments, Integer> apptIdColByMonth;

    @FXML
    private TableColumn<Appointments, String> titleColByMonth;

    @FXML
    private TableColumn<Appointments, String> typeColByMonth;

    @FXML
    private TableColumn<Appointments, String> descriptionColByMonth;

    @FXML
    private TableColumn<Appointments, String> locationColByMonth;

    @FXML
    private TableColumn<Appointments, LocalDate> dateColByMonth;

    @FXML
    private TableColumn<Appointments, String> startTimeColByMonth;

    @FXML
    private TableColumn<Appointments, String> endTimeColByMonth;

    @FXML
    private TableColumn<Appointments, Integer> custIdColByMonth;

    @FXML
    private TextField yearTextField;

    @FXML
    private Button monthSearchBtn;

    @FXML
    private Tab appointmentsByDateTab;

    @FXML
    private TableView<Appointments> appointmentByDateTableView;

    @FXML
    private TableColumn<Appointments, Integer> apptIdColByDate;

    @FXML
    private TableColumn<Appointments, String> titleColByDate;

    @FXML
    private TableColumn<Appointments, String> typeColByDate;

    @FXML
    private TableColumn<Appointments, String> descriptionColByDate;

    @FXML
    private TableColumn<Appointments, String> locationColByDate;

    @FXML
    private TableColumn<Appointments, LocalDate> dateColByDate;

    @FXML
    private TableColumn<Appointments, String> startTimeColByDate;

    @FXML
    private TableColumn<Appointments, String> endTimeColByDate;

    @FXML
    private TableColumn<Appointments, Integer> custIdColByDate;

    @FXML
    private Label totalByDateLbl;

    @FXML
    private DatePicker startingDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private Button searchDatesBtn;

    @FXML
    private ComboBox<String> typeCB;

    @FXML
    private ComboBox<String> monthCB;

    @FXML
    private Tab appointmentsByCountryTab;

    @FXML
    private TableView<Appointments> apointmentsByCountryTableView;

    @FXML
    private TableColumn<Appointments, Integer> apptIdColByCountry;

    @FXML
    private TableColumn<Appointments, String> titleColByCountry;

    @FXML
    private TableColumn<Appointments, String> typeColByCountry;

    @FXML
    private TableColumn<Appointments, String> descriptionColByCountry;

    @FXML
    private TableColumn<Appointments, String> locationColByCountry;

    @FXML
    private TableColumn<Appointments, LocalDate> dateColByCountry;

    @FXML
    private TableColumn<Appointments, String> startTimeColByCountry;

    @FXML
    private TableColumn<Appointments, String> endTimeColByCountry;

    @FXML
    private TableColumn<Appointments, Integer> custIdColByCountry;

    @FXML
    private ComboBox<Countries> countryCB;

    @FXML
    private Label totalByCountryLbl;

    @FXML
    private Tab appointmentsByContactTab;

    @FXML
    private Label totalByMonthLbl;

    @FXML
    private Label totalByTypeLbl;

    @FXML
    private TableView<Appointments> appointmentByContactsTableView;

    @FXML
    private TableColumn<Appointments, Integer> apptIdCol;

    @FXML
    private TableColumn<Appointments, String> titleCol;

    @FXML
    private TableColumn<Appointments, String> typeCol;

    @FXML
    private TableColumn<Appointments, String> descriptionCol;

    @FXML
    private TableColumn<Appointments, String> locationCol;

    @FXML
    private TableColumn<Appointments, LocalDate> dateCol;

    @FXML
    private TableColumn<Appointments, String> startTimeCol;

    @FXML
    private TableColumn<Appointments, String> endTimeCol;

    @FXML
    private TableColumn<Appointments, Integer> custIdCol;

    @FXML
    private Label totalApptsLbl;

    @FXML
    private ComboBox<Contact> contactCBox;

    @FXML
    private Tab loginAttemptsTab;

    @FXML
    private TextArea loginAttemptsTextArea;

    @FXML
    private Button backBtn;

    /**
     * Filters appointments scheduled in a specific year and month when a year is entered and a month is selected from a
     * combo box and displays those appointments in a table view.
     * @param event The search button is selected on the month tab
     */
    @FXML
    void onActionMonthSearchBtn(ActionEvent event) {
        if (CheckValues.isYear(yearTextField.getText())) {
            appointmentByMonthTableView.setItems(DBAppointments.getApptsByMonthAndType(Integer.valueOf(yearTextField.getText()), monthCB.getSelectionModel().getSelectedItem(), typeCB.getSelectionModel().getSelectedItem()));
            apptIdColByMonth.setCellValueFactory(new PropertyValueFactory<>("id"));
            titleColByMonth.setCellValueFactory(new PropertyValueFactory<>("title"));
            typeColByMonth.setCellValueFactory(new PropertyValueFactory<>("type"));
            descriptionColByMonth.setCellValueFactory(new PropertyValueFactory<>("description"));
            locationColByMonth.setCellValueFactory(new PropertyValueFactory<>("location"));
            dateColByMonth.setCellValueFactory(new PropertyValueFactory<>("startDate"));
            startTimeColByMonth.setCellValueFactory(new PropertyValueFactory<>("formattedStart"));
            endTimeColByMonth.setCellValueFactory(new PropertyValueFactory<>("formattedEnd"));
            custIdColByMonth.setCellValueFactory(new PropertyValueFactory<>("customerId"));

            totalByMonthLbl.setText(String.valueOf(appointmentByMonthTableView.getItems().size()));
        } else {
            Alert invalidYearAlert = new Alert(Alert.AlertType.ERROR, "Please enter a valid 4-digit year.", ButtonType.OK);
            invalidYearAlert.showAndWait();
        }
    }

    /**
     * Filters appointments by a specific country when a country is selected from a combo box and displays those appointments
     * in a table view.
     * @param event A specific country is selected from the Country combo box
     */
    @FXML
    void onActionCountrySelected(ActionEvent event) {
        apointmentsByCountryTableView.setItems(DBAppointments.getApptsByCountry(countryCB.getSelectionModel().getSelectedItem().getId()));
        apptIdColByCountry.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColByCountry.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColByCountry.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeColByCountry.setCellValueFactory(new PropertyValueFactory<>("type"));
        locationColByCountry.setCellValueFactory(new PropertyValueFactory<>("location"));
        dateColByCountry.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        startTimeColByCountry.setCellValueFactory(new PropertyValueFactory<>("formattedStart"));
        endTimeColByCountry.setCellValueFactory(new PropertyValueFactory<>("formattedEnd"));
        custIdColByCountry.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        totalByCountryLbl.setText(String.valueOf(apointmentsByCountryTableView.getItems().size()));
    }

    /**
     * Filters appointments by a specific contact when a contact is selected from a combo box and displays those appointments
     * in a table view.
     * @param event A specific contact is selected from the contact combo box
     */
    @FXML
    void onActionContactSelected(ActionEvent event) {
        appointmentByContactsTableView.setItems(DBAppointments.getApptsByContact(contactCBox.getSelectionModel().getSelectedItem().getId()));
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("formattedStart"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("formattedEnd"));
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        totalApptsLbl.setText(String.valueOf(appointmentByContactsTableView.getItems().size()));
    }

    /**
     * Filters appointments by a specific date range when a starting date and end date are selected from a date picker
     * and displays those appointments in a table view.
     * @param event The search button is clicked
     */
    @FXML
    void onActionSearchDatesBtn(ActionEvent event) {
        LocalDate startSearch= startingDate.getValue();
        LocalDate endSearch = endDate.getValue();
        appointmentByDateTableView.setItems(DBAppointments.appointmentsByDates(startSearch, endSearch));
        apptIdColByDate.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColByDate.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColByDate.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeColByDate.setCellValueFactory(new PropertyValueFactory<>("type"));
        locationColByDate.setCellValueFactory(new PropertyValueFactory<>("location"));
        dateColByDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        startTimeColByDate.setCellValueFactory(new PropertyValueFactory<>("formattedStart"));
        endTimeColByDate.setCellValueFactory(new PropertyValueFactory<>("formattedEnd"));
        custIdColByDate.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        totalByDateLbl.setText(String.valueOf(appointmentByDateTableView.getItems().size()));
    }

    /**
     * Returns to the main menu.
     * @param event The back button is clicked
     */
    @FXML
    void onActionBackBtn(ActionEvent event) {
        try {
            SceneChange.sceneChange(event, "/view/MainMenu.fxml", "Main Menu");
        } catch (NullPointerException | IOException e) {
            ExceptionHandlers.nextPageError(event);
        }
    }

    /**
     * Fills all the combo boxes when the page loads and reads and displays data from the login_activity file.
     * @param url The location used
     * @param rb The resources used
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        typeCB.setItems(AppointmentTypes.getTypes());
        monthCB.setItems(Months.getMonths());
        countryCB.setItems(DBCountries.getAllCountries());

        contactCBox.setItems(DBContacts.getAllContacts());
        loginAttemptsTextArea.setText(FileIO.read());
    }
}
