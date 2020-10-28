package gui.controller;

import api.Text2Speech;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import service.DictionaryManagement;
import service.Word;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SearchController {

    //fx::id.
    @FXML private TextField searchWord;
    @FXML private Button searchButton;
    @FXML private Label wordLabel;
    @FXML private ListView<String> wordList;
    @FXML private TextArea wordExplain;
    @FXML private Button pronounButton;

    //historyList stores looked-up words from new search and saved file.
    public static ArrayList<String> historyList = new ArrayList<>();
    private final Text2Speech text2Speech = new Text2Speech("en");
    //observableList stores historyList.
    private final ObservableList<String> observableList = FXCollections.observableArrayList();
    private String currentWord;

    /**
     * initialize.
     */
    public void initialize() {
        searchWord.textProperty().addListener(((observable, oldValue, newValue) -> {
            observableList.clear();
            if (!newValue.equals("")) {
                ArrayList<Word> arrayList = DictionaryManagement.dictionarySearcher(newValue.trim(), 10);
                for (Word word : arrayList) {
                    observableList.add(word.getWord_target());
                }
            }
        }));
        wordList.setItems(observableList);
    }

    /**
     * mouse click on pronounButton (Phát âm) to play pronunciation.
     * @throws IOException .
     * @throws JavaLayerException .
     */
    public void pronounButtonOnMouseClick() throws IOException, JavaLayerException {
        InputStream inputStream;
        inputStream = text2Speech.getText2Speech(currentWord);
        new Player(inputStream).play();
    }

    /**
     * press Enter on textField to search.
     * @param event .
     */
    public void searchOnEnterPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            onSearchAction();
        }
    }

    /**
     * mouse click on searchButton to search.
     */
    public void searchButtonOnMouseClick() {
        onSearchAction();
    }

    /**
     * mouse click on listView to search.
     */
    public void listOnMouseClick() {
        onListAction();
    }

    /**
     * UP or DOWN on listView to search.
     * @param event .
     */
    public void listOnKeyRelease(KeyEvent event) {
        if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
            onListAction();
        }
    }

    /**
     * get word to search.
     */
    private void onSearchAction() {
        currentWord = searchWord.getText().trim();
        addHistory(currentWord);
        onSearch(currentWord);
    }

    /**
     * search.
     * @param word2Search .
     */
    private void onSearch(String word2Search) {

        wordExplain.clear();
        String result = DictionaryManagement.dictionaryLookup(word2Search).replaceAll("[|#]", "\n");
        if (result.equals("No data")) {
            if (!wordList.getItems().isEmpty()) {
                wordList.getSelectionModel().select(0);
                word2Search = wordList.getSelectionModel().getSelectedItem();
                wordExplain.appendText(DictionaryManagement.dictionaryLookup(word2Search).replaceAll("[|#]", "\n"));
            } else {
                wordExplain.setPromptText("Word is unavailable");
            }
        } else {
            wordExplain.appendText(result);
        }
        wordLabel.setText(word2Search);
        wordExplain.positionCaret(0);
    }

    /**
     * on list search.
     */
    private void onListAction() {
        currentWord = wordList.getSelectionModel().getSelectedItem();
        onSearch(currentWord);
        addHistory(currentWord);
    }

    /**
     * add history of looked-up words.
     * @param historyWord .
     */
    private void addHistory(String historyWord) {
        if(!historyList.contains(historyWord) && !historyWord.equals("")) {
            historyList.add(historyWord);
        }
    }
}