package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeValues {

    /**
     * An observable list of the values "AM" and "PM" to fill combo boxes
     */
    public static final ObservableList<String> amPm = FXCollections.observableArrayList("AM", "PM");

    /**
     * Takes input values for time and converts to LocalTime object.
     * @param hour the input hour
     * @param min the input minutes
     * @param amPm the selected value "AM" or "PM"
     * @return the LocalTime object
     */
    public static LocalTime setTime(int hour, int min, String amPm)
    {
        if (amPm.equals("PM"))
            if (hour != 12) //for all other pm hours than 12:00
                hour += 12; //convert to military time
            //if midnight
        if(amPm.equals("AM") && hour == 12)
            hour = 0;
        LocalTime lTime = LocalTime.of(hour, min);
        return lTime;
    }
}
