package ca.awesome.travis.savemydrone.savemydrone;

import java.util.List;

import ca.awesome.travis.savemydrone.savemydrone.clouddata.Airport;
import ca.awesome.travis.savemydrone.savemydrone.clouddata.Airports;

/**
 * Created by tco on 16-04-23.
 */
public class SharedPreferences {

    private int flightTime;
    private float flightRange;
    private List<Airport> airports;

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

    public List<Airport> getAirports() {
        return airports;
    }

    public void setAirports(List<Airport> airports) {
        this.airports = airports;
    }

}
