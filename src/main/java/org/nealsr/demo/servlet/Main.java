package org.nealsr.demo.servlet;

import java.io.IOException;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nealsr.demo.util.JsonUtil;
import org.nealsr.demo.util.WikipediaUtil;

/**
 * Simple HttpServlet for searching wikipedia by title.
 */
public class Main extends HttpServlet {

    private static String TITLE_PARAM_KEY = "title";
    private static String RAW_PARAM_KEY = "raw";
    private static String DEFAULT_TITLE = "Cincinnati";
    private static String SEARCH_RESULTS = "Your search for: %s contained %d matches.";

    /**
     * Public facing endpoint for searching wikipedia by title and optionally return raw data.
     * @param request the HttpServletRequest.
     * @param response the HttpServletResponse.
     * @throws IOException if an error occurs writing to the response output stream.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String title = getTitle(request);
        System.out.println("TITLE: " + title);

        final String json = new WikipediaUtil().getRawJsonFromPage(title);

        final String htmlText = JsonUtil.getHtmlFromJson(json);

        final int matches = StringUtils.countMatches(htmlText, title);

        System.out.println("MATCHES: " + matches);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getOutputStream().println(String.format(SEARCH_RESULTS, title, matches));
        if (showRaw(request)) {
            response.getOutputStream().println("\n\nRAW DATA:");
            response.getOutputStream().println(json);
        }
    }

    /**
     * Gets the title query parameter from the request.
     * @param request the HttpServletRequest.
     * @return the title if specified in the query params, or "Cincinnati" by default.
     */
    private String getTitle(HttpServletRequest request) {
        if (request.getParameterMap() != null && request.getParameterMap().containsKey(TITLE_PARAM_KEY)) {
            return request.getParameterMap().get(TITLE_PARAM_KEY)[0];
        } else {
            return DEFAULT_TITLE;
        }
    }

    /**
     * Checks whether the "raw" query parameter is present.
     * @param request the HttpServletRequest.
     * @return true if present, false otherwise.
     */
    private boolean showRaw(HttpServletRequest request) {
        return (request.getParameterMap() != null && request.getParameterMap().containsKey(RAW_PARAM_KEY));
    }
}
