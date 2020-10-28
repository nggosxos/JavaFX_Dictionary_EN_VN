package api;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * https://developer.oxforddictionaries.com/documentation#!/Entries/get_entries_source_lang_word_id
 * Simple using of Oxford Dictionary API (modified to use easily)
 */
public class OxfordAPI {
    //My application's id and key - DON'T COPY
    private final String applicationId = "0d232aa5";
    private final String applicationKey = "b59d137ac263dc0c93337fefd363b65b";

    //url to access API
    private String url;

    //to language (default en-gb);
    private String language = "en-gb";

    /**
     * constructor to request endpoint language.
     *
     * @param language .
     */
    public OxfordAPI(String language) {
        this.language = language;
    }

    /**
     * This method performs work that uses to get definition of word.
     */
    public String Post(String word2Dict) throws Exception {

        String word_id = word2Dict.toLowerCase();
        /*
         * field is set 'definitions' to get definitions of word.
         * strictMatch is set 'false' to get the most familiar word.
         */
        this.url = "https://od-api.oxforddictionaries.com:443/api/v2/entries/" + this.language + "/" + word_id + "?" + "fields=definitions&strictMatch=false";

        //establish connection.
        URL url = new URL(this.url);
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        urlConnection.setRequestProperty("Accept", "application/json");
        urlConnection.setRequestProperty("app_id", applicationId);
        urlConnection.setRequestProperty("app_key", applicationKey);

        // read the output from the server.
        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }

        return stringBuilder.toString();
    }

    /**
     * this method performs work that dig into JSON output of API
     * @param response .
     * @return .
     * @throws ParseException .
     */
    public String processOutput(String response) throws ParseException {
        StringBuilder result = new StringBuilder();
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(response);
        JSONArray lexicalEntries = (JSONArray) ((JSONObject) (((JSONArray) ((JSONObject) obj).get("results")).get(0))).get("lexicalEntries");
        if (lexicalEntries != null) {
            for (Object lexicalEntry : lexicalEntries) {
                JSONArray entries = (JSONArray) ((JSONObject) lexicalEntry).get("entries");
                if (entries != null) {
                    result.append(((JSONObject) lexicalEntry).get("text").toString()).append("\n");
                    result.append(((JSONObject) ((JSONObject) lexicalEntry).get("lexicalCategory")).get("text").toString()).append("\n");
                    JSONArray senses = (JSONArray) ((JSONObject) entries.get(0)).get("senses");
                    if (senses != null) {
                        for (Object sens : senses) {
                            result.append(((JSONObject) sens).get("definitions")).append("\n");
                        }
                    }
                    JSONArray sub_senses = (JSONArray) ((JSONObject) ((JSONArray) (((JSONObject) entries.get(0)).get("senses"))).get(0)).get("subsenses");
                    if (sub_senses != null) {
                        for (Object sub_sens : sub_senses) {
                            result.append(((JSONObject) sub_sens).get("definitions")).append("\n");
                        }
                    }
                }
            }
        }
        return result.toString().replace("[", "").replace("]", "");
    }
}