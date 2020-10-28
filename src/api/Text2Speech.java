package api;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Simple way to use Google's TTS for text to speech
 */
public class Text2Speech {
    //language.
    private String language;

    /**
     * constructor - set language text to speech.
     * @param language .
     */
    public Text2Speech(String language) {
        this.language = language;
    }

    /**
     * this method processing request to get InputStream from Google's TTS.
     * @param text2Speech .
     * @return .
     * @throws IOException .
     */
    public InputStream getText2Speech(String text2Speech) throws IOException {
        //url for connecting to Google's TTS.
        URL url = new URL("http://translate.google.com/translate_tts?ie=UTF-8&tl=" + language + "&client=tw-ob&q="
                + text2Speech.replace(" ", "%20"));
        //establish connection.
        URLConnection urlConnection = url.openConnection();
        urlConnection.addRequestProperty("User-Agent", "Mozilla/4.76");
        InputStream audioSrc = urlConnection.getInputStream();
        return new BufferedInputStream(audioSrc);
    }
}
