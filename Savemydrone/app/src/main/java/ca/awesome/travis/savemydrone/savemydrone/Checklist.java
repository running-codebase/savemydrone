package ca.awesome.travis.savemydrone.savemydrone;

import android.graphics.Point;

/**
 * Created by Edward on 23/04/2016.
 */
public class Checklist {


//    Need to work out names of variables to pass to this method

//    public int pnpoly(int nvert, float *vertx, float *verty, float testx, float testy)
//    {
//        int i, j, c = 0;
//        for (i = 0, j = nvert-1; i < nvert; j = i++) {
//            if ( ((verty[i]>testy) != (verty[j]>testy)) &&
//                    (testx < (vertx[j]-vertx[i]) * (testy-verty[i]) / (verty[j]-verty[i]) + vertx[i]) )
//                c = !c;
//        }
//        return c;
//    }


    public boolean isWindy(double windGust, double windSteady) {

        boolean isWindyGust = isGusty(windGust);
        boolean isWindySteady = isSteady(windSteady);

        if (isWindyGust | isWindySteady) {
            return true;
        } else {
            return false;
        }
    }



    // Check if wind gusts exceed limit
    private boolean isGusty(double windGust) {
        double windGustLimit = 15; // knots
        //Check it here
        return windGustLimit < windGust;
    }

    // Check if steady wind speed exceeds limit
    private boolean isSteady(double windSteady) {
        double windSteadyLimit = 10; // knots
        //Check it here
        return windSteadyLimit < windSteady;
    }

    public boolean isSafeAirspace(){
        //TODO - implementation
        return true;
    }




    // Check if there is sufficient light before sunset
    public boolean isDark(int time) {
        int daylightLimit = 30; // minutes before sunset
        //Check it here
        return daylightLimit < time;
    }


//    // Check if cloud base is lower than limit
//    public boolean isCloudy(double cloudBase) {
//        int cloudBaseLimit = 500; // feet
//        //Check it here
//        return cloudBaseLimit > cloudBase;
//    }

//    // Check if there is sufficient visibility
//    public boolean isLowVis(int visibility) {
////      Define minimum visibility
//        double visibilityLimit = 1.5 * 1.609; // km (1km=1.609 statute miles)
//        return visibilityLimit > visibility;
//    }

//    public void checklistCriteria(boolean airspaceCheck, boolean windCheck, boolean cloudCheck,
//                                  boolean daylightCheck, boolean visibilityCheck) {
///**
// * Logic for determining if weather criteria are satisfied
// */
//
//        if (!windCheck | !cloudCheck | !daylightCheck | !visibilityCheck) {
//
//            boolean weatherCheck = false;
//
//        } else {
//            boolean weatherCheck = true;
//        }
//
//
//        /**
//         * Logic for determining advice to be given to pilot about whether flight should proceed
//         */
//
//        if (airspaceCheck && weatherCheck) {
//
//            String advice = "Checklist complete. You're cleared for takeoff!";
//
//        } else if (airspaceCheck && !weatherCheck) {
//
//            String advice = "Location is fine but today's conditions may make this flight your last.";
//
//        } else if (!airspaceCheck && weatherCheck) {
//
//            String advice = "Conditions are fine but this airspace is not safe to fly in.";
//
//        } else if (!airspaceCheck && !weatherCheck) {
//
//            String advice = "Sorry, you're out of luck. The weather isn't cooperating and this isn't a safe location to fly in anyway."
//        }
//    return advice;
//
//    }
}