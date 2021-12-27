package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Months {

    private static ObservableList<String> month = FXCollections.observableArrayList(
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
    );

    /**
     * Returns an observable list of months
     * @return An observable list of months
     */
    public static ObservableList<String> getMonths() {
        return month;
    }

}
