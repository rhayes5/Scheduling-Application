package model;

import Database.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;

public class ExceptionHandlers {

    public static void nextPageError(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to load next page.  Returning to main menu.", ButtonType.OK);
        alert.showAndWait();
        try {
            SceneChange.sceneChange(event, "/view/MainMenu.fxml", "Main Menu");
        }
        catch (IOException e) {
            e.printStackTrace();
            Alert fatalErrorAlert = new Alert(Alert.AlertType.ERROR, "Cannot return to main menu.  Application will now close.",ButtonType.OK);
            fatalErrorAlert.showAndWait();
            DBConnection.closeConnection();
            System.exit(-1);
        }
    }
}
