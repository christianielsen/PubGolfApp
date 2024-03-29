package aston.cs3mdd.pubgolf.ui.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.maps.android.PolyUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import aston.cs3mdd.pubgolf.R;
import aston.cs3mdd.pubgolf.ui.map.directionsmodels.Directions;
import aston.cs3mdd.pubgolf.ui.map.directionsmodels.EndLocation;
import aston.cs3mdd.pubgolf.ui.map.directionsmodels.Route;
import aston.cs3mdd.pubgolf.ui.map.directionsmodels.StartLocation;
import aston.cs3mdd.pubgolf.ui.map.directionsmodels.Step;
import aston.cs3mdd.pubgolf.ui.map.placemodels.Restaurant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SelectedPubFragment extends Fragment implements OnMapReadyCallback, Serializable {

    private FusedLocationProviderClient fusedLocationClient;
    private Location mCurrentLocation;
    Button btTravel, btBack;
    private GoogleMap mMap;
    private static final String ARG_rName = "name";
    private static final String ARG_rLat = "lat";
    private static final String ARG_rLng = "lng";
    private String rName;
    private String rLat;
    private String rLng;


    public SelectedPubFragment() {
        // Required empty public constructor
    }

    public static SelectedPubFragment newInstance(String rName, String rLat, String rLng) {
        SelectedPubFragment fragment = new SelectedPubFragment();
        Bundle args = new Bundle();
        args.putString(ARG_rName, rName);
        args.putString(ARG_rLat, rLat);
        args.putString(ARG_rLng, rLng);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            rName = getArguments().getString(ARG_rName);
            rLat = getArguments().getString(ARG_rLat);
            rLng = getArguments().getString(ARG_rLng);
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        CancellationTokenSource cancellationToken = new CancellationTokenSource();
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cancellationToken.getToken()).addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    mCurrentLocation = location;
                } else {
                    Toast.makeText(getActivity(), "No location", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_selected_pub, container, false);

        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map_selected);

        supportMapFragment.getMapAsync(this);

        TextView tvSelectedPub = view.findViewById(R.id.tvSelectedPub);
        tvSelectedPub.setText(rName);

        TextView tvDistance = view.findViewById(R.id.tvDistance);
        TextView tvDuration = view.findViewById(R.id.tvDuration);

        btTravel = view.findViewById(R.id.btTravel);
        btTravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentLocation != null) {
                    //Retrofit Builder
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://maps.googleapis.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    //Instance for interface
                    APICall APICall = retrofit.create(APICall.class);

                    String locLat = String.valueOf(mCurrentLocation.getLatitude());
                    String locLng = String.valueOf(mCurrentLocation.getLongitude());
                    String origin = locLat + "," + locLng;
                    String placeLat = rLat;
                    String placeLng = rLng;
                    String destination = placeLat + "," + placeLng;
                    String mode = "walking";
                    String key = getResources().getString(R.string.API_KEY);

                    Call<Directions> call = APICall.getDirections(origin, destination, mode, key);
                    Log.i("AJB", call.toString());

                    call.enqueue(new Callback<Directions>() {
                        @Override
                        public void onResponse(Call<Directions> call, Response<Directions> response) {
                            /*
                             * Polyline drawer from https://www.youtube.com/watch?v=xl0GwkLNpNI&list=PLgCYzUzKIBE-SZUrVOsbYMzH7tPigT3gi&index=20
                             * Modified for my project
                             * */
                            for (Route route : response.body().getRoutes()) {
                                //Decode the polyline using Android Maps Util's PolyUtil decoder
                                ArrayList<LatLng> decodedPath = (ArrayList<LatLng>) PolyUtil.decode(route.getOverviewPolyline().getPoints());
                                ArrayList<LatLng> newDecodedPath = new ArrayList<>();

                                for (LatLng latLng : decodedPath) {
                                    newDecodedPath.add(new LatLng(latLng.latitude, latLng.longitude));
                                }
                                //Draw polyline on map
                                PolylineOptions polyline = new PolylineOptions();
                                polyline.addAll(newDecodedPath);
                                polyline.color(Color.RED);
                                polyline.width(10);
                                mMap.addPolyline(polyline);
                                //Place map markers on user and place locations
                                LatLng userLocation = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                                LatLng rLocation = new LatLng(Double.valueOf(rLat), Double.valueOf(rLng));
                                mMap.addMarker(new MarkerOptions()
                                        .position(userLocation)
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                                        .title("Your Location"));
                                mMap.addMarker(new MarkerOptions().position(rLocation).title(rName));
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
                            }
                            String duration = response.body().getRoutes().get(0).getLegs().get(0).getDuration().getText();
                            String distance = response.body().getRoutes().get(0).getLegs().get(0).getDistance().getText();
                            tvDuration.setText(duration);
                            tvDistance.setText(distance);
                        }

                        @Override
                        public void onFailure(Call<Directions> call, Throwable t) {

                        }
                    });
                } else {
                    Snackbar.make(view.findViewById(R.id.google_map_selected), "No location yet, try again in a couple of seconds", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        //Remove the fragment on click to go back to recyclerview
        btBack = view.findViewById(R.id.btBack);

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().remove(SelectedPubFragment.this).commit();
            }
        });
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

}