package aston.cs3mdd.pubgolf.ui.map;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationRequest;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.maps.android.PolyUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import aston.cs3mdd.pubgolf.R;
import aston.cs3mdd.pubgolf.ui.map.model.Directions;
import aston.cs3mdd.pubgolf.ui.map.model.EndLocation;
import aston.cs3mdd.pubgolf.ui.map.model.Route;
import aston.cs3mdd.pubgolf.ui.map.model.StartLocation;
import aston.cs3mdd.pubgolf.ui.map.model.Step;
import aston.cs3mdd.pubgolf.ui.map.models.Restaurant;
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
    Restaurant restaurant;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private String mParam1;
    private String mParam2;
    private String mParam3;

    ViewPager2 viewPager;

    public SelectedPubFragment() {
        // Required empty public constructor
    }

    public static SelectedPubFragment newInstance(String param1, String param2, String param3) {
        SelectedPubFragment fragment = new SelectedPubFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
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
        tvSelectedPub.setText(mParam1);

        TextView tvDistance = view.findViewById(R.id.tvDistance);

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
                    String placeLat = mParam2;
                    String placeLng = mParam3;
                    String destination = placeLat + "," + placeLng;
                    String mode = "walking";
                    String key = getResources().getString(R.string.API_KEY);

                    Call<Directions> call = APICall.getDirections(origin, destination, mode, key);
                    Log.i("AJB", call.toString());

                    call.enqueue(new Callback<Directions>() {
                        @Override
                        public void onResponse(Call<Directions> call, Response<Directions> response) {
                            ArrayList<LatLng> routeList = new ArrayList<LatLng>();
                            Route route = response.body().getRoutes().get(0);
                            if (response.body().getRoutes().size() > 0) {
                                List<Step> steps = route.getLegs().get(0).getSteps();
                                Step step;
                                StartLocation start;
                                EndLocation end;
                                String polyline;
                                for (int i = 0; i < steps.size(); i++) {
                                    step = steps.get(i);
                                    start = step.getStartLocation();
                                    routeList.add((new LatLng(start.getLat(), start.getLng())));
                                    polyline = step.getPolyline().getPoints();
                                    List<LatLng> decodelist = PolyUtil.decode(polyline);
                                    routeList.addAll(decodelist);
                                    end = step.getEndLocation();
                                    routeList.add(new LatLng(end.getLat(), end.getLng()));

                                }
                            }
                            if (routeList.size() > 0) {
                                PolylineOptions polyline = new PolylineOptions()
                                        .width(10)
                                        .color(Color.RED);
                                for (int i = 0; i < routeList.size(); i++) {
                                    polyline.add(routeList.get(i));
                                }
                                LatLng userLocation = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                                LatLng rLocation = new LatLng(Double.valueOf(mParam2), Double.valueOf(mParam3));
                                mMap.addMarker(new MarkerOptions()
                                        .position(userLocation)
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                                        .title("Your Location"));
                                mMap.addMarker(new MarkerOptions().position(rLocation).title(mParam1));
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
                                mMap.addPolyline(polyline);
                            }
                            String distance = response.body().getRoutes().get(0).getLegs().get(0).getDistance().getText();
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
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

}