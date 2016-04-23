package ca.awesome.travis.savemydrone.savemydrone.clouddata;

import android.widget.Toast;

import java.util.List;

import ca.awesome.travis.savemydrone.savemydrone.MapsActivity;
import ca.awesome.travis.savemydrone.savemydrone.SharedPreferences;
import ca.awesome.travis.savemydrone.savemydrone.clouddata.pojos.Airport;
import ca.awesome.travis.savemydrone.savemydrone.clouddata.pojos.Airspace;
import ca.awesome.travis.savemydrone.savemydrone.clouddata.pojos.LngLatBox;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by tco on 16-04-23.
 */
public class AirspacesRetrofitApi implements Callback<List<Airspace>> {

    private MapsActivity mapsActivity;
    private SharedPreferences sharedPreferences;

    public AirspacesRetrofitApi(MapsActivity mapsActivity, SharedPreferences sharedPreferences) {
        this.mapsActivity = mapsActivity;
        this.sharedPreferences = sharedPreferences;
    }

    public void getAirspaces(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://savemydrone.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // prepare call in Retrofit 2.0
        SaveMyDroneApi saveMyDroneApi = retrofit.create(SaveMyDroneApi.class);

        LngLatBox lngLatBox = new LngLatBox(-34.846389, -38.846389, 150.793333, 144.793333);

        Call<List<Airspace>> call = saveMyDroneApi.getAirspaces(lngLatBox);

        call.enqueue(this);
    }


    @Override
    public void onResponse(Response<List<Airspace>> response, Retrofit retrofit) {

        sharedPreferences.setAirspaces(response.body());
//        List<Airport> airports = sharedPreferences.getAirspaces();
        mapsActivity.drawAirspaces();
    }

    @Override
    public void onFailure(Throwable t) {
        Toast.makeText(mapsActivity, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }
}
