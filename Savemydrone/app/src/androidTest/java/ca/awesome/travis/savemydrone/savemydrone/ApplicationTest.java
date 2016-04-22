package ca.awesome.travis.savemydrone.savemydrone;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import org.junit.Test;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }


    @Test
    public void testOverlapAlgorithms(){
        ApiData apiData = new ApiData();

        double lat1 = 0;
        double lng1 = 10;
        double lat2 = 0;
        double lng2 = 10;

        double correctAnswer = 10;
        assertEquals(correctAnswer, apiData.haversine(lat1, lng1, lat2, lng2), 0.5);

    }

    @Test
    public void testOverlapAlgorithmsSecondTime(){
        ApiData apiData = new ApiData();

        double lat1 = 0;
        double lng1 = 10;
        double lat2 = 0;
        double lng2 = 10;

        double correctAnswer = 10;
        assertEquals(correctAnswer, apiData.haversine(lat1, lng1, lat2, lng2), 0.5);

    }



}