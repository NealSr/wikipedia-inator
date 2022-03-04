package org.nealsr.demo.util;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WikipediaUtil {

    private final OkHttpClient client = new OkHttpClient();
    private final String WIKIPEDIA_URL_TEMPLATE
        = "https://en.wikipedia.org/w/api.php?action=parse&section=0&prop=text&format=json&page=%s";
    public String getJsonFromPage(final String title) throws IOException {
        Request request = new Request.Builder()
            .url(String.format(WIKIPEDIA_URL_TEMPLATE, title))
            .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}