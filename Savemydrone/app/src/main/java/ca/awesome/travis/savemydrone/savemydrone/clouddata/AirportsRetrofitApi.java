package ca.awesome.travis.savemydrone.savemydrone.clouddata;

import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import ca.awesome.travis.savemydrone.savemydrone.MapsActivity;
import ca.awesome.travis.savemydrone.savemydrone.SharedPreferences;
import ca.awesome.travis.savemydrone.savemydrone.clouddata.pojos.Airport;
import ca.awesome.travis.savemydrone.savemydrone.clouddata.pojos.LngLatBox;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by tco on 16-04-23.
 */
public class AirportsRetrofitApi implements Callback<List<Airport>> {

    private MapsActivity mapsActivity;
    private SharedPreferences sharedPreferences;

    public AirportsRetrofitApi(MapsActivity mapsActivity, SharedPreferences sharedPreferences) {
        this.mapsActivity = mapsActivity;
        this.sharedPreferences = sharedPreferences;
    }

    public void getAirports(LatLng currentLngLat){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://savemydrone.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // prepare call in Retrofit 2.0
        SaveMyDroneApi saveMyDroneApi = retrofit.create(SaveMyDroneApi.class);

//        LngLatBox lngLatBox = new LngLatBox(currentLngLat.latitude + MapsActivity.LATITUDE_CONVERSION * 200 ,
//                currentLngLat.latitude - MapsActivity.LATITUDE_CONVERSION * 200,
//                currentLngLat.longitude - MapsActivity.LONGITUDE_CONVERSION * 200,
//                currentLngLat.longitude + MapsActivity.LONGITUDE_CONVERSION * 200);

        LngLatBox lngLatBox = new LngLatBox(-34.846389, -38.846389, 150.793333, 144.793333);

        Call<List<Airport>> call = saveMyDroneApi.getAirportWeathers(lngLatBox);

        call.enqueue(this);
    }

    @Override
    public void onResponse(Response<List<Airport>> response, Retrofit retrofit) {

        sharedPreferences.setAirports(response.body());
//        List<Airport> airports = sharedPreferences.getAirports();
        mapsActivity.drawAirports();
        mapsActivity.updateWeather();
    }


    @Override
    public void onFailure(Throwable t) {
        Toast.makeText(mapsActivity, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }


}
