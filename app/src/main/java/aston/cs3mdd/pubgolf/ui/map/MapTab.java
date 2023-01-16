package aston.cs3mdd.pubgolf.ui.map;

import static com.google.android.gms.location.Priority.PRIORITY_BALANCED_POWER_ACCURACY;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.maps.android.PolyUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aston.cs3mdd.pubgolf.R;
import aston.cs3mdd.pubgolf.databinding.FragmentMapTabBinding;
import aston.cs3mdd.pubgolf.ui.map.model.EndLocation__1;
import aston.cs3mdd.pubgolf.ui.map.model.Route;
import aston.cs3mdd.pubgolf.ui.map.model.StartLocation__1;
import aston.cs3mdd.pubgolf.ui.map.model.Step;
import aston.cs3mdd.pubgolf.ui.map.models.Restaurant;
import aston.cs3mdd.pubgolf.ui.map.models.ResultsItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapTab extends Fragment implements OnMapReadyCallback {
    public static final String TAG = "AJB";
    private FusedLocationProviderClient fusedLocationClient;
    private Location mCurrentLocation;

    private boolean requestingLocationUpdates;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    private FragmentMapTabBinding binding;
    private GoogleMap mMap;

    private Button btLocation, btRestaurant;

    public static ArrayList<Restaurant> restaurantList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMapTabBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        supportMapFragment.getMapAsync(this);

        restaurantList = new ArrayList<Restaurant>();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Location permissions have not been granted");
            ActivityResultLauncher<String[]> locationPermissionRequest =
                    registerForActivityResult(new ActivityResultContracts
                                    .RequestMultiplePermissions(), result -> {
                                Boolean fineLocationGranted = null;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                    fineLocationGranted = result.getOrDefault(
                                            Manifest.permission.ACCESS_FINE_LOCATION, false);
                                } else {
                                    fineLocationGranted =
                                            result.get(Manifest.permission.ACCESS_FINE_LOCATION) != null ?
                                                    result.get(Manifest.permission.ACCESS_FINE_LOCATION) :
                                                    false;
                                }
                                Boolean coarseLocationGranted = null;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                    coarseLocationGranted = result.getOrDefault(
                                            Manifest.permission.ACCESS_COARSE_LOCATION, false);
                                } else {
                                    coarseLocationGranted =
                                            result.get(Manifest.permission.ACCESS_COARSE_LOCATION) != null ?
                                                    result.get(Manifest.permission.ACCESS_COARSE_LOCATION) :
                                                    false;
                                }
                                if (fineLocationGranted != null && fineLocationGranted) {
                                    Log.i(TAG, "Precise location access granted.");
                                    this.getLastLocation();
                                } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                    Log.i(TAG, "Only approximate location access granted.");
                                    this.getLastLocation();
                                } else {
                                    Log.i(TAG, "No location access granted.");
                                }
                            }
                    );
