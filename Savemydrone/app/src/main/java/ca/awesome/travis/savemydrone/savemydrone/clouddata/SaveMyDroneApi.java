package ca.awesome.travis.savemydrone.savemydrone.clouddata;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.Call;

/**
 *
 * Created by tco on 16-04-23.
 */
public interface SaveMyDroneApi {

//    @GET("/2.2/questions?order=desc&sort=creation&site=stackoverflow")
//    Call<Airports> loadQuestions(@Query("tagged") String tags);


    @POST("/api/weather")
    Call<List<Airport>> getAirportWeathers(@Body LngLatBox lngLatBox);
}
