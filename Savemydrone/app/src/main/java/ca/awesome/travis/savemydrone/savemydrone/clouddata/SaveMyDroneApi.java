package ca.awesome.travis.savemydrone.savemydrone.clouddata;

import java.util.List;

import ca.awesome.travis.savemydrone.savemydrone.clouddata.pojos.Airport;
import ca.awesome.travis.savemydrone.savemydrone.clouddata.pojos.Airspace;
import ca.awesome.travis.savemydrone.savemydrone.clouddata.pojos.LngLatBox;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.Call;

/**
 *
 * Created by tco on 16-04-23.
 */
public interface SaveMyDroneApi {

//    @GET("/2.2/questions?order=desc&sort=creation&site=stackoverflow")
//    Call<Airports> loadQuestions(@Query("tagged") String tags);

    @POST("/api/airspace")
    Call<List<Airspace>> getAirspaces(@Body LngLatBox lngLatBox);


    @POST("/api/weather")
    Call<List<Airport>> getAirportWeathers(@Body LngLatBox lngLatBox);
}
