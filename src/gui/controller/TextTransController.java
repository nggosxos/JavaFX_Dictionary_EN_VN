package gui.controller;

import api.TranslateAPI;
import api.Text2Speech;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;


public class TextTransController {
    //fx:id.
    @FXML private TextArea enText2Trans;
    @FXML private TextArea vnText2Trans;
    @FXML private TextArea enVnText;
    @FXML private TextArea vnEnText;

    private final Text2Speech enText2Speech = new Text2Speech("en");
    private final Text2Speech vnText2Speech = new Text2Speech("vi");
    private InputStream enSpeech;
    private InputStream vnSpeech;
    private Player vnPlayer;
    private Player enPlayer;
    private String input;
    private String result;

    /**
     * just a initialize =)).
     */
    public void onTabEn2VN() {
        input = enText2Trans.getText().trim();
        result = enVnText.getText().trim();
    }
    
    /**
     * just a initialize =)).
     */
    public void onTabVN2En() {
        input = vnText2Trans.getText().trim();
        result = vnEnText.getText().trim();
    }
    /**
     * press Enter to translate.
     * after translation press F1 or F2 to play speech.
     * @param event .
     * @throws IOException .
     * @throws ParseException .
     * @throws JavaLayerException .
     */
    public void onKeyPressEn2Vn(KeyEvent event) throws IOException, ParseException, JavaLayerException {
        if (event.getCode() == KeyCode.ENTER) {
            TranslateAPI textTranslate = new TranslateAPI("en", "vi");
            input = enText2Trans.getText().trim();
            String response = textTranslate.Post(input);
            result = (String) ((JSONObject) ((JSONArray) ((JSONObject) ((JSONArray) ((new JSONParser()).parse(response))).get(0)).get("translations")).get(0)).get("text");
            enVnText.clear();
            enVnText.appendText(result);
            enVnText.positionCaret(0);
        } else if (event.getCode() == KeyCode.F2) {
            enSpeech = enText2Speech.getText2Speech(input);
            enPlayer = new Player(enSpeech);
            enPlayer.play();
        } else if (event.getCode() == KeyCode.F3) {
            vnSpeech = vnText2Speech.getText2Speech(result);
            vnPlayer = new Player(vnSpeech);
            vnPlayer.play();
        }
    }

    /**
     * press Enter to translate.
     * after translation press F1 or F2 to play speech.
     * @param event .
     * @throws IOException .
     * @throws ParseException .
     * @throws JavaLayerException .
     */
    public void onKeyPressVn2En(KeyEvent event) throws IOException, ParseException, JavaLayerException {
        if (event.getCode() == KeyCode.ENTER) {
            TranslateAPI textTranslate = new TranslateAPI("vi", "en");
            input = vnText2Trans.getText().trim();
            String response = textTranslate.Post(input);
            result = (String) ((JSONObject) ((JSONArray) ((JSONObject) ((JSONArray) ((new JSONParser()).parse(response))).get(0)).get("translations")).get(0)).get("text");
            vnEnText.clear();
            vnEnText.appendText(result);
            vnEnText.positionCaret(0);
        } else if (event.getCode() == KeyCode.F2) {
            enSpeech = enText2Speech.getText2Speech(result);
            enPlayer = new Player(enSpeech);
            enPlayer.play();
        } else if (event.getCode() == KeyCode.F3) {
            vnSpeech = vnText2Speech.getText2Speech(input);
            vnPlayer = new Player(vnSpeech);
            vnPlayer.play();
        }
    }
}
