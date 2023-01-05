package aston.cs3mdd.pubgolf.ui.dashboard;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import aston.cs3mdd.pubgolf.R;
import aston.cs3mdd.pubgolf.databinding.FragmentDashboardBinding;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashboardFragment extends Fragment implements OnMapReadyCallback {

    private Location mCurrentLocation;
    private FragmentDashboardBinding binding;
    private GoogleMap mMap;

    Button btLocation, btRestaurant;
    TextView tvLatitude, tvLongitude, textview2;
    FusedLocationProviderClient client;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        supportMapFragment.getMapAsync(this);
        String apiKey = getActivity().getResources().getString(R.string.API_KEY);
        Places.initialize(getActivity().getApplicationContext(), apiKey);


        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ADDRESS, Place.Field.NAME, Place.Field.LAT_LNG));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NotNull Place place) {
                mMap.addMarker(new MarkerOptions().position(place.getLatLng()));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 15));

            }

            @Override
            public void onError(@NotNull Status status) {
            }
        });

        textview2 = root.findViewById(R.id.textView2);
        btRestaurant = root.findViewById(R.id.btRestaurant);
        btLocation = root.findViewById(R.id.btLocation);
        tvLatitude = root.findViewById(R.id.tvLatitude);
        tvLongitude = root.findViewById(R.id.tvLongitude);

        client = LocationServices.getFusedLocationProviderClient(getActivity());

        btLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // check condition
                if (ContextCompat.checkSelfPermission(
                        getActivity(),
                        Manifest.permission
                                .ACCESS_FINE_LOCATION)
                        == PackageManager
                        .PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(
                        getActivity(),
                        Manifest.permission
                                .ACCESS_COARSE_LOCATION)
                        == PackageManager
                        .PERMISSION_GRANTED) {
                    // When permission is granted
                    // Call method
                    getCurrentLocation();
                } else {
                    // When permission is not granted
                    // Call method
                    requestPermissions(
                            new String[]{
                                    Manifest.permission
                                            .ACCESS_FINE_LOCATION,
                                    Manifest.permission
                                            .ACCESS_COARSE_LOCATION},
                            100);
                }

            }
        });

        btRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Retrofit Builder
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://maps.googleapis.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                //Instance for interface
                MyAPICall myAPICall = retrofit.create(MyAPICall.class);

                String loc = ",";
                String radius = "5000";
                String type = "restaurant";
                String keyword = "bar,restaurant";
                String key = getActivity().getResources().getString(R.string.API_KEY);

                Call<ResultsItem> call = myAPICall.getData(loc, radius, type, keyword, key);
                Log.i("AJB", call.toString());

                call.enqueue(new Callback<ResultsItem>() {
                    @Override
                    public void onResponse(Call<ResultsItem> call, Response<ResultsItem> response) {
                        //Checking for the response

                        if (response.code() != 200) {
                            textview2.setText("No connection");
                            return;
                        }

                        if (response.isSuccessful()) {
                            //Loop through results
                            for (int i = 0; i < response.body().getResults().size(); i++) {
                                //Get LatLng and place name of each result
                                Double lat = response.body().getResults().get(i).getGeometry().getLocation().getLat();
                                Double lng = response.body().getResults().get(i).getGeometry().getLocation().getLng();
                                String rName = response.body().getResults().get(i).getName();
                                Log.i("AJB", lat.toString() + lng.toString());
                                //Place markers with title
                                LatLng rLocation = new LatLng(lat, lng);
                                mMap.addMarker(new MarkerOptions().position(rLocation).title(rName));

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultsItem> call, Throwable t) {
                        textview2.append(t.toString());
                    }
                });


            }
        });

        return root;
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
        // Check condition
        if (requestCode == 100 && (grantResults.length > 0)
                && (grantResults[0] + grantResults[1]
                == PackageManager.PERMISSION_GRANTED)) {
            // When permission are granted
            // Call  method
            getCurrentLocation();
        } else {
            // When permission are denied
            // Display toast
            Toast
                    .makeText(getActivity(),
                            "Permission denied",
                            Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        // Initialize Location manager
        LocationManager locationManager
                = (LocationManager) getActivity()
                .getSystemService(
                        Context.LOCATION_SERVICE);
        // Check condition
        if (locationManager.isProviderEnabled(
                LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER)) {
            // When location service is enabled
            // Get last location
            client.getLastLocation().addOnCompleteListener(
                    new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(
                                @NonNull Task<Location> task) {

                            // Initialize location
                            Location location
                                    = task.getResult();
                            // Check condition
                            if (location != null) {
                                // When location result is not
                                // null set latitude
                                tvLatitude.setText(
                                        String.valueOf(
                                                location
                                                        .getLatitude()));
                                // set longitude
                                tvLongitude.setText(
                                        String.valueOf(
                                                location
                                                        .getLongitude()));
                                LatLng dLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                mMap.addMarker(new MarkerOptions().position(dLocation));
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(dLocation, 15));

                            } else {
                                // When location result is null
                                // initialize location request
                                LocationRequest locationRequest
                                        = new LocationRequest()
                                        .setPriority(
                                                LocationRequest
                                                        .PRIORITY_HIGH_ACCURACY)
                                        .setInterval(10000)
                                        .setFastestInterval(
                                                1000)
                                        .setNumUpdates(1);

                                // Initialize location call back
                                LocationCallback
                                        locationCallback
                                        = new LocationCallback() {
                                    @Override
                                    public void
                                    onLocationResult(
                                            LocationResult
                                                    locationResult) {
                                        // Initialize
                                        // location
                                        Location location1
                                                = locationResult
                                                .getLastLocation();
                                        // Set latitude
                                        tvLatitude.setText(
                                                String.valueOf(
                                                        location1
                                                                .getLatitude()));
                                        // Set longitude
                                        tvLongitude.setText(
                                                String.valueOf(
                                                        location1
                                                                .getLongitude()));
                                    }
                                };

                                // Request location updates
                                client.requestLocationUpdates(
                                        locationRequest,
                                        locationCallback,
                                        Looper.myLooper());
                            }
                        }
                    });
        } else {
            // When location service is not enabled
            // open location setting
            startActivity(
                    new Intent(
                            Settings
                                    .ACTION_LOCATION_SOURCE_SETTINGS)
                            .setFlags(
                                    Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
    }

}