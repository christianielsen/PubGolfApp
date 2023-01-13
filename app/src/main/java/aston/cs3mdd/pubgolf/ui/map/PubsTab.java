package aston.cs3mdd.pubgolf.ui.map;

import static aston.cs3mdd.pubgolf.ui.map.MapTab.restaurantList;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import aston.cs3mdd.pubgolf.R;
import aston.cs3mdd.pubgolf.databinding.FragmentPubsTabBinding;
import aston.cs3mdd.pubgolf.ui.map.models.Restaurant;

public class PubsTab extends Fragment implements RestaurantAdapter.RestaurantClickListener {

    private FragmentPubsTabBinding binding;

    private Location mCurrentLocation;

    SearchView searchbar;
    RestaurantAdapter adapter, adapter1;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPubsTabBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        searchbar = root.findViewById(R.id.searchbar);
        recyclerView = root.findViewById(R.id.recyclerview);

        buildRecyclerView();

        searchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

        return root;
    }

    private void buildRecyclerView() {
        adapter = new RestaurantAdapter(restaurantList, getActivity(), this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void filter(String text) {
        ArrayList<Restaurant> filteredList = new ArrayList<Restaurant>();
        text = text.toLowerCase();
        for (Restaurant item : restaurantList) {
            if (item.getName().toLowerCase().contains(text) ||
                    item.getAddress().toLowerCase().contains(text) ||
                    item.getRating().contains(text)) {
                filteredList.add(item);
                Log.i("AAAAA", String.valueOf(filteredList.size()));
            }
        }
        if (filteredList.isEmpty()) {
            //No data
        } else {
            adapter.filterList(filteredList);
        }
    }

    @Override
    public void selectedRestaurant(Restaurant restaurant) {
//        Toast.makeText(getActivity(), "Selected Restaurant" + restaurant.getName(), Toast.LENGTH_LONG).show();
//    startActivity(new Intent(getActivity(), SelectedPubActivity.class).putExtra("data", restaurant));
        Fragment fragment = SelectedPubFragment.newInstance(restaurant.getName());
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.pubsTab, fragment, "selected_pub_fragment");
//        transaction.hide(getActivity().getSupportFragmentManager().findFragmentByTag("pubs_tab_fragment"));
//        transaction.add(R.id.rec, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}