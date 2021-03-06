package DBAccess;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Appointments;
import model.Customer;
import model.FirstLevelDivisions;
import model.Months;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

public class DBAppointments {

    /**
     * Retrieves from the database and returns an observable list of all appointments from the database.
     * @return An observable list of all appointments
     */
    public static ObservableList<Appointments> getAllAppointments() {
        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");

                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                ZonedDateTime startZoned = start.atZone(ZoneId.systemDefault());
                LocalDate startDate = startZoned.toLocalDate();
                LocalTime startTime = startZoned.toLocalTime();
                String formattedStart = getObservableTime(startTime);

                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                ZonedDateTime endZoned = end.atZone(ZoneId.systemDefault());
                LocalDate endDate = endZoned.toLocalDate();
                LocalTime endTime = endZoned.toLocalTime();
                String formattedEnd = getObservableTime(endTime);

                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointments c = new Appointments(id, title, description, location, type, startDate, startTime,
                        formattedStart, endDate, endTime, formattedEnd, customerId, userId, contactId);
                appointmentsList.add(c);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentsList;
    }

    /**
     * Takes a customer ID and returns an observable list of all appointments in the database for that customer.
     * @param cId The customer's ID
     * @return an observable list of appointments for a specific customer
     */
    public static ObservableList<Appointments> getApptsByCustomer(int cId)
    {
        ObservableList<Appointments> apptsByCustomerList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments WHERE customer_id = " + cId;

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");

                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                ZonedDateTime startZoned = start.atZone(ZoneId.systemDefault());
                LocalDate startDate = startZoned.toLocalDate();
                LocalTime startTime = startZoned.toLocalTime();
                String formattedStart = getObservableTime(startTime);

                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                ZonedDateTime endZoned = end.atZone(ZoneId.systemDefault());
                LocalDate endDate = endZoned.toLocalDate();
                LocalTime endTime = endZoned.toLocalTime();
                String formattedEnd = getObservableTime(endTime);

                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointments c = new Appointments(id, title, description, location, type, startDate, startTime,
                        formattedStart, endDate, endTime, formattedEnd, customerId, userId, contactId);
                apptsByCustomerList.add(c);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return apptsByCustomerList;
    }

    /**
     * Returns a string formatting the time as h:mm with am or pm
     * @param l the local time to format
     * @return a string formatted as h:mm with am or pm
     */
    public static String getObservableTime(LocalTime l) {
        return l.format(DateTimeFormatter.ofPattern("h:mm a"));
    }

    /**
     * Takes a contact ID and returns an observable list of all appointments from the database for that contact.
     * @param cId The contact ID
     * @return An observable list of appointments for a specific contact
     */
    public static ObservableList<Appointments> getApptsByContact(int cId)
    {
        ObservableList<Appointments> apptsByContactList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments WHERE contact_id = " + cId + " ORDER BY start";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");

                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                ZonedDateTime startZoned = start.atZone(ZoneId.systemDefault());
                LocalDate startDate = startZoned.toLocalDate();
                LocalTime startTime = startZoned.toLocalTime();
                String formattedStart = getObservableTime(startTime);

                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                ZonedDateTime endZoned = end.atZone(ZoneId.systemDefault());
                LocalDate endDate = endZoned.toLocalDate();
                LocalTime endTime = endZoned.toLocalTime();
                String formattedEnd = getObservableTime(endTime);

                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");

