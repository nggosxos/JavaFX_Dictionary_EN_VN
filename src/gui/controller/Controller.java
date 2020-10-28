package gui.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.stage.FileChooser;
import service.DictionaryManagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;

import static gui.controller.HistoryController.observableHistoryList;
import static gui.controller.SearchController.historyList;

/**
 * controller of Main.fxml which contains tabs of feature .
 */
public class Controller {
    @FXML private SearchController searchController;
    @FXML private HistoryController historyController;
    @FXML private TextTransController textTransController;
    @FXML private GrammarController grammarController;
    @FXML private DictEditController dictEditController;
    @FXML private Tab historyTab;

    /**
     * this method show a Warning dialog when user is going to quit application .
     * File >> Quit || Ctrl + Q but not tick X =)).
     * @throws FileNotFoundException .
     */
    public void quitApp() throws FileNotFoundException {
        Alert quitAlert = new Alert(Alert.AlertType.WARNING);
        quitAlert.setHeaderText("Warning! You are going to quit the application");
        quitAlert.setContentText("Click 'Quit' to quit =((");

        ButtonType buttonTypeQuit = new ButtonType("Quit", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        quitAlert.getButtonTypes().setAll(buttonTypeQuit, buttonTypeCancel);
        Optional<ButtonType> result = quitAlert.showAndWait();

        if (result.isPresent() && result.get() == buttonTypeQuit) {
            //save history to file
            HistoryController.saveHistoryToFile();
            Platform.exit();
            System.exit(0);
        }
    }

    /**
     * this method show a information dialog of TEAM27
     * Help >> About.
     */
    public void aboutApp() {
        Alert aboutAlert = new Alert(Alert.AlertType.INFORMATION);
        aboutAlert.setHeaderText("Java Dictionary by TEAM27 - Lương Hải Long - Nguyễn Như Ngọc");
        aboutAlert.setContentText("Click 'OK' to continue");
        aboutAlert.setTitle("About");

        ButtonType buttonTypeOK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        aboutAlert.getButtonTypes().setAll(buttonTypeOK);
        aboutAlert.showAndWait();
    }

    /**
     * this method performs selection of new dictionary's database.
     * File >> Set Dictionary.
     * @throws Exception .
     */
    public void selectDictionary() throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a new your dictionary");
        FileChooser.ExtensionFilter fileFilter = new FileChooser.ExtensionFilter("Dictionary", "*.txt", "*.dd");
        fileChooser.getExtensionFilters().add(fileFilter);
        File file = fileChooser.showOpenDialog(null);

        if (file != null){
            DictionaryManagement.setInputfile(file.getAbsolutePath());
            DictionaryManagement.dictionaryImportFromFile();
        }

    }

    /**
     * this method performs saving your modified dictionary's database.
     * File >> Save Dictionary.
     * @throws Exception .
     */
    public void saveDictionary() throws Exception {
        DictionaryManagement.dictionaryExportToFile();
    }

    /**
     * this method perform loading history of looked-up words.
     * only when click History Tab (Từ đã tra).
     */
    public void loadHistory() {
        for (int i = historyList.size() - 1; i >= 0; i--) {
            String temp = historyList.get(i);
            if (!observableHistoryList.contains(temp)) {
                observableHistoryList.add(temp);
            }
        }
    }
}
