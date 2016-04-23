package ca.awesome.travis.savemydrone.savemydrone;

/**
 * Created by Edward on 23/04/2016.
 */
public class Checklist {

//    Logic for comparing actual steady wind speed and wind gusts to criteria wind speed and gusts

    public boolean isGusty(double windGust){
        boolean isGusty = false;
        //Check it here

        return isGusty;
    }


    public void windCheck(double windSteady, double windGust) {
//      Define steady and gust wind limits
        double windSteadyLimit = 10; // knots
        double windGustLimit = 15; // knots
        boolean windCheck;

//      Determine whether the steady or gust wind limits are exceeded
        if (windSteady > windSteadyLimit | windGust > windGustLimit) {
            windCheck = false;
        } else {
            windCheck = true;
        }
        return;
    }

//      Logic for comparing actual cloud base to criteria cloud base

    public void cloudCheck(int cloudBase) {
//      Define lower cloud base limit
        int cloudBaseLimit=500; // feet
        boolean cloudCheck;

//      Determine whether the cloud base limits are exceeded
        if (cloudBase <= cloudBaseLimit) {
            cloudCheck = false;
        } else cloudCheck = true;
        return;
    }

    public void daylightCheck(int time) {
//      Define minimum time before sunset
        int daylightLimit=30; // minutes before sunset
        boolean daylightCheck;

//      Determine whether there is sufficient time for estimated flight duration before sunset
        if (time > daylightLimit) {
            daylightCheck = false;
        } else daylightCheck = true;
        return;
    }

    public void visibilityCheck(int visibility) {
//      Define minimum visibility
        double visibilityLimit=1.5*1.609; // km (1km=1.609 statute miles)
        boolean visibilityCheck;

//      Determine whether there is sufficient visibility
        if (visibility < visibilityLimit) {
            visibilityCheck = false;
        } else visibilityCheck = true;
        return;
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