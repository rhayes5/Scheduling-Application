package model;

import DBAccess.DBAppointments;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.time.*;

public class CheckValues {
    public static boolean isInteger(String s)
    {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public static boolean isAllowableTimeValue(String h, String m)
    {
        int hour = Integer.parseInt(h);
        int min = Integer.parseInt(m);
        if (min < 60 && min >= 0)
        {
            if (hour <= 12 && hour >= 1)
                return true;
        }
        return false;
    }

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

    public static boolean isInBusinessHours(LocalDateTime start, LocalDateTime end) {
        //8:00 a.m. to 10:00 p.m. EST
        LocalTime openTime = LocalTime.of(8,00);
        LocalTime closeTime = LocalTime.of(22, 00);

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

    public static boolean checkCustomerAppointmentOverlap(int cId, LocalDateTime start, LocalDateTime end) {
        ObservableList<Appointments> appointmentList = DBAppointments.getApptsByCustomer(cId);

        for (Appointments a : appointmentList) {
            if (checkForOverlap(a, start, end))
                return false;
        }
        return true;
    }

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
