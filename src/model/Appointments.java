package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointments {

    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;
    private int customerId;
    private int userId;
    private int contactId;

    /**
     * The appointments object constructor
     * @param id Appointment id integer
     * @param title Appointment title string
     * @param description Appointment description string
     * @param location Appointment location string
     * @param type Appointment type string
     * @param startDate Appointment start date LocalDate
     * @param startTime Appointment start time LocalTime
     * @param endDate Appointment end date LocalDate
     * @param endTime Appointment end time LocalTime
     * @param customerId Appointment customer id integer
     * @param userId Appointment user id integer
     * @param contactId Appointment contact id integer
     */
    public Appointments(int id, String title, String description, String location, String type, LocalDate startDate, LocalTime startTime,
                        LocalDate endDate, LocalTime endTime, int customerId, int userId, int contactId)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**
     * Gets and returns appointment id
     * @return Appointment id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the appointment id
     * @param id The appointment's id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets and returns appointment title
     * @return Appointment title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the appointment title
     * @param title The appointment's title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets and returns appointment description
     * @return Appointment description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the appointment description
     * @param description The appointment's description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets and returns appointment location
     * @return Appointment location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the appointment location
     * @param location The appointment's location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets and returns appointment type
     * @return Appointment type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the appointment type
     * @param type The appointment's type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets and returns appointment start date
     * @return Appointment start date
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Sets the appointment startDate
     * @param startDate The appointment's startDate
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets and returns appointment start time
     * @return Appointment start time
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Sets the appointment startTime
     * @param startTime The appointment's startTime
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets and returns appointment end date
     * @return Appointment end date
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Sets the appointment endDate
     * @param endDate The appointment's endDate
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets and returns appointment end time
     * @return Appointment end time
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Sets the appointment endTime
     * @param endTime The appointment's endTime
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets and returns appointment customer id
     * @return Appointment customer id
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets the appointment customer id
     * @param customerId The appointment's customer id
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets and returns appointment user id
     * @return Appointment user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the appointment user id
     * @param userId The appointment's user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets and returns appointment contact id
     * @return Appointment contact id
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Sets the appointment contact id
     * @param contactId The appointment's contact id
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
