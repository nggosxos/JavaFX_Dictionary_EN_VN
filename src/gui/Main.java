package gui;

import gui.controller.HistoryController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import service.DictionaryManagement;

import java.io.FileNotFoundException;

/**
 * Main extends Application
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Main.fxml"));
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("image/icon.png")));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Java Dictionary by TEAM27");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            try {
                HistoryController.saveHistoryToFile();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) throws Exception {
        DictionaryManagement.dictionaryImportFromFile();
        HistoryController.loadHistoryFromFile();
        launch(args);
    }
}
