package api;

import java.io.*;
import java.util.Iterator;
import com.google.gson.*;
import com.squareup.okhttp.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;

/**
 * https://github.com/MicrosoftTranslator/Text-Translation-API-V3-Java/blob/master/Translate/src/main/java/Translate.java
 *
 * Simple using of Microsoft Translator API
 */
public class TranslateAPI {

    //My subscription key - DON'T COPY
    private final String subscriptionKey = "236e4563a3d94be99bd94af403ace2b4";
    private final String endpoint = "https://api.cognitive.microsofttranslator.com";
    //URL to access API
    private String url;

    /**
     * constructor to create translation from one to one an other.
     * @param fromLang .
     * @param toLang .
     */
    public TranslateAPI(String fromLang, String toLang) {
        this.url = endpoint + "/translate?api-version=3.0&from=" + fromLang + "&to=" + toLang;
    }

    // Instantiates the OkHttpClient.
    private OkHttpClient client = new OkHttpClient();

    /**
     * This method performs a POST request.
     */
    public String Post(String text2Translate) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType
                , "[{\n\t\"Text\": \"" + text2Translate + "\"\n}]");
        Request request = new Request.Builder()
                .url(url).post(body)
                .addHeader("Ocp-Apim-Subscription-Key", subscriptionKey)
                .addHeader("Content-type", "application/json").build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}