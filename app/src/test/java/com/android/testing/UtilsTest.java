package com.android.testing;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class UtilsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testEmailForValidity() {
        String testEmail = "     anupamchugh@gmail.com";
        Assert.assertThat(String.format("Email Validity Test failed for %s ", testEmail),
                Utils.checkEmailForValidity(testEmail), is(true));
    }

    @Test
    public void testCalendarDate() {
        long inMillis = System.currentTimeMillis();
        Date date = Utils.calendarDate(inMillis);
        assertEquals("Date time in millis is wrong",
                inMillis * 1000, date.getTime());
    }
}