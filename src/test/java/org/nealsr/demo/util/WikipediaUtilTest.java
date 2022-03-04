package org.nealsr.demo.util;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class WikipediaUtilTest {

    @Test
    public void getRawJsonFromPage_returns_results() throws IOException {
        String rawJson = new WikipediaUtil().getRawJsonFromPage("Test");
        Assert.assertTrue("Normal request should return data but didn't", rawJson.length() > 0);
    }

    @Test
    public void getRawJsonFromPage_no_title_returns_null() throws IOException {
        Assert.assertNull("null or empty title should just return null.", new WikipediaUtil().getRawJsonFromPage(""));
        Assert.assertNull("null or empty title should just return null.", new WikipediaUtil().getRawJsonFromPage(null));
    }
}
