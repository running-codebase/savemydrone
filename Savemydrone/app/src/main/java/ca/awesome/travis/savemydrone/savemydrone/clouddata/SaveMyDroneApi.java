package ca.awesome.travis.savemydrone.savemydrone.clouddata;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.Call;

/**
 *
 * Created by tco on 16-04-23.
 */
public interface SaveMyDroneApi {

    @GET("/2.2/questions?order=desc&sort=creation&site=stackoverflow")
    Call<Airports> loadQuestions(@Query("tagged") String tags);


}
