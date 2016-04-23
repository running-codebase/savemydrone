package ca.awesome.travis.savemydrone.savemydrone;

/**
 * Created by tco on 16-04-23.
 */
public class SharedPreferences {

    private int flightTime;
    private float flightRange;

    public SharedPreferences(){

    }

    public void setFlightTime(int time){
        this.flightTime = time;
    }

    public int getFlightTime(){
        return flightTime;
    }

    public void setFlightRange(float range){
        this.flightRange = range;
    }

    public float getFlightRange(){
        return flightRange;
    }

}