                Appointments c = new Appointments(id, title, description, location, type, startDate, startTime,
                        formattedStart, endDate, endTime, formattedEnd, customerId, userId, cId);
                apptsByContactList.add(c);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return apptsByContactList;
    }

    /**
     * Searches for appointments in the database in a specific month and returns a list of those appointments.
     * @param year The 4-digit year integer to search
     * @param month The month string to search
     * @return An observable list of all appointments for that month
     */
    public static ObservableList<Appointments> getApptsByMonth(Integer year, String month)
    {
        ObservableList<Appointments> apptsByMonthList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments ";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                ZonedDateTime startZoned = start.atZone(ZoneId.systemDefault());
                LocalDate startDate = startZoned.toLocalDate();
                LocalTime startTime = startZoned.toLocalTime();
                int y = startDate.getYear();
                int m = startDate.getMonthValue();
                int searchedMonth = Months.getMonths().indexOf(month) + 1;

                if(y == year && m == searchedMonth) {
                    int id = rs.getInt("Appointment_ID");
                    String title = rs.getString("Title");
                    String description = rs.getString("Description");
                    String location = rs.getString("Location");
                    String type = rs.getString("Type");

                    String formattedStart = getObservableTime(startTime);
                    LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                    ZonedDateTime endZoned = end.atZone(ZoneId.systemDefault());
                    LocalDate endDate = endZoned.toLocalDate();
                    LocalTime endTime = endZoned.toLocalTime();
                    String formattedEnd = getObservableTime(endTime);

                    int customerId = rs.getInt("Customer_ID");
                    int userId = rs.getInt("User_ID");
                    int contactId = rs.getInt("Contact_ID");

                    Appointments c = new Appointments(id, title, description, location, type, startDate, startTime,
                            formattedStart, endDate, endTime, formattedEnd, customerId, userId, contactId);
                    apptsByMonthList.add(c);
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return apptsByMonthList;
    }

    /**
     * Searches for appointments in the database of a specific type, month, and year and returns a list of those appointments.
     * @param year the 4-digit year
     * @param month the month string
     * @param type the appointment type string
     * @return An observable list of all appointments of that type in the month and year
     */
    public static ObservableList<Appointments> getApptsByMonthAndType(Integer year, String month, String type)
    {
        ObservableList<Appointments> apptsByMonthList = FXCollections.observableArrayList();
        ObservableList<Appointments> apptsByMonthAndTypeList = FXCollections.observableArrayList();

        apptsByMonthList = getApptsByMonth(year, month);
        for (Appointments a : apptsByMonthList)
        {
            if (a.getType().equals(type))
            {
                apptsByMonthAndTypeList.add(a);
            }
        }
        return apptsByMonthAndTypeList;
    }

    /**
     * Searches for appointments in the database with a specific country ID and returns a list of those appointments.
     * @param countryId The country ID integer
     * @return An observable list of the appointments with a specific country ID
     */
    public static ObservableList<Appointments> getApptsByCountry(int countryId)
    {
        ObservableList<Appointments> apptsByCountryList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int customerId = rs.getInt("Customer_ID");

                Customer c = DBCustomers.getCustomerById(customerId);
                FirstLevelDivisions f = DBFirstLevelDivisions.getDivisionById(c.getDivision());

                if(f.getCountryId() == countryId) {
                    int id = rs.getInt("Appointment_ID");
                    String title = rs.getString("Title");
                    String description = rs.getString("Description");
                    String location = rs.getString("Location");
                    String type = rs.getString("Type");

                    LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                    ZonedDateTime startZoned = start.atZone(ZoneId.systemDefault());
                    LocalDate startDate = startZoned.toLocalDate();
                    LocalTime startTime = startZoned.toLocalTime();
                    String formattedStart = getObservableTime(startTime);

                    LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                    ZonedDateTime endZoned = end.atZone(ZoneId.systemDefault());
                    LocalDate endDate = endZoned.toLocalDate();
                    LocalTime endTime = endZoned.toLocalTime();
                    String formattedEnd = getObservableTime(endTime);


                    int userId = rs.getInt("User_ID");
                    int contactId = rs.getInt("Contact_ID");

                    Appointments a = new Appointments(id, title, description, location, type, startDate, startTime,
                            formattedStart, endDate, endTime, formattedEnd, customerId, userId, contactId);
                    apptsByCountryList.add(a);
                }
            }
        }
        catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        return apptsByCountryList;
    }

    /**
     * Deletes an appointment from the database with a specific id after user confirmation.
     * @param id The appointment ID integer
     * @return True if deleted successfully or false if not
     */
    public static boolean deleteAppointment(int id) {
        Alert areYouSureAlert = new Alert(Alert.AlertType.WARNING, "Are you sure you want to delete this appointment?", ButtonType.YES, ButtonType.CANCEL);
        Optional<ButtonType> clickButton = areYouSureAlert.showAndWait();

        if (clickButton.isPresent() && clickButton.get() == ButtonType.YES) {
            try {
                Appointments a = getAppointmentById(id);
                String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
                PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
                ps.setInt(1, id);
                ps.execute();
                String successAlertContent = "Appointment " + id + " successfully deleted. Type: " + a.getType();
                Alert successAlert = new Alert(Alert.AlertType.CONFIRMATION, successAlertContent, ButtonType.OK);
                successAlert.showAndWait();
                return true;
            } catch (SQLIntegrityConstraintViolationException e) {
                Alert cannotDeleteAlert = new Alert(Alert.AlertType.ERROR, "Cannot delete appointment.", ButtonType.OK);
                cannotDeleteAlert.showAndWait();
            }
            catch (SQLException | NullPointerException e) {
                e.printStackTrace();
            }
        }
        //if not found
        return false;
    }

    /**
     * Searches for an appointment by id in the database and returns a single appointment if one is found or null if not.
     * @param id The integer appointment id to search for
     * @return An appointment with the matching id
     */
    public static Appointments getAppointmentById(int id) {

        try {
            String sql = "SELECT * FROM appointments WHERE Appointment_ID = ?" ;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            rs.next();
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");

            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            ZonedDateTime startZoned = start.atZone(ZoneId.systemDefault());
            LocalDate startDate = startZoned.toLocalDate();
            LocalTime startTime = startZoned.toLocalTime();
            String formattedStart = getObservableTime(startTime);

            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            ZonedDateTime endZoned = end.atZone(ZoneId.systemDefault());
            LocalDate endDate = endZoned.toLocalDate();
            LocalTime endTime = endZoned.toLocalTime();
            String formattedEnd = getObservableTime(endTime);

            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");

            return new Appointments(id, title, description, location, type, startDate, startTime, formattedStart,
                    endDate, endTime, formattedEnd, customerId, userId, contactId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;    //none found
    }

    /**
     * Adds a new appointment to the database.
     * @param title The title of the appointment string
     * @param description A description of the appointment string
     * @param location The location of the appointment string
     * @param type The type of appointment string
     * @param startTimestamp The appointment start time timestamp
     * @param endTimestamp The appointment end time timestamp
     * @param user The name of the current user string
     * @param customerId The appointment's customer ID integer
     * @param userId The current user ID integer
     * @param contactId The appointment's contact ID integer
     * @return True if new appointment added successfully or false if not
     */
    public static boolean addNewAppointment(String title, String description, String location, String type,
                                         Timestamp startTimestamp, Timestamp endTimestamp,
                                         String user, int customerId, int userId, int contactId)
    {
        try {
            String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, " +
                    "Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                    "VALUES(?, ?, ?, ?, ?, ?, NOW(), ?, NOW(), ?, ?, ?, ?)";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, startTimestamp);
            ps.setTimestamp(6, endTimestamp);
            ps.setString(7,user);
            ps.setString(8, user);
            ps.setInt(9, customerId);
            ps.setInt(10, userId);
            ps.setInt(11, contactId);

            ps.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates an existing appointment in the database.
     * @param appointmentId The id of the existing appointment
     * @param title The title of the appointment string
     * @param description A description of the appointment string
     * @param location The location of the appointment string
     * @param type The type of appointment string
     * @param start The appointment start time LocalDateTime
     * @param end The appointment end time LocalDateTime
     * @param user The name of the current user string
     * @param customerId The appointment's customer ID integer
     * @param userId The current user ID integer
     * @param contactId The appointment's contact ID integer
     * @return True if new appointment updated successfully or false if not
     */
    public static boolean updateAppointment(int appointmentId, String title, String description, String location, String type,
                                         LocalDateTime start, LocalDateTime end,
                                         String user, int customerId, int userId, int contactId)
    {
        Timestamp startTimestamp = Timestamp.valueOf(start);
        Timestamp endTimestamp = Timestamp.valueOf(end);

        try {
            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?," +
                    "Last_Update = NOW(), Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? " +
                    "WHERE Appointment_ID = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, startTimestamp);
            ps.setTimestamp(6, endTimestamp);
            ps.setString(7,user);
            ps.setInt(8, customerId);
            ps.setInt(9, userId);
            ps.setInt(10, contactId);
            ps.setInt(11, appointmentId);

            ps.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gets and returns an observable list of the appointments for the current day in the database.
     * @param now The current localDateTime
     * @return An observable list of appointments on a specific date.
     */
    public static List<Appointments> appointmentsToday(LocalDateTime now) {
        List<Appointments> a = new ArrayList<>();
        ObservableList<Appointments> all = getAllAppointments();

        for (Appointments appointment : all)
        {
            if (appointment.getStartDate().equals(now.toLocalDate()) &&
                    (appointment.getStartTime().isAfter(now.toLocalTime().minusSeconds(1)) && appointment.getStartTime().isBefore(now.toLocalTime().plusMinutes(15).plusSeconds(1)))) {
                a.add(appointment);
            }
        }
        return a;
    }

    /**
     * Searches for appointments between 2 dates in the database and returns an Observable List of appointments.
     * @param startSearch The local date to begin the search
     * @param endSearch The local date to end the search
     * @return An observable list of appointments.
     */
    public static ObservableList<Appointments> appointmentsByDates(LocalDate startSearch, LocalDate endSearch)
    {
        ObservableList<Appointments> apptsByWeekList = FXCollections.observableArrayList();

        LocalDateTime startSearchLDT = LocalDateTime.of(startSearch, startSearch.atStartOfDay().toLocalTime());
        LocalDateTime endSearchLDT = LocalDateTime.of(endSearch.plusDays(1), endSearch.atStartOfDay().toLocalTime());

        Timestamp startTimestamp = Timestamp.valueOf(startSearchLDT);
        Timestamp endTimestamp = Timestamp.valueOf(endSearchLDT);
        try {
            String sql = "SELECT * from appointments WHERE start >= ? AND start < ? ORDER BY start";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setTimestamp(1, startTimestamp);
            ps.setTimestamp(2, endTimestamp);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");

                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                ZonedDateTime startZoned = start.atZone(ZoneId.systemDefault());
                LocalDate startDate = startZoned.toLocalDate();
                LocalTime startTime = startZoned.toLocalTime();
                String formattedStart = getObservableTime(startTime);

                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                ZonedDateTime endZoned = end.atZone(ZoneId.systemDefault());
                LocalDate endDate = endZoned.toLocalDate();
                LocalTime endTime = endZoned.toLocalTime();
                String formattedEnd = getObservableTime(endTime);

                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointments c = new Appointments(id, title, description, location, type, startDate, startTime,
                        formattedStart, endDate, endTime, formattedEnd, customerId, userId, contactId);
                apptsByWeekList.add(c);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return apptsByWeekList;
    }
}


