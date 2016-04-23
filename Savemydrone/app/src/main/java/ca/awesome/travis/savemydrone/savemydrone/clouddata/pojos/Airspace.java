package ca.awesome.travis.savemydrone.savemydrone.clouddata.pojos;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tco on 16-04-23.
 */
public class Airspace {

    private String _class;
    private String name;
    private String hours;
    private String altitudeLower;
    private String altitudeUpper;
    private String area;
    private String bboxNorth;
    private String bboxSouth;
    private String bboxEast;
    private String bboxWest;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The _class
     */
    public String getClass_() {
        return _class;
    }

    /**
     *
     * @param _class
     * The class
     */
    public void setClass_(String _class) {
        this._class = _class;
    }

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
     * The hours
     */
    public String getHours() {
        return hours;
    }

    /**
     *
     * @param hours
     * The hours
     */
    public void setHours(String hours) {
        this.hours = hours;
    }

    /**
     *
     * @return
     * The altitudeLower
     */
    public String getAltitudeLower() {
        return altitudeLower;
    }

    /**
     *
     * @param altitudeLower
     * The altitude_lower
     */
    public void setAltitudeLower(String altitudeLower) {
        this.altitudeLower = altitudeLower;
    }

    /**
     *
     * @return
     * The altitudeUpper
     */
    public String getAltitudeUpper() {
        return altitudeUpper;
    }

    /**
     *
     * @param altitudeUpper
     * The altitude_upper
     */
    public void setAltitudeUpper(String altitudeUpper) {
        this.altitudeUpper = altitudeUpper;
    }

    /**
     *
     * @return
     * The area
     */
    public String getArea() {
        return area;
    }

    /**
     *
     * @param area
     * The area
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     *
     * @return
     * The bboxNorth
     */
    public String getBboxNorth() {
        return bboxNorth;
    }

    /**
     *
     * @param bboxNorth
     * The bbox_north
     */
    public void setBboxNorth(String bboxNorth) {
        this.bboxNorth = bboxNorth;
    }

    /**
     *
     * @return
     * The bboxSouth
     */
    public String getBboxSouth() {
        return bboxSouth;
    }

    /**
     *
     * @param bboxSouth
     * The bbox_south
     */
    public void setBboxSouth(String bboxSouth) {
        this.bboxSouth = bboxSouth;
    }

    /**
     *
     * @return
     * The bboxEast
     */
    public String getBboxEast() {
        return bboxEast;
    }

    /**
     *
     * @param bboxEast
     * The bbox_east
     */
    public void setBboxEast(String bboxEast) {
        this.bboxEast = bboxEast;
    }

    /**
     *
     * @return
     * The bboxWest
     */
    public String getBboxWest() {
        return bboxWest;
    }

    /**
     *
     * @param bboxWest
     * The bbox_west
     */
    public void setBboxWest(String bboxWest) {
        this.bboxWest = bboxWest;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public boolean areaIsPolygon(){

        try {
            JSONObject obj = new JSONObject(getArea());
            if (obj.getString("ShapeType").equals("Polygon")){
                return true;
            } else {
                return false;
            }

        } catch (JSONException e) {
            Log.e("JSON", "Error Parsing json: " + e.toString());

        }

        return false;

    }

    public List<LatLng> getPolygonPoints(){
        List<LatLng> points = new ArrayList<LatLng>();
        try {
            JSONObject obj = new JSONObject(getArea());
            JSONArray lngLats = obj.getJSONArray("Points");
            final int n = lngLats.length();
            for (int i = 0; i < n; ++i) {
                JSONObject latLngObj = lngLats.getJSONObject(i);
                LatLng latLng = new LatLng(latLngObj.getDouble("Latitude"), latLngObj.getDouble("Longitude"));
                points.add(latLng);
            }
        } catch (JSONException e) {
            Log.e("JSON", "Error Parsing json: " + e.toString());

        }

        return points;
    }

    public LatLng getCircleCenter(){
        LatLng latLng = null;
        try {
            JSONObject obj = new JSONObject(getArea());
            JSONObject latLngObj = obj.getJSONObject("CentrePoint");
            latLng = new LatLng(latLngObj.getDouble("Latitude"), latLngObj.getDouble("Longitude"));

        } catch (JSONException e) {
            Log.e("JSON", "Error Parsing json: " + e.toString());
        }
        return latLng;
    }

    public double getRadius(){
        double radius = 0;
        try {
            JSONObject obj = new JSONObject(getArea());
            radius = obj.getDouble("Radius");

        } catch (JSONException e) {
            Log.e("JSON", "Error Parsing json: " + e.toString());
        }

        return radius;
    }

}
