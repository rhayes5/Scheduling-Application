package model;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneChange {
    /**
     * Changes to the next scene.
     * @param event the event that preceded the scene change
     * @param nextPage the location of the next page
     * @param title the title of the next page
     * @throws IOException if unable to load next page
     */
    public static void sceneChange(ActionEvent event, String nextPage, String title) throws IOException {
        Parent root;
        Stage stage;

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        root = FXMLLoader.load(SceneChange.class.getResource(nextPage));
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }
}