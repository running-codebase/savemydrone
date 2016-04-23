package ca.awesome.travis.savemydrone.savemydrone;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.Arrays;
import java.util.List;

import ca.awesome.travis.savemydrone.savemydrone.clouddata.AirportsRetrofitApi;
import ca.awesome.travis.savemydrone.savemydrone.clouddata.AirspacesRetrofitApi;
import ca.awesome.travis.savemydrone.savemydrone.clouddata.pojos.Airport;
import ca.awesome.travis.savemydrone.savemydrone.clouddata.pojos.Airspace;
import ca.awesome.travis.savemydrone.savemydrone.clouddata.pojos.LngLatBox;
import ca.awesome.travis.savemydrone.savemydrone.clouddata.SaveMyDroneApi;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private LocationManager locationManager;
    private LatLng currentLngLat;
    private GoogleMap mMap;
    private static final int circleRadius = 1000000;
    private static final int KILOMETRE = 1000;

    public SharedPreferences sharedPreferences;
    public AirspacesRetrofitApi airspacesRetrofitApi;
    public AirportsRetrofitApi airportsRetrofitApi;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        sharedPreferences = new SharedPreferences();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




        initializeLocationManager();
        downloadData();

//        goToIntroFragment();
//        goToFlightDetailsFragment();




    }

    private void initializeLocationManager(){
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                currentLngLat = new LatLng(location.getLatitude(), location.getLongitude());
                //addMarker(currentLngLat, "You are Here");
                updateMap();
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }

    private void downloadData(){
        airspacesRetrofitApi = new AirspacesRetrofitApi(this, sharedPreferences);
        airspacesRetrofitApi.getAirspaces();

        airportsRetrofitApi = new AirportsRetrofitApi(this, sharedPreferences);
        airportsRetrofitApi.getAirports();
    }

    private void goToIntroFragment(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        Fragment fragment = null;

        fragment = (Fragment) new IntroScreenFragment().newInstance();
        fragmentTransaction.addToBackStack(IntroScreenFragment.TAG);
        fragmentTransaction.replace(R.id.popup_frame_layout, fragment,
                IntroScreenFragment.TAG);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void goToFlightDetailsFragment(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        Fragment fragment = null;

        fragment = (Fragment) new FlightDetailsFragment().newInstance();
        fragmentTransaction.addToBackStack(FlightDetailsFragment.TAG);
        fragmentTransaction.replace(R.id.popup_frame_layout, fragment,
                IntroScreenFragment.TAG);
        fragmentTransaction.commitAllowingStateLoss();
    }


    private void updateMap() {

        if (currentLngLat != null) {
            //addCircle(currentLngLat, "", sharedPreferences.getFlightRange() * KILOMETRE, 1);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLngLat));
            zoomToFit();
        }
        //Draw the circle
        //make web call
        //zoom to relevant area

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        updateMap();
//        LatLng sydney = new LatLng(-10, 151);
//        moveToUserGPS();
//        addCircle(sydney, "Sydney", circleRadius, 10);
//        addPolygon(sydney);
//        addMarker(new LatLng(-34, 151), "marker title");
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


    }


    public void doneButtonPressed(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commitAllowingStateLoss();
        updateMap();
    }



    public void drawAirports(){
        List<Airport> airports = sharedPreferences.getAirports();

        for (Airport airport: airports){
            LatLng latLng = new LatLng(airport.getLatitude(), airport.getLongitude());
            String title = airport.getName();
            addCircle(latLng, title, 5 * KILOMETRE, 1);
//            addMarker(latLng, title);
        }
        updateMap();

    }

    public void drawAirspaces(){
        List<Airspace> airspaces = sharedPreferences.getAirspaces();
//        Airspace airspace = new Airspace();
//        airspace.areaIsPolygon();

        for (Airspace airspace: airspaces){
            LatLng latLng = new LatLng(0,0);
            if (airspace.areaIsPolygon()){
                List<LatLng> points = airspace.getPolygonPoints();
                addPolygon(points);
            } else {



                Log.d("Polygon", "Found circle");
            }
//            addPolygon(latLng);

//            LatLng latLng = new LatLng(airspace.getLatitude(), airport.getLongitude());
//            String title = airport.getName();
//            addMarker(latLng, title);
        }
        updateMap();
    }


    private void addMarker(LatLng latLng, String title) {
        mMap.addMarker(new MarkerOptions().position(latLng).title(title));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

    }

    private void zoomToFit(){
//        meters_per_pixel = 156543.03392 * Math.cos(latLng.lat() * Math.PI / 180) / Math.pow(2, zoom)
        if (currentLngLat != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLngLat, 12.0f));
        }
    }

    private void addCircle(LatLng center, String title, double radius, float width) {

        int mStrokeColor = Color.BLACK;
        int mFillColor = ContextCompat.getColor(this, R.color.transparentAirports);

        Circle circle = mMap.addCircle(new CircleOptions()
                .center(center)
                .radius(radius)
                .strokeWidth(width)
                .strokeColor(mStrokeColor)
                .fillColor(mFillColor));
    }

    private void addPolygon(List<LatLng> points){
        Polygon mClickablePolygonWithHoles = mMap.addPolygon(new PolygonOptions()
                        .addAll(points)
//                .addHole(createRectangle(new LatLng(-22, 128), 1, 1))
//                .addHole(createRectangle(new LatLng(-18, 133), 0.5, 1.5))
                        .fillColor(Color.RED)
                        .strokeColor(Color.BLUE)
                        .strokeWidth(5)
//                .clickable(mClickabilityCheckbox.isChecked())
        );
    }


     /**
     * Creates a List of LatLngs that form a rectangle with the given dimensions.
     */
    private List<LatLng> createRectangle(LatLng center, double halfWidth, double halfHeight) {
        return Arrays.asList(new LatLng(center.latitude - halfHeight, center.longitude - halfWidth),
                new LatLng(center.latitude - halfHeight, center.longitude + halfWidth),
                new LatLng(center.latitude + halfHeight, center.longitude + halfWidth),
                new LatLng(center.latitude + halfHeight, center.longitude - halfWidth),
                new LatLng(center.latitude - halfHeight, center.longitude - halfWidth));
    }

    private List<LatLng> createPolygon() {

        Airspace airspace = new Airspace();
        airspace.areaIsPolygon();
        List<LatLng> points = airspace.getPolygonPoints();

        return points;


    }

}
