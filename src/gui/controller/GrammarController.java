package gui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import service.UsefulFunction;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * controller of GrammarTab.fxml (Ngữ pháp).
 */
public class GrammarController {

    //fx::id.
    @FXML private ListView<String> listView;
    @FXML private TextArea textArea;

    private final ObservableList<String> grammarList = FXCollections.observableArrayList();
    private final ObservableList<String> filePathList = FXCollections.observableArrayList();

    /**
     * initialize.
     * @throws FileNotFoundException .
     */
    public void initialize() throws FileNotFoundException {
        //load grammar list and field path to grammar.
        Scanner scanner = new Scanner(new File(UsefulFunction.projectPath + "/src/db/grammar/grammarList.txt"));
        String line;
        while (scanner.hasNext()) {
            line = scanner.nextLine();
            String[] field = line.split(",");
            grammarList.add(field[1]);
            filePathList.add(field[2]);
        }
        listView.setItems(grammarList);
    }

    /**
     * mouse click on listView to view.
     * @throws FileNotFoundException .
     */
    public void onMouseClicked() throws FileNotFoundException {
        grammarView();
    }

    /**
     * UP or DOWN on listView to view grammar.
     * @param event .
     * @throws FileNotFoundException .
     */
    public void onKeyRelease(KeyEvent event) throws FileNotFoundException {
        if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
            grammarView();
        }
    }

    /**
     * load grammar's content to textArea.
     * @throws FileNotFoundException .
     */
    private void grammarView() throws FileNotFoundException {
        textArea.clear();
        final int selectedItem = listView.getSelectionModel().getSelectedIndex();
        Scanner scanner = new Scanner(new File(UsefulFunction.projectPath + "/src/db/grammar/" + filePathList.get(selectedItem)));
        while (scanner.hasNext()) {
            textArea.appendText(scanner.nextLine() + "\n");
        }
        textArea.positionCaret(0);
    }
}
