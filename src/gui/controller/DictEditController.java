package gui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import service.DictionaryManagement;
import service.Word;

import java.util.ArrayList;
import java.util.Optional;

/**
 * controller of EditWord.fxml which modify loaded dictionary (Thay đổi).
 */
public class DictEditController {

    //fx::id
    @FXML private TextField searchWord;
    @FXML private Button searchButton;
    @FXML private ListView<String> wordList;
    @FXML private TextArea wordExplain;
    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML private Button editButton;

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
        searchWord.textProperty().addListener((observable, oldValue, newValue) -> {
            if (observable.getValue().trim().equals("")) {
                onAvailable("init");
            }
        });
    }

    /**
     * press Enter to search from textField.
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
     */
    public void listOnKeyRelease(KeyEvent event) {
        if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
            onListAction();
        }
    }

    /** name tells you everything. */
    public void onAddButton() {
        //create new word and add to dictionary's data
        Word newWord = new Word(currentWord, wordExplain.getText().replace("\n", " |"));
        DictionaryManagement.insertWord(newWord);

        //making information alert.
        Alert addAlert = new Alert(Alert.AlertType.INFORMATION);
        addAlert.setHeaderText("Add word successfully");
        ButtonType buttonTypeOK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        addAlert.getButtonTypes().setAll(buttonTypeOK);
        addAlert.showAndWait();
        onAvailable("true");
    }

    /** name tells you everything. */
    public void onDeleteButton() {
        //making warning alert.
        Alert deleteAlert = new Alert(Alert.AlertType.WARNING);
        deleteAlert.setHeaderText("Warning! You are going to delete this word");
        ButtonType buttonTypeOK = new ButtonType("Continue anyway", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        deleteAlert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);
        Optional<ButtonType> result = deleteAlert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeOK) {
            //delete word (•_•).
            DictionaryManagement.remove(currentWord);
            wordExplain.clear();
            wordExplain.setPromptText("Word is unavailable");
            onAvailable("false");
        }
    }

    /** name tells you everything. */
    public void onEditButton() {
        //making warning alert.
        Alert editAlert = new Alert(Alert.AlertType.WARNING);
        editAlert.setHeaderText("Warning! Word's explain is modified. Click 'Save' to save");
        ButtonType buttonTypeSave = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        editAlert.getButtonTypes().setAll(buttonTypeSave, buttonTypeCancel);
        Optional<ButtonType> result = editAlert.showAndWait();

        if (result.isPresent() && result.get() == buttonTypeSave) {
            editAlert.setHeaderText("Word is modified successfully");
            ButtonType buttonTypeOK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            editAlert.getButtonTypes().setAll(buttonTypeOK);
            editAlert.showAndWait();

            //edit word's explain.
            DictionaryManagement.editWord(currentWord, wordExplain.getText().replace("\n", " |"));
            onAvailable("true");
        }
    }

    /**
     * this method performs getting word to search.
     */
    private void onSearchAction() {
        currentWord = searchWord.getText().trim();
        onSearch(currentWord);
    }

    /**
     * this method performs search's action and show result.
     * @param word2Search .
     */
    private void onSearch(String word2Search) {
        wordExplain.clear();
        String result = DictionaryManagement.dictionaryLookup(word2Search).replaceAll("[|#]", "\n");
        if (result.equals("No data")) {
            editButton.setDisable(true);
            deleteButton.setDisable(true);
            addButton.setDisable(false);
            wordExplain.setPromptText("Word is unavailable");
        } else {
            editButton.setDisable(false);
            deleteButton.setDisable(false);
            addButton.setDisable(true);
            wordExplain.appendText(result);
        }

        wordExplain.positionCaret(0);
    }

    /**
     * this method show listView's actions.
     */
    private void onListAction() {
        currentWord = wordList.getSelectionModel().getSelectedItem();
        onSearch(currentWord);
    }

    /**
     * this method sets addButton(Thêm), editButton(Sửa), deleteButton(Xóa) available or unavailable.
     * 'init'; 'false'; 'true'.
     * @param code .
     */
    private void onAvailable(String code) {
        switch (code) {
            case "true":
                editButton.setDisable(false);
                deleteButton.setDisable(false);
                addButton.setDisable(true);
                break;
            case "false":
                editButton.setDisable(true);
                deleteButton.setDisable(true);
                addButton.setDisable(false);
                break;
            case "inti":
                editButton.setDisable(true);
                deleteButton.setDisable(true);
                addButton.setDisable(true);
                break;
        }
    }
}
