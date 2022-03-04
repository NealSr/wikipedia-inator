package org.nealsr.demo.util;

import org.junit.Assert;
import org.junit.Test;

public class JsonUtilTest {

    @Test
    public void test_getHtmlFromJson_successfully_parses() {
        String expected = "RAW_HTML_HERE";
        String actual = JsonUtil.getHtmlFromJson("{\"parse\":{\"title\":\"Test\",\"pageid\":11089416,"
                + "\"text\":{\"*\":\"RAW_HTML_HERE\"}}}");

        Assert.assertEquals("The text.* field should be returned but wasn't.", expected, actual);
    }

    @Test
    public void test_getHtmlFromJson_null_if_missing_text() {
        String actual = JsonUtil.getHtmlFromJson("{\"parse\":{\"title\":\"Test\",\"pageid\":11089416,"
                + "\"other_key\":{\"*\":\"RAW_HTML_HERE\"}}}");
        Assert.assertNull("If text is missing it should just return null but didn't.", actual);
    }

    @Test
    public void test_getHtmlFromJson_null_if_invalid_json() {
        String actual = JsonUtil.getHtmlFromJson("<this isn't even JSON></d>");
        Assert.assertNull("Invalid JSON should not cause errors and should return null but didn't.", actual);
    }
}
