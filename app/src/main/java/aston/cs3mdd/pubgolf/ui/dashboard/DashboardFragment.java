package aston.cs3mdd.pubgolf.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import aston.cs3mdd.pubgolf.R;
import aston.cs3mdd.pubgolf.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment implements OnMapReadyCallback {

    private FragmentDashboardBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        SupportMapFragment supportMapFragment = (SupportMapFragment)
//                getChildFragmentManager().findFragmentById(R.id.google_map);
//
//        supportMapFragment.getMapAsync(this);
        String apiKey = "AIzaSyAE1O94eM9xi9_NR9jEZwm9xuKWUFyWCOU";
        Places.initialize(getActivity().getApplicationContext(), apiKey);


        // Get a reference to the TextView
        TextView text = (TextView) root.findViewById(R.id.textview_first);
// Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);
// Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

// Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NotNull Place place) {
                text.setText("Place: " + place.getName() + ", " + place.getId());
            }

            @Override
            public void onError(@NotNull Status status) {
                text.setText("An error occurred: " + status);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // When map is loaded
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                // When clicked on map
                // Initialize marker options
                MarkerOptions markerOptions = new MarkerOptions();
                // Set position of marker
                markerOptions.position(latLng);
                // Set title of marker
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                // Remove all marker
                googleMap.clear();
                // Animating to zoom the marker
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                // Add marker on map
                googleMap.addMarker(markerOptions);

            }
        });
    }
}