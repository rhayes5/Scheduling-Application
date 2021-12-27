package model;

import DBAccess.DBAppointments;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.time.*;

public class CheckValues {
    /**
     * Checks if string is an integer and returns true if it is an integer or false if not.
     * @param s String to check
     * @return true if string is integer or else false
     */
    public static boolean isInteger(String s)
    {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Checks to see if string entered is a 4-digit number and returns true if it is or false if not.
     * @param s String to check
     * @return true if a valid 4-digit number or false if not
     */
    public static boolean isYear(String s) {
        if (isInteger(s)) {
            int i = Integer.parseInt(s);
            return i >= 1000 && i <= 9999;
        }
        return false;
    }

    /**
     * Checks to see if valid time was entered and returns true if hours are between 1 and 12 and minutes are between 0 and 59 or false if not.
     * @param h hours input string
     * @param m minutes input string
     * @return true if hours are between 1 and 12 and minutes are between 0 and 59 or false if not
     */
    public static boolean isAllowableTimeValue(String h, String m)
    {
        int hour = Integer.parseInt(h);
        int min = Integer.parseInt(m);
        if (min < 60 && min >= 0)
        {
            return hour <= 12 && hour >= 1;
        }
        return false;
    }

    /**
     * Checks that input beginning time is before ending time and returns true if it is or gives an error and returns false if not.
     * @param t1 starting date and time
     * @param t2 ending date and time
     * @return true if start time is before end time or false if not
     */
    public static boolean endAfterBegin(LocalDateTime t1, LocalDateTime t2) {
        if (t1.toInstant(ZoneOffset.UTC).isBefore(t2.toInstant(ZoneOffset.UTC))) {
            return true;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "End time must be after beginning time.", ButtonType.OK);
            alert.showAndWait();
            return false;
        }
    }

    /**
     * Checks that appointment is within business hours.
     * @param start the appointment start date and time
     * @param end the appointment end date and time
     * @return true if within business hours or false if not
     */
    public static boolean isInBusinessHours(LocalDateTime start, LocalDateTime end) {
        //8:00 a.m. to 10:00 p.m. EST
        LocalTime openTime = LocalTime.of(8,0);
        LocalTime closeTime = LocalTime.of(22, 0);

        ZonedDateTime startDateTime = start.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime startInUTC = startDateTime.withZoneSameInstant(ZoneId.of("UTC"));

        ZonedDateTime endDateTime = end.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime endInUTC = endDateTime.withZoneSameInstant(ZoneId.of("UTC"));

        //convert to UTC
        LocalDateTime openDateTime = LocalDateTime.of(startDateTime.toLocalDate(), openTime);
        ZonedDateTime zonedOpen = ZonedDateTime.of(openDateTime, ZoneId.of("America/New_York"));
        LocalDateTime closeDateTime = LocalDateTime.of(startDateTime.toLocalDate().plusDays(1), closeTime);
        ZonedDateTime zonedClose = ZonedDateTime.of(closeDateTime, ZoneId.of("America/New_York"));

        ZonedDateTime openInUTC = zonedOpen.withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime closeInUTC = zonedClose.withZoneSameInstant(ZoneId.of("UTC"));

        if ((startInUTC.isAfter(openInUTC) || startInUTC.isEqual(openInUTC)) &&
                (endInUTC.isBefore(closeInUTC) || endInUTC.isEqual(closeInUTC)))
            return true;
        else
            return false;
    }

    /**
     * Checks if an updated appointment overlaps another appointment for the same customer (not including the appointment being updated).
     * @param cId customer id
     * @param appointmentId appointment id
     * @param start the start date and time
     * @param end the end date and time
     * @return true if no overlap or false if there is an overlapping appointment
     */
    public static boolean checkCustomerAppointmentOverlap(int cId, int appointmentId, LocalDateTime start, LocalDateTime end) {
        ObservableList<Appointments> appointmentList = DBAppointments.getApptsByCustomer(cId);

        for (Appointments a : appointmentList) {
            if (a.getId() != (appointmentId)) {
                if (checkForOverlap(a, start, end))
                    return false;
            }
        }
        return true;
    }

    /**
     * Checks if a new appointment overlaps another appointment for the same customer
     * @param cId customer id
     * @param start the start date and time
     * @param end the end date and time
     * @return true if no overlap or false if there is an overlapping appointment
     */
    public static boolean checkCustomerAppointmentOverlap(int cId, LocalDateTime start, LocalDateTime end) {
        ObservableList<Appointments> appointmentList = DBAppointments.getApptsByCustomer(cId);

        for (Appointments a : appointmentList) {
            if (checkForOverlap(a, start, end))
                return false;
        }
        return true;
    }

    /**
     * Compares an potential start and end date and time with a pre-existing appointment to see if there is any overlap.
     * @param a the appointment object
     * @param start the appointment start date and time
     * @param end the appointment end date and time
     * @return true if there is an overlapping appointment or false if not
     */
    public static boolean checkForOverlap(Appointments a, LocalDateTime start, LocalDateTime end) {
        LocalDateTime oldStart = LocalDateTime.of(a.getStartDate(), a.getStartTime());
        LocalDateTime oldEnd = LocalDateTime.of(a.getEndDate(), a.getEndTime());
        //if overlap exists
        if (((oldStart.isAfter(start) || oldStart.isEqual(start)) && oldStart.isBefore(end)) ||
                (oldEnd.isAfter(start) && (oldEnd.isBefore(end) || oldEnd.isEqual(end))) ||
                ((oldStart.isBefore(start) || oldStart.isEqual(start)) && (oldEnd.isAfter(end) || oldEnd.isEqual(end))))
            return true;  //there is overlap
        return false; //no overlap
    }
}
