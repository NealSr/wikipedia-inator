package org.nealsr.demo.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nealsr.demo.util.WikipediaUtil;

public class Main extends HttpServlet {

    private static String TITLE_PARAM_KEY = "title";
    private static String DEFAULT_TITLE = "Cincinnati";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int status = HttpServletResponse.SC_OK;
        String json = new WikipediaUtil().getJsonFromPage(getTitle(request));

        response.setStatus(status);
        response.getOutputStream().println(json);
    }

    private String getTitle(HttpServletRequest request) {
        if (request.getParameterMap() != null && request.getParameterMap().containsKey(TITLE_PARAM_KEY)) {
            return request.getParameterMap().get(TITLE_PARAM_KEY)[0];
        } else {
            return DEFAULT_TITLE;
        }
    }
}
