package ca.awesome.travis.savemydrone.savemydrone;

import java.util.List;

import ca.awesome.travis.savemydrone.savemydrone.clouddata.pojos.Airport;
import ca.awesome.travis.savemydrone.savemydrone.clouddata.pojos.Airspace;

/**
 * Created by tco on 16-04-23.
 */
public class SharedPreferences {

    private int flightTime = 1;
    private float flightRange = 3;
    private List<Airport> airports;
    private List<Airspace> airspaces;

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

    public List<Airspace> getAirspaces(){
        return airspaces;
    }

    public void setAirspaces(List<Airspace> airspaces){
        this.airspaces = airspaces;
    }

}
