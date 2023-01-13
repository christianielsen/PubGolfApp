package aston.cs3mdd.pubgolf.ui.map;

import aston.cs3mdd.pubgolf.ui.map.models.ResultsItem;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APICall {

    // https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=52.573012,-1.173310&radius=500&type=restaurant&key=AIzaSyAE1O94eM9xi9_NR9jEZwm9xuKWUFyWCOU
//?location=52.573012,-1.173310&radius=500&type=restaurant&key=AIzaSyAE1O94eM9xi9_NR9jEZwm9xuKWUFyWCOU

    @GET("maps/api/place/nearbysearch/json?")
    Call<ResultsItem> getData(@Query("location") String loc,
                                    @Query("radius") String radius,
                                    @Query("type") String type,
                                    @Query("keyword") String keyword,
                                    @Query("key") String key);

    @GET("maps/api/directions/json?")
    Call<aston.cs3mdd.pubgolf.map.model.Directions> getDirections(@Query("origin") String origin,
                                                                  @Query("destination") String destination,
                                                                  @Query("mode") String mode,
                                                                  @Query("key") String key);
}


