package ca.awesome.travis.savemydrone.savemydrone.clouddata.pojos;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tco on 16-04-23.
 */
public class LngLatBox {

    private Double north;
    private Double south;
    private Double east;
    private Double west;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    public LngLatBox(Double north, Double south, Double east, Double west) {
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
    }

    /**
     *
     * @return
     * The north
     */
    public Double getNorth() {
        return north;
    }

    /**
     *
     * @param north
     * The north
     */
    public void setNorth(Double north) {
        this.north = north;
    }

    /**
     *
     * @return
     * The south
     */
    public Double getSouth() {
        return south;
    }

    /**
     *
     * @param south
     * The south
     */
    public void setSouth(Double south) {
        this.south = south;
    }

    /**
     *
     * @return
     * The east
     */
    public Double getEast() {
        return east;
    }

    /**
     *
     * @param east
     * The east
     */
    public void setEast(Double east) {
        this.east = east;
    }

    /**
     *
     * @return
     * The west
     */
    public Double getWest() {
        return west;
    }

    /**
     *
     * @param west
     * The west
     */
    public void setWest(Double west) {
        this.west = west;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }


}
