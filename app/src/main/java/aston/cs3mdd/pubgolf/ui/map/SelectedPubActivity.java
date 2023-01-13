package aston.cs3mdd.pubgolf.ui.map;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;

import aston.cs3mdd.pubgolf.R;
import aston.cs3mdd.pubgolf.ui.map.model.EndLocation__1;
import aston.cs3mdd.pubgolf.ui.map.model.Route;
import aston.cs3mdd.pubgolf.ui.map.model.StartLocation__1;
import aston.cs3mdd.pubgolf.ui.map.model.Step;
import aston.cs3mdd.pubgolf.ui.map.models.Restaurant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SelectedPubActivity extends AppCompatActivity {
    TextView tvSelectedPub;
    Button btTravel;
    Restaurant restaurant;
    Intent intent;
    private FusedLocationProviderClient fusedLocationClient;
    private Location mCurrentLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_pub);
        tvSelectedPub = findViewById(R.id.tvSelectedPub);
        intent = getIntent();

        if(intent != null) {
            restaurant = (Restaurant) intent.getSerializableExtra("data");
            String rName = restaurant.getName();
            tvSelectedPub.setText(rName);
        }
        btTravel = findViewById(R.id.btTravel);

//        btTravel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Retrofit Builder
//                Retrofit retrofit = new Retrofit.Builder()
//                        .baseUrl("https://maps.googleapis.com/")
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//
//                //Instance for interface
//                APICall APICall = retrofit.create(APICall.class);
//
//                String locLat = String.valueOf(mCurrentLocation.getLatitude());
//                String locLng = String.valueOf(mCurrentLocation.getLongitude());
//                String origin = locLat + "," + locLng;
////                String placeLat = restaurant.getLat();
////                String placeLng = restaurant.getLng();
////                String destination = placeLat + "," + placeLng;
//                String destination = "52.4767078,-1.911394099999999";
//                String mode = "walking";
//                String key = getResources().getString(R.string.API_KEY);
//
//                Call<aston.cs3mdd.pubgolf.map.model.Directions> call = APICall.getDirections(origin, destination, mode, key);
//                Log.i("AJB", call.toString());
//
//                call.enqueue(new Callback<aston.cs3mdd.pubgolf.map.model.Directions>() {
//                    @Override
//                    public void onResponse(Call<aston.cs3mdd.pubgolf.map.model.Directions> call, Response<aston.cs3mdd.pubgolf.map.model.Directions> response) {
//                        ArrayList<LatLng> routeList = new ArrayList<LatLng>();
//                        Route routeA = response.body().getRoutes().get(0);
//                        if (response.body().getRoutes().size() > 0) {
//                            List<Step> steps = routeA.getLegs().get(0).getSteps();
//                            Step step;
//                            StartLocation__1 start;
//                            EndLocation__1 end;
//                            String polyline;
//                            for (int i = 0; i < steps.size(); i++) {
//                                step = steps.get(i);
//                                start = step.getStartLocation();
//                                routeList.add((new LatLng(start.getLat(), start.getLng())));
//                                polyline = step.getPolyline().getPoints();
//                                List<LatLng> decodelist = PolyUtil.decode(polyline);
//                                routeList.addAll(decodelist);
//                                end = step.getEndLocation();
//                                routeList.add(new LatLng(end.getLat(), end.getLng()));
//
//                            }
//                        }
//                        if(routeList.size() > 0) {
//                            PolylineOptions polyline = new PolylineOptions()
//                                    .width(10)
//                                    .color(Color.RED);
//                            for(int i = 0; i < routeList.size(); i++) {
//                                polyline.add(routeList.get(i));
//                            }
//                            mMap.addPolyline(polyline);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<aston.cs3mdd.pubgolf.map.model.Directions> call, Throwable t) {
//
//                    }
//                });
//            }
//        });
    }



}