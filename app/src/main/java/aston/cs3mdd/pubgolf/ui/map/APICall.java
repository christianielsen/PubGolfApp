package aston.cs3mdd.pubgolf.ui.map;

import aston.cs3mdd.pubgolf.ui.map.directionsmodels.Directions;
import aston.cs3mdd.pubgolf.ui.map.placemodels.ResultsItem;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APICall {

//    Retrofit interface for the nearby place details
    @GET("maps/api/place/nearbysearch/json?")
    Call<ResultsItem> getData(@Query("location") String loc,
                                    @Query("radius") String radius,
                                    @Query("type") String type,
                                    @Query("keyword") String keyword,
                                    @Query("key") String key);

//    Retrofit interface for the directions to a place
    @GET("maps/api/directions/json?")
    Call<Directions> getDirections(@Query("origin") String origin,
                                   @Query("destination") String destination,
                                   @Query("mode") String mode,
                                   @Query("key") String key);
}


