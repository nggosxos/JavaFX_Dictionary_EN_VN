package gui.controller;

import api.Text2Speech;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import service.DictionaryManagement;
import service.UsefulFunction;

import java.io.*;
import java.util.Scanner;

import static gui.controller.SearchController.historyList;


public class HistoryController {

    //fx::id.
    @FXML private Button delButton;
    @FXML private Label wordLabel;
    @FXML private ListView<String> wordList;
    @FXML private TextArea wordExplain;
    @FXML private Button pronounButton;

    private final Text2Speech text2Speech = new Text2Speech("en");
    //observableList stores looked-up words (～￣▽￣)～.
    public static ObservableList<String> observableHistoryList = FXCollections.observableArrayList();
    private String currentWord;

    /**
     * initialize.
     */
    public void initialize() {
        wordList.setItems(observableHistoryList);
    }

    /**
     * mouse click on pronounButton (Phát âm) to play pronunciation.
     * @throws IOException .
     * @throws JavaLayerException .
     */
    public void onPronounButton() throws IOException, JavaLayerException {
        InputStream inputStream;
        inputStream = text2Speech.getText2Speech(currentWord);
        new Player(inputStream).play();
    }

    /**
     * mouse click on delButton (Xóa hết) to clear history.
     */
    public void onDeleteButton() {
        historyList.clear();
        observableHistoryList.clear();
    }

    /**
     * mouse click on history list to view again =)).
     */
    public void listOnMouseClick() {
        onListAction();
    }

    /**
     * UP or DOWN on history list to view again :v.
     * @param event .
     */
    public void listOnKeyRelease(KeyEvent event) {
        if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
            onListAction();
        }
    }

    /**
     * on list's action (；′⌒`).
     */
    private void onListAction() {
        currentWord = wordList.getSelectionModel().getSelectedItem();
        onSearch(currentWord);
    }

    /**
     * search.
     * @param word2Search .
     */
    private void onSearch(String word2Search) {
        wordLabel.setText(word2Search);
        wordExplain.clear();
        String result = DictionaryManagement.dictionaryLookup(word2Search.trim()).replaceAll("[|#]", "\n");
        if (result.equals("No data")) {
            wordExplain.setPromptText("Word is unavailable");
        } else {
            wordExplain.appendText(result);
        }
        wordExplain.positionCaret(0);
    }

    /**
     * this method performs loading history from file.
     * I put it on Main to start up with application.
     * @throws FileNotFoundException .
     */
    public static void loadHistoryFromFile() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(UsefulFunction.projectPath + "/src/db/history/enVnHistory.txt"));
        String line;
        while (scanner.hasNext()) {
            line = scanner.nextLine();
            if (!historyList.contains(line)) {
                historyList.add(line);
            }
        }
    }

    /**
     * this method performs saving history to file.
     * I put it on Controller when you choose FILE >> CLOSE.
     * @throws FileNotFoundException .
     */
    public static void saveHistoryToFile() throws FileNotFoundException {
        PrintWriter printer = new PrintWriter(new File(UsefulFunction.projectPath + "/src/db/history/enVnHistory.txt"));
        for (String s : historyList) {
            printer.print(s + "\n");
        }
        printer.close();
    }
}
