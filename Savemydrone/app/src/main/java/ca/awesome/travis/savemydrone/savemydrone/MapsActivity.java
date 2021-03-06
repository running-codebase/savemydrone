package ca.awesome.travis.savemydrone.savemydrone;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import ca.awesome.travis.savemydrone.savemydrone.clouddata.AirportsRetrofitApi;
import ca.awesome.travis.savemydrone.savemydrone.clouddata.AirspacesRetrofitApi;
import ca.awesome.travis.savemydrone.savemydrone.clouddata.pojos.Airport;
import ca.awesome.travis.savemydrone.savemydrone.clouddata.pojos.Airspace;
import ca.awesome.travis.savemydrone.savemydrone.clouddata.pojos.FlightReport;
import ca.awesome.travis.savemydrone.savemydrone.clouddata.pojos.LngLatBox;
import ca.awesome.travis.savemydrone.savemydrone.clouddata.SaveMyDroneApi;
import ca.awesome.travis.savemydrone.savemydrone.clouddata.pojos.Sky;
import ca.awesome.travis.savemydrone.savemydrone.clouddata.pojos.Weather;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {


//    private static LatLng debugLngLat = new LatLng( -37.413001, 144.912844);

    private static LatLng debugLngLat = new LatLng(  -37.492854, 144.876110);




    private static final int circleRadius = 1000000;
    private static final int KILOMETRE = 1000;
    private static final int NAUTICAL_MILE = 1852;
    public static final double LATITUDE_CONVERSION = 110.574;
    public static final double LONGITUDE_CONVERSION = 111.320; // 1 deg = 111.320*cos(latitude);

    public enum AppState {
        PRE_FLIGHT,
        FLIGHT_CHECK,
        START_FLIGHT,
        IN_FLIGHT,
        FLIGHT_OVER
    }

    public AppState currentState = AppState.PRE_FLIGHT;

    private boolean locationZoomed = false;
    private boolean dataDownloaded = false;
    private boolean timerRunning = false;

    private boolean isDebug = true;


    private Chronometer chronometer;
    private TextView bottomInstructionTextview;
    private RelativeLayout bottomDetailsRelativeLayout;
    public CardView cardView;

    private LocationManager locationManager;
    private LatLng currentLngLat;
    private GoogleMap mMap;

    TextToSpeech tts;


    public SharedPreferences sharedPreferences;
    public AirspacesRetrofitApi airspacesRetrofitApi;
    public AirportsRetrofitApi airportsRetrofitApi;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        chronometer = (Chronometer) findViewById(R.id.countdown_chronometer);
        bottomInstructionTextview = (TextView) findViewById(R.id.bottom_instruction_textview);
        bottomDetailsRelativeLayout = (RelativeLayout) findViewById(R.id.bottom_details_relative_layout);
        bottomDetailsRelativeLayout.setOnClickListener(this);

        sharedPreferences = new SharedPreferences();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        initVoice();
        initializeLocationManager();
        downloadData(currentLngLat);


        goToIntroFragment();

    }

    private void initializeLocationManager(){
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.

                if (isDebug){
                    currentLngLat = debugLngLat;
                } else {
                    currentLngLat = new LatLng(location.getLatitude(), location.getLongitude());
                }

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

    private void downloadData(LatLng currentLngLat){
        if (!dataDownloaded){

            airspacesRetrofitApi = new AirspacesRetrofitApi(this, sharedPreferences);
            airspacesRetrofitApi.getAirspaces(currentLngLat);

            airportsRetrofitApi = new AirportsRetrofitApi(this, sharedPreferences);
            airportsRetrofitApi.getAirports(currentLngLat);
            dataDownloaded = true;
        }

    }

    private void initVoice(){
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener(){

            @Override
            public void onInit(int status) {
                // TODO Auto-generated method stub
                if(status == TextToSpeech.SUCCESS){
                    int result=tts.setLanguage(Locale.US);
                    if(result==TextToSpeech.LANG_MISSING_DATA ||
                            result==TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("error", "This Language is not supported");
                    }
                    else{
                        //ConvertTextToSpeech("It's working");
                    }
                }
                else
                    Log.e("error", "Initilization Failed!");
            }
        });
        tts.setLanguage(Locale.US);
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

    private void goToFlightCheckFragment(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        Fragment fragment = null;

        fragment = (Fragment) new ChecklistFragment().newInstance();
        fragmentTransaction.addToBackStack(ChecklistFragment.TAG);
        fragmentTransaction.replace(R.id.popup_frame_layout, fragment,
                IntroScreenFragment.TAG);
        fragmentTransaction.commitAllowingStateLoss();

    }

    private void goToFlightReportFragment(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        Fragment fragment = null;

        fragment = (Fragment) new FlightReportFragment().newInstance();
        fragmentTransaction.addToBackStack(FlightReportFragment.TAG);
        fragmentTransaction.replace(R.id.popup_frame_layout, fragment,
                IntroScreenFragment.TAG);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void debriefComplete(FlightReport flightReport) {
        //Add marker

        addMarker(currentLngLat, flightReport.getTitle());
        bottomInstructionTextview.setText("FLIGHT CHECK");
        bottomDetailsRelativeLayout.setVisibility(View.VISIBLE);


    }


    private void updateMap() {

        if (currentLngLat != null) {

            if (!locationZoomed){
                mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLngLat));
                zoomToFit();
                locationZoomed = true;
            }
        }


        //Draw the circle
        //make web call
        //zoom to relevant area

    }

    /*
 * Draw the weather into the weather section?
 */
    public void updateWeather() {
        Map<Weather, Double> weatherSources = new HashMap<Weather, Double>();

        // iterate through all the airports to see if we have any weather to use
        for (Airport airport : sharedPreferences.getAirports()) {
            Weather airportWeather = airport.getWeather();

            if (airportWeather == null || airportWeather.getRawMetar() == null)
                continue;

            double distance = 0;

            if (currentLngLat != null)
                distance = airport.getDistanceFromPoint(currentLngLat);

            weatherSources.put(airportWeather, distance);
        }

        Weather fakeWeather = new Weather();
        fakeWeather.setAltimeterIn(29.92);
        fakeWeather.setDewpointC(10);
        fakeWeather.setObservationTime("now-ish");
        fakeWeather.setRawMetar("YMML WEATHER ABCDD");
        fakeWeather.setTempC(25);
        fakeWeather.setVisibilitySmi(5.0);
        fakeWeather.setWindDirectionDeg(180);
        fakeWeather.setWindSpeedKt(15);
        fakeWeather.setWindGustKt(20);

        Sky s = new Sky();
        s.setCloudBaseFtAgl(1500);
        s.setSkyCover("FEW");

        Sky s2 = new Sky();
        s2.setCloudBaseFtAgl(5000);
        s.setSkyCover("OVC");

        List<Sky> sky = new ArrayList<Sky>();
        sky.add(s);
        sky.add(s2);

        fakeWeather.setSky(sky);

        weatherSources.put(fakeWeather, 5.2);

        // we don't want to do anything else if we don't have any weather
        if (weatherSources.isEmpty())
            return;

        // sort the weather sources by their distances
        weatherSources = MapUtil.sortByValue(weatherSources);

        // get the first weather source (because they've been sorted)
        for (Weather weather : weatherSources.keySet()) {

            cardView = (CardView) findViewById(R.id.card_view);

            if (currentState != AppState.PRE_FLIGHT && currentState != AppState.PRE_FLIGHT ) {
                cardView.setVisibility(View.VISIBLE);
            }

            int windGusts = fakeWeather.getWindGustKt();
            String windText = String.format("%s%s kt", weather.getWindSpeedKt(), windGusts > 0 ? "-" + windGusts : "");

            TextView wind = (TextView) findViewById(R.id.wind);
            TextView windDirection = (TextView) findViewById(R.id.windDirection);
            TextView cloud = (TextView) findViewById(R.id.cloud);
            TextView sunset = (TextView) findViewById(R.id.sunset);

            wind.setText(windText);
            windDirection.setText(String.format("%d° (%s)", weather.getWindDirectionDeg(), degreeToCardinalPoint(weather.getWindDirectionDeg())));

            List<Sky> clouds = weather.getSky();

            if (clouds.isEmpty()) {
                cloud.setText("No cloud");
            } else {
                Sky individualCloud = clouds.get(0);
                cloud.setText(individualCloud.getSkyCover() +  " abv " + individualCloud.getCloudBaseFtAgl() + " ft");
            }

            sunset.setText("5:41 PM");
        }
    }

    private String degreeToCardinalPoint(int degree){
        String directions[] = {"N", "NE", "E", "SE", "S", "SW", "NW"};
        return directions[ (int)Math.round((  ((double)degree % 360) / 45)) ];
    }



    public void setFlightRadius(){
        if (currentLngLat!= null){
            addCircle(currentLngLat, "", sharedPreferences.getFlightRange() * KILOMETRE, 1, ContextCompat.getColor(this, R.color.flightRadius));
        }
    }

    public void setFlightTime(){
        chronometer.setBase(SystemClock.elapsedRealtime());
//        chronometer.setFormat("Formated Time - %s");
        chronometer.setVisibility(View.VISIBLE);
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

        updateBottomBarView();

        updateMap();
        updateWeather();
    }



    public void drawAirports(){
        List<Airport> airports = sharedPreferences.getAirports();

        for (Airport airport: airports){
            LatLng latLng = new LatLng(airport.getLatitude(), airport.getLongitude());
            String title = airport.getName();
            addCircle(latLng, title, 5 * KILOMETRE, 1, ContextCompat.getColor(this, R.color.transparentAirports));
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
                LatLng center = airspace.getCircleCenter();
                double radius = airspace.getRadius();
                addCircle(center, "", radius * NAUTICAL_MILE, 1, ContextCompat.getColor(this, R.color.transparentNoFlyZones));

            }
//            addPolygon(latLng);

//            LatLng latLng = new LatLng(airspace.getLatitude(), airport.getLongitude());
//            String title = airport.getName();
//            addMarker(latLng, title);
        }
        updateMap();
    }

    public void updateBottomBarView(){

        switch(currentState){
            case PRE_FLIGHT:
                bottomDetailsRelativeLayout.setVisibility(View.INVISIBLE);
                break;

            case FLIGHT_CHECK:
                bottomInstructionTextview.setText("FLIGHT CHECK");
                bottomDetailsRelativeLayout.setVisibility(View.VISIBLE);
                setFlightRadius();
                break;

            case START_FLIGHT:
                bottomInstructionTextview.setText("START FLIGHT");
                bottomDetailsRelativeLayout.setVisibility(View.VISIBLE);
                updateWeather();
                break;

            case IN_FLIGHT:
                setFlightTime();
                updateWeather();

                break;

            case FLIGHT_OVER:
                bottomInstructionTextview.setText("CREATE BRIEFING");
                updateWeather();

                break;
        }

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

    private void addCircle(LatLng center, String title, double radius, float width, int fillColor) {

        int mStrokeColor = Color.BLACK;

        Circle circle = mMap.addCircle(new CircleOptions()
                .center(center)
                .radius(radius)
                .strokeWidth(width)
                .strokeColor(mStrokeColor)
                .fillColor(fillColor));
    }

    private void addPolygon(List<LatLng> points){
        Polygon mClickablePolygonWithHoles = mMap.addPolygon(new PolygonOptions()
                        .addAll(points)
//                .addHole(createRectangle(new LatLng(-22, 128), 1, 1))
//                .addHole(createRectangle(new LatLng(-18, 133), 0.5, 1.5))
                        .fillColor(ContextCompat.getColor(this, R.color.transparentNoFlyZones))
                        .strokeColor(ContextCompat.getColor(this, R.color.transparentNoFlyZones))
                        .strokeWidth(1)
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

    private void setUpAndStartTimer(int minutes){

//        int milliseconds = minutes * 60 *1000;
//
        int milliseconds = 40000;


        CountDownTimer cT =  new CountDownTimer(milliseconds, 1000) {

            public void onTick(long millisUntilFinished) {

                String v = String.format("%02d", millisUntilFinished/60000);
                int va = (int)( (millisUntilFinished%60000)/1000);
                bottomInstructionTextview.setText("" +v+":"+String.format("%02d",va));

                playSetAudio(millisUntilFinished);



            }

            public void onFinish() {
                bottomInstructionTextview.setText("CREATE BRIEFING");
                timerRunning = false;
                currentState = AppState.FLIGHT_OVER;
            }
        };
        cT.start();
        timerRunning = true;



    }

    private void playSetAudio(long millisUntilFinished) {


        int seconds = (int) millisUntilFinished / 1000;
        Log.d("Timer", "value: " +seconds);


        if (seconds == 1) {
            ConvertTextToSpeech("Flight Completed");
        } else if (seconds == 10) {
            ConvertTextToSpeech("10 second flight warning");

        } else if (seconds == 20) {
            ConvertTextToSpeech("Wind has increased to 20 Knots South");

        } else if (seconds == 30) {
            ConvertTextToSpeech("30 second flight warning");

        }  else if (seconds == 39) {
            ConvertTextToSpeech("Flight Initiated");
        }

    }



    private void ConvertTextToSpeech(String text) {
        // TODO Auto-generated method stub
//        text = et.getText().toString();
        if(text==null||"".equals(text))
        {
            text = "Content not available";
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }else
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }


    ///----------------------------------

    public void planFlightPressed(){

        //Zoom to a useful area and allow free explore

        //TODO implement
    }

    public void flyNowButtonPressed(){
        goToFlightDetailsFragment();
    }

    public void debriefButtonPressed(){
        //TODO implement
    }

    @Override
    public void onClick(View v) {
        if (timerRunning) {
            return;
        }

        if (v.getId() == R.id.bottom_details_relative_layout){
            switch(currentState){
                case PRE_FLIGHT:
                    bottomDetailsRelativeLayout.setVisibility(View.INVISIBLE);
                    break;

                case FLIGHT_CHECK:
                    bottomDetailsRelativeLayout.setVisibility(View.INVISIBLE);
                    cardView.setVisibility(View.INVISIBLE);
                    goToFlightCheckFragment();

                    break;

                case START_FLIGHT:
                    bottomInstructionTextview.setText("");
                    currentState = AppState.IN_FLIGHT;
                    //setFlightTime();
                    //chronometer.start();
                    setUpAndStartTimer(sharedPreferences.getFlightTime());


                    break;

                case IN_FLIGHT:
                    bottomInstructionTextview.setText("CREATE BRIEFING");
                    currentState = AppState.IN_FLIGHT;
                    break;

                case FLIGHT_OVER:
                    goToFlightReportFragment();
                    bottomDetailsRelativeLayout.setVisibility(View.INVISIBLE);
                    cardView.setVisibility(View.INVISIBLE);
                    break;
            }
        }

    }
}