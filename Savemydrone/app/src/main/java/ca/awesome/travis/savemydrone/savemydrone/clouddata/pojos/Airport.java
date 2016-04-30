package ca.awesome.travis.savemydrone.savemydrone.clouddata.pojos;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tco on 16-04-23.
 */
public class Airport {

    private String name;
    private String city;
    private String country;
    private String iata;
    private Double latitude;
    private Double longitude;
    private String icao;
    private Weather weather;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The city
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * @param city
     * The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return
     * The country
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     * The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * @return
     * The iata
     */
    public String getIata() {
        return iata;
    }

    /**
     *
     * @param iata
     * The iata
     */
    public void setIata(String iata) {
        this.iata = iata;
    }

    /**
     *
     * @return
     * The latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     *
     * @param latitude
     * The latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     *
     * @return
     * The longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     *
     * @param longitude
     * The longitude
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     *
     * @return
     * The icao
     */
    public String getIcao() {
        return icao;
    }

    /**
     *
     * @param icao
     * The icao
     */
    public void setIcao(String icao) {
        this.icao = icao;
    }

    /**
     *
     * @return
     * The weather
     */
    public Weather getWeather() {
        return weather;
    }

    /**
     *
     * @param weather
     * The weather
     */
    public void setWeather(Weather weather) {

        this.weather = weather;
    }


    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }


    public double getDistanceFromPoint(LatLng point) {
        LatLng airportPosition = new LatLng(latitude, longitude);

        final int earthRadius = 6371;

        double deltaLat = degToRad(airportPosition.latitude - point.latitude);
        double deltaLon = degToRad(airportPosition.longitude - point.longitude);


        double a =
                Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                        Math.cos(degToRad(point.latitude)) * Math.cos(degToRad(airportPosition.latitude)) *
                                Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = earthRadius * c; // Distance in km
        return d;
    }

    private double degToRad(double deg) {
        return deg * (Math.PI / 180);
    }


}
