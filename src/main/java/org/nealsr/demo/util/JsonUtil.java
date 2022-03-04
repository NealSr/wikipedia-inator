package org.nealsr.demo.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Simple Utility to parse text from Wikipedia's parse API.
 */
public class JsonUtil {

    /**
     * Parses the Html Text field from a parse call to wikipedia's API.
     * @param json the raw JSON response from wikipedia.
     * @return the String of HTML text in the json, or null if an error occurs parsing.
     */
    public static String getHtmlFromJson(String json) {
        final JSONParser jsonParser = new JSONParser();
        String html;
        try {
            JSONObject wikiJson = (JSONObject) jsonParser.parse(json);
            JSONObject parsedObject = (JSONObject) wikiJson.get("parse");
            JSONObject textObject = (JSONObject) parsedObject.get("text");
            html = (String) textObject.get("*");
        } catch (ParseException pe) {
            return null;
        } catch (NullPointerException npe) {
            return null;
        }
        return html;
    }

}
