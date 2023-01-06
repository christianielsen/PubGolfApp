package aston.cs3mdd.pubgolf.ui.map;

import aston.cs3mdd.pubgolf.ui.map.models.ResultsItem;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeoAPICall {

    @GET("maps/api/geocode/json?")
    Call<ResultsItem> getData(@Query("latlng") String latlng,
                              @Query("key") String key);
}

