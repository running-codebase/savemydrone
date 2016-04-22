package ca.awesome.travis.savemydrone.savemydrone;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int circleRadius = 10000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

//        mMap.addCircle()

        LatLng sydney = new LatLng(-34, 151);
      //  moveToUserGPS();

        addCircle(sydney, "Sydney", circleRadius, 10);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

//        addMarker(new LatLng(-34, 151), "marker title");

    }



    private void moveToUserGPS(){
        // Add a marker in Sydney and move the camera

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

    private void addPolygon(){
//        Polygon mClickablePolygonWithHoles = mMap.addPolygon(new PolygonOptions()
//                .addAll(createRectangle(new LatLng(-20, 130), 5, 5))
//                .addHole(createRectangle(new LatLng(-22, 128), 1, 1))
//                .addHole(createRectangle(new LatLng(-18, 133), 0.5, 1.5))
//                .fillColor(Color.CYAN)
//                .strokeColor(Color.BLUE)
//                .strokeWidth(5)
//                .clickable(mClickabilityCheckbox.isChecked()));
    }

}
