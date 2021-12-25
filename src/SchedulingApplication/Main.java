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
     *
     * @param primaryStage The primary stage
     * @throws IOException Throws exception if unable to load next page
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));

            primaryStage.setTitle("Login");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
         }
        catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot start because page does not exist.  Program will now close.");
        }
    }


    /**
     * The main method. Creates initial data and launches the program.
     * @param args  Command line arguments
     */
    public static void main(String[] args) throws SQLException {

        //Locale.setDefault(new Locale("fr", "Quebec"));
        //TimeZone.setDefault(TimeZone.getTimeZone("EST"));
        Connection connection = DBConnection.startConnection();
        DBQuery.setStatement(connection);   //create statement object

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