// Before you perform the actual permission request, check whether your app
// already has the permissions, and whether your app needs to show a permission
// rationale dialog. For more details, see Request permissions.
            locationPermissionRequest.launch(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
        } else {
            Log.i(TAG, "Location permissions already granted.");
            getLastLocation();
        }

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    Log.i(TAG, "Location Update: NO LOCATION");
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    Log.i(TAG, "Location Update: (" + location.getLatitude() +
                            ", " + location.getLongitude() + ")");
                    mCurrentLocation = location;
                    updateUILocation(location);
                }
            }
        };

        String apiKey = getActivity().getResources().getString(R.string.API_KEY);
        Places.initialize(getActivity().getApplicationContext(), apiKey);

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ADDRESS, Place.Field.NAME, Place.Field.LAT_LNG));
        autocompleteFragment.setHint("Search for a pub");
        autocompleteFragment.setTypesFilter(Arrays.asList("restaurant", "bar"));
        autocompleteFragment.setCountries("UK");

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NotNull Place place) {
                mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName()));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 15));

            }

            @Override
            public void onError(@NotNull Status status) {
            }
        });

        btLocation = root.findViewById(R.id.btLocation);

        btLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (requestingLocationUpdates == false) {
                    // starting
                    requestingLocationUpdates = true;
                    btLocation.setText("Stop Location Updates");
                    startLocationUpdates();
                    Log.i(TAG, "Updates started");
                    double lat = mCurrentLocation.getLatitude();
                    double lng = mCurrentLocation.getLongitude();
                    LatLng latlng = new LatLng(lat, lng);
                    mMap.clear();
                    mMap.addMarker(new MarkerOptions().position(latlng).title("Current Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));

                } else {
                    // stopping
                    requestingLocationUpdates = false;
                    btLocation.setText("Start Location Updates");
                    stopLocationUpdates();
                    Log.i(TAG, "Updates stopped");
                }
            }
        });


        btRestaurant = root.findViewById(R.id.btRestaurant);
        btRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Retrofit Builder
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://maps.googleapis.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                //Instance for interface
                APICall APICall = retrofit.create(APICall.class);

                String lat = String.valueOf(mCurrentLocation.getLatitude());
                String lng = String.valueOf(mCurrentLocation.getLongitude());
                String loc = lat + "," + lng;
                String radius = "2000";
                String type = "restaurant";
                String keyword = "bar,restaurant";
                String key = getActivity().getResources().getString(R.string.API_KEY);

                Call<ResultsItem> call = APICall.getData(loc, radius, type, keyword, key);
                Log.i("AJB", call.toString());

                call.enqueue(new Callback<ResultsItem>() {
                    @Override
                    public void onResponse(Call<ResultsItem> call, Response<ResultsItem> response) {
                        //Checking for the response
                        if (response.isSuccessful()) {
                            //Loop through results
                            restaurantList.clear();
                            for (int i = 0; i < response.body().getResults().size(); i++) {
                                //Get LatLng and place name of each result
                                Double lat = response.body().getResults().get(i).getGeometry().getLocation().getLat();
                                Double lng = response.body().getResults().get(i).getGeometry().getLocation().getLng();

                                String rName = response.body().getResults().get(i).getName().toString();
                                String address = response.body().getResults().get(i).getVicinity().toString();
                                String rating = response.body().getResults().get(i).getRating().toString();
                                String totalRating = response.body().getResults().get(i).getUserRatingsTotal().toString();
                                String isOpen = response.body().getResults().get(i).getOpeningHours().getOpenNow().toString();
                                if(isOpen.equals("true")) {
                                    isOpen = "Open Now";
                                } else {
                                    isOpen = "Closed Now";
                                }

                                //Place markers with title
                                LatLng rLocation = new LatLng(lat, lng);
                                mMap.addMarker(new MarkerOptions().position(rLocation).title(rName));
                                mMap.animateCamera(CameraUpdateFactory.newLatLng(rLocation));

                                restaurantList.add(new Restaurant(rName, address, rating, totalRating, lat, lng, isOpen));

                            }
                            Toast.makeText(getActivity(), "Found " + response.body().getResults().size() + " pubs", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultsItem> call, Throwable t) {
                    }
                });

            }
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopLocationUpdates();
        binding = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
    }

    public void getLastLocation() {
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
        fusedLocationClient.getCurrentLocation(PRIORITY_BALANCED_POWER_ACCURACY, null)
                //fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            //mCurrentLocation = location;
                            updateUILocation(location);
                            // Logic to handle location object
                            Log.i(TAG, "We got a location: (" + location.getLatitude() +
                                    ", " + location.getLongitude() + ")");

                        } else {
                            Log.i(TAG, "We failed to get a last location");
                        }
                    }
                });

    }

    private void startLocationUpdates() {
        if (locationRequest == null) {
            createLocationRequest();
        }
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
        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }

    protected void createLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void stopLocationUpdates() {
        Log.i(TAG, "Pause: stopping location updates");
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    private void updateUILocation(Location location) {
        this.mCurrentLocation = location;
        LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latlng).title("Current Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));

    }
}


