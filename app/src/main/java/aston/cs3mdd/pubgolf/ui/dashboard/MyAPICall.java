package aston.cs3mdd.pubgolf.ui.dashboard;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyAPICall {

    // https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=52.573012,-1.173310&radius=500&type=restaurant&key=AIzaSyAE1O94eM9xi9_NR9jEZwm9xuKWUFyWCOU
//?location=52.573012,-1.173310&radius=500&type=restaurant&key=AIzaSyAE1O94eM9xi9_NR9jEZwm9xuKWUFyWCOU

    @GET("maps/api/place/nearbysearch/json?")
    Call<ResultsItem> getData(@Query("location") String loc,
                              @Query("radius") String radius,
                              @Query("type") String type,
                              @Query("keyword") String keyword,
                              @Query("key") String key);
}
