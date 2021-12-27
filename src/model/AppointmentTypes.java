package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AppointmentTypes {

    private static ObservableList<String> type = FXCollections.observableArrayList(
            "Introductory",
            "Planning Session",
            "Goal Setting",
            "Status Update",
            "De-Briefing"
    );

    /**
     * Returns an observable list of appointment types
     * @return An observable list of appointment types
     */
    public static ObservableList<String> getTypes() {
        return type;
    }
}
