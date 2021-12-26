package SchedulingApplication;

import Database.DBQuery;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import Database.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main extends Application {

    /**
     * Displays the login page.
     * @param primaryStage The primary stage
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));

            primaryStage.setTitle("Login");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
         }
        catch (NullPointerException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot start because page does not exist.  Program will now close.");
            alert.showAndWait();
        }
    }

    /**
     * The main method. Creates initial data and launches the program.
     * @param args Command line arguments
     */
    public static void main(String[] args) {

        try {
            Connection connection = DBConnection.startConnection();
            DBQuery.setStatement(connection);   //create statement object
        } catch (SQLException e) {
            e.printStackTrace();
        }

        launch(args);
        try {
            DBConnection.closeConnection();
        }
        catch (Exception e)
        {
            //if it's already closed, do nothing
        }
    }
}

