package ca.awesome.travis.savemydrone.savemydrone;

import android.graphics.Point;

/**
 * Created by Edward on 23/04/2016.
 */
public class Checklist {

    public boolean isWindy;
    public boolean isWindySteady;
    public boolean isWindyGust;
    public boolean isDark;

//    /**
//     * Return true if the given point is contained inside the boundary.
//     * See: http://www.ecse.rpi.edu/Homepages/wrf/Research/Short_Notes/pnpoly.html
//     * @param test The point to check
//     * @return true if the point is inside the boundary, false otherwise
//     *
//     */
//    public boolean contains(Point test) {
//        int i;
//        int j;
//        boolean result = false;
//        for (i = 0, j = points.length - 1; i < points.length; j = i++) {
//            if ((points[i].y > test.y) != (points[j].y > test.y) &&
//                    (test.x < (points[j].x - points[i].x) * (test.y - points[i].y) / (points[j].y-points[i].y) + points[i].x)) {
//                result = !result;
//            }
//        }
//        return result;
//    }

    // Check if wind gusts exceed limit
    public boolean isGusty(double windGust) {
        double windGustLimit = 15; // knots
        //Check it here
        return isWindyGust = windGustLimit < windGust;
    }

    // Check if steady wind speed exceeds limit
    public boolean isSteady(double windSteady) {
        double windSteadyLimit = 10; // knots
        //Check it here
        return isWindySteady = windSteadyLimit < windSteady;
    }

    public boolean isWindy(boolean isWindyGust, boolean isWindySteady) {
        if (isWindyGust | isWindySteady) {
            return isWindy = true;
        } else {
            return isWindy = false;
        }
    }


//    // Check if cloud base is lower than limit
//    public boolean isCloudy(double cloudBase) {
//        int cloudBaseLimit = 500; // feet
//        //Check it here
//        return cloudBaseLimit > cloudBase;
//    }

    // Check if there is sufficient light before sunset
    public boolean isDark(int time) {
        int daylightLimit = 30; // minutes before sunset
        //Check it here
        return isDark = daylightLimit < time;
    }

//    // Check if there is sufficient visibility
//    public boolean isLowVis(int visibility) {
////      Define minimum visibility
//        double visibilityLimit = 1.5 * 1.609; // km (1km=1.609 statute miles)
//        return visibilityLimit > visibility;
//    }

    public String flightAdvice(boolean isGusty, boolean isWindy, boolean isDark, boolean isSafeAirspace) {
        String flightAdvice;

        if (isGusty | isWindy | isDark) {
            flightAdvice = "The weather isn't great today. Perhaps you should consider returning when it's better.";
        } else if (!isSafeAirspace) {
            flightAdvice = "It is not allowed to fly in this airspace. Try moving outside this restricted zone.";
        } else
            flightAdvice = "Checklist complete. You're cleared for take off!";
        return flightAdvice;
    }

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