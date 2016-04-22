package ca.awesome.travis.savemydrone.savemydrone;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private LocationManager locationManager;
    private LatLng currentLngLat;
    private GoogleMap mMap;
    private static final int circleRadius = 1000000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initializeLocationManager();

    }

    private void initializeLocationManager(){
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                currentLngLat = new LatLng(location.getLatitude(), location.getLongitude());
                addMarker(currentLngLat, "You are Here");
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        LatLng sydney = new LatLng(-10, 151);
//        moveToUserGPS();
//        addCircle(sydney, "Sydney", circleRadius, 10);
//        addPolygon(sydney);
//        addMarker(new LatLng(-34, 151), "marker title");
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


    }

    private void addMarker(LatLng latLng, String title) {
        mMap.addMarker(new MarkerOptions().position(latLng).title(title));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

    }

    private void addCircle(LatLng center, String title, double radius, float width) {

        int mStrokeColor = Color.BLACK;
        int mFillColor = Color.RED;

        Circle circle = mMap.addCircle(new CircleOptions()
                .center(center)
                .radius(radius)
                .strokeWidth(width)
                .strokeColor(mStrokeColor)
                .fillColor(mFillColor));
    }

    private void addPolygon(LatLng latLng){
        Polygon mClickablePolygonWithHoles = mMap.addPolygon(new PolygonOptions()
                        .addAll(createPolygon())
//                .addHole(createRectangle(new LatLng(-22, 128), 1, 1))
//                .addHole(createRectangle(new LatLng(-18, 133), 0.5, 1.5))
                        .fillColor(Color.CYAN)
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
        return Arrays.asList(
                new LatLng(-34, 155),
                new LatLng(-39, 160),
                new LatLng(-45, 151),
                new LatLng(-39, 143),
                new LatLng(-20, 127),
                new LatLng(-10, 151));


    }

}
