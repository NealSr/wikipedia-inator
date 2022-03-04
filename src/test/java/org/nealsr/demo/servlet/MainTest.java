package org.nealsr.demo.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mockito;

public class MainTest {

    @Test
    public void doGet_defaults_to_Cincinnati() throws IOException {
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = Mockito.mock(HttpServletResponse.class);
        ServletOutputStream mockOutputStream = Mockito.mock(ServletOutputStream.class);
        Mockito.when(mockResponse.getOutputStream()).thenReturn(mockOutputStream);

        new Main().doGet(mockRequest, mockResponse);

        Mockito.verify(mockOutputStream, Mockito.times(1)).println("Your search for: Cincinnati contained 173 matches.");
    }

    @Test
    public void doGet_reads_title_from_request() throws IOException {
        Map<String, String[]> requestParams = new HashMap<>();
        requestParams.put("title", new String[] { "Test" });
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(mockRequest.getParameterMap()).thenReturn(requestParams);
        HttpServletResponse mockResponse = Mockito.mock(HttpServletResponse.class);
        ServletOutputStream mockOutputStream = Mockito.mock(ServletOutputStream.class);
        Mockito.when(mockResponse.getOutputStream()).thenReturn(mockOutputStream);

        new Main().doGet(mockRequest, mockResponse);

        Mockito.verify(mockOutputStream, Mockito.times(1)).println("Your search for: Test contained 7 matches.");
    }

    @Test
    public void doGet_reads_only_first_title_param() throws IOException {
        Map<String, String[]> requestParams = new HashMap<>();
        requestParams.put("title", new String[] { "Test", "Test2", "Ignore" });
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(mockRequest.getParameterMap()).thenReturn(requestParams);
        HttpServletResponse mockResponse = Mockito.mock(HttpServletResponse.class);
        ServletOutputStream mockOutputStream = Mockito.mock(ServletOutputStream.class);
        Mockito.when(mockResponse.getOutputStream()).thenReturn(mockOutputStream);

        new Main().doGet(mockRequest, mockResponse);

        Mockito.verify(mockOutputStream, Mockito.times(1)).println("Your search for: Test contained 7 matches.");
    }

    @Test
    public void doGet_reads_raw_param_if_provided() throws IOException {
        Map<String, String[]> requestParams = new HashMap<>();
        requestParams.put("title", new String[] { "Test" });
        requestParams.put("raw", new String[] { "true" });
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(mockRequest.getParameterMap()).thenReturn(requestParams);
        HttpServletResponse mockResponse = Mockito.mock(HttpServletResponse.class);
        ServletOutputStream mockOutputStream = Mockito.mock(ServletOutputStream.class);
        Mockito.when(mockResponse.getOutputStream()).thenReturn(mockOutputStream);

        new Main().doGet(mockRequest, mockResponse);

        Mockito.verify(mockOutputStream, Mockito.times(3)).println(Mockito.anyString());
    }
}
