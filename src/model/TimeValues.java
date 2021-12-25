package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalTime;

public class TimeValues {

    public static final ObservableList<String> amPm = FXCollections.observableArrayList("AM", "PM");

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
