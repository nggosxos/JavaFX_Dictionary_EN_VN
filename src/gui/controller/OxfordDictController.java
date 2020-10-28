package gui.controller;

import api.OxfordAPI;
import api.Text2Speech;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.IOException;
import java.io.InputStream;

public class OxfordDictController {

    //fx::id
    @FXML private TextField searchWord;
    @FXML private Button searchButton;
    @FXML private Label wordLabel;
    @FXML private TextArea wordExplain;
    @FXML private Button pronounButton;
    @FXML private ListView<String> wordList;

    private final OxfordAPI enGbDict = new OxfordAPI("en");
    private final Text2Speech text2Speech = new Text2Speech("en");
    //observableList to save only looked-up words not word's explain.
    //however I can create a new database for looked-up word from Oxford to reuse but I still do not know how (┬┬﹏┬┬).
    private final ObservableList<String> observableList = FXCollections.observableArrayList();
    private String currentWord;

    /**
     * initialize.
     */
    public void initialize() {
        wordList.setItems(observableList);
    }

    /**
     * mouse click on pronounButton to play pronunciation.
     * @throws IOException .
     * @throws JavaLayerException .
     */
    public void onPronounButtonAction() throws IOException, JavaLayerException {
        InputStream inputStream = text2Speech.getText2Speech(currentWord);
        new Player(inputStream).play();
    }

    /**
     * mouse click on searchButton to search with Oxford API.
     * @throws Exception .
     */
    public void onSearchButtonAction() throws Exception {
        currentWord = searchWord.getText();
        onSearch(currentWord);
    }

    /**
     * press Enter on textField to search with Oxford API.
     * @param event .
     * @throws Exception .
     */
    public void searchOnEnterPress(KeyEvent event) throws Exception {
        if (event.getCode() == KeyCode.ENTER) {
            currentWord = searchWord.getText();
            onSearch(currentWord);
        }
    }

    /**
     * mouse click on listView to search with Oxford API.
     * @throws Exception .
     */
    public void listOnMouseClick() throws Exception {
        currentWord = wordList.getSelectionModel().getSelectedItem();
        onSearch(currentWord);
    }

    /**
     * UP or DOWN on listView to search with Oxford API.
     * @param event .
     * @throws Exception .
     */
    public void listOnKeyRelease(KeyEvent event) throws Exception {
        if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
            currentWord = wordList.getSelectionModel().getSelectedItem();
            onSearch(currentWord);
        }
    }

    /**
     * this method performs search action with Oxford API.
     * @param word2Search .
     * @throws Exception .
     */
    private void onSearch(String word2Search) throws Exception {
        word2Search = word2Search.trim();
        if (word2Search.equals("")) {
            wordExplain.clear();
        }
        else {
            if (!observableList.contains(word2Search)) {
                observableList.add(word2Search);
            }
            wordLabel.setText(word2Search);
            wordExplain.clear();
            String response = enGbDict.Post(word2Search);
            String result = enGbDict.processOutput(response);
            wordExplain.appendText(result);
            wordExplain.positionCaret(0);
        }
    }
}
