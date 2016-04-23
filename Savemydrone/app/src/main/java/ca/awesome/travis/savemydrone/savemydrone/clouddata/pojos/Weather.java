package ca.awesome.travis.savemydrone.savemydrone.clouddata.pojos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tco on 16-04-23.
 */
public class Weather {

    private String rawMetar;
    private String observationTime;
    private Integer tempC;
    private Integer dewpointC;
    private Integer windDirectionDeg;
    private Integer windSpeedKt;
    private Integer windGustKt;
    private Double visibilitySmi;
    private Double altimeterIn;
    private List<Sky> sky = new ArrayList<Sky>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The rawMetar
     */
    public String getRawMetar() {
        return rawMetar;
    }

    /**
     * @param rawMetar The raw_metar
     */
    public void setRawMetar(String rawMetar) {
        this.rawMetar = rawMetar;
    }

    /**
     * @return The observationTime
     */
    public String getObservationTime() {
        return observationTime;
    }

    /**
     * @param observationTime The observation_time
     */
    public void setObservationTime(String observationTime) {
        this.observationTime = observationTime;
    }

    /**
     * @return The tempC
     */
    public Integer getTempC() {
        return tempC;
    }

    /**
     * @param tempC The temp_c
     */
    public void setTempC(Integer tempC) {
        this.tempC = tempC;
    }

    /**
     * @return The dewpointC
     */
    public Integer getDewpointC() {
        return dewpointC;
    }

    /**
     * @param dewpointC The dewpoint_c
     */
    public void setDewpointC(Integer dewpointC) {
        this.dewpointC = dewpointC;
    }

    /**
     * @return The windDirectionDeg
     */
    public Integer getWindDirectionDeg() {
        return windDirectionDeg;
    }

    /**
     * @param windDirectionDeg The wind_direction_deg
     */
    public void setWindDirectionDeg(Integer windDirectionDeg) {
        this.windDirectionDeg = windDirectionDeg;
    }

    /**
     * @return The windSpeedKt
     */
    public Integer getWindSpeedKt() {
        return windSpeedKt;
    }

    /**
     * @param windSpeedKt The wind_speed_kt
     */
    public void setWindSpeedKt(Integer windSpeedKt) {
        this.windSpeedKt = windSpeedKt;
    }

    /**
     * @return The windGustKt
     */
    public Integer getWindGustKt() {
        return windGustKt;
    }

    /**
     * @param windGustKt The wind_gust_kt
     */
    public void setWindGustKt(Integer windGustKt) {
        this.windGustKt = windGustKt;
    }

    /**
     * @return The visibilitySmi
     */
    public Double getVisibilitySmi() {
        return visibilitySmi;
    }

    /**
     * @param visibilitySmi The visibility_smi
     */
    public void setVisibilitySmi(Double visibilitySmi) {
        this.visibilitySmi = visibilitySmi;
    }

    /**
     * @return The altimeterIn
     */
    public Double getAltimeterIn() {
        return altimeterIn;
    }

    /**
     * @param altimeterIn The altimeter_in
     */
    public void setAltimeterIn(Double altimeterIn) {
        this.altimeterIn = altimeterIn;
    }

    /**
     * @return The sky
     */
    public List<Sky> getSky() {
        return sky;
    }

    /**
     * @param sky The sky
     */
    public void setSky(List<Sky> sky) {
        this.sky = sky;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}