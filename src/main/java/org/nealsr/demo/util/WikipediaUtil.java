package org.nealsr.demo.util;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Simple utility to make a GET request against wikipedia's parse API.
 */
public class WikipediaUtil {

    private final OkHttpClient client = new OkHttpClient();
    private final String WIKIPEDIA_URL_TEMPLATE
        = "https://en.wikipedia.org/w/api.php?action=parse&section=0&prop=text&format=json&page=%s";

    /**
     * Calls wikipedia's parse API and retrieves the raw JSON response by page.
     * @param title the wikipedia page to retrieve.
     * @return the raw JSON from wikipedia.
     * @throws IOException if an error occurs calling wikipedia.
     */
    public String getRawJsonFromPage(final String title) throws IOException {
        Request request = new Request.Builder()
            .url(String.format(WIKIPEDIA_URL_TEMPLATE, title))
            .get()
            .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}