package aston.cs3mdd.pubgolf.ui.map;

import static aston.cs3mdd.pubgolf.ui.map.MapTab.restaurantList;

import android.location.Location;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import aston.cs3mdd.pubgolf.R;
import aston.cs3mdd.pubgolf.databinding.FragmentPubsTabBinding;
import aston.cs3mdd.pubgolf.ui.map.placemodels.Restaurant;

/*
 * Pubs tab to show list of pubs in a recyclerview
 * */
public class PubsTab extends Fragment implements RestaurantAdapter.RestaurantClickListener {

    private FragmentPubsTabBinding binding;
    SearchView searchbar;
    RestaurantAdapter adapter;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPubsTabBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        searchbar = root.findViewById(R.id.searchbar);
        recyclerView = root.findViewById(R.id.recyclerview);

        initRecyclerView();

        //Searchbar to search through the arraylist
        searchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            //When the user adds a letter, filter through
            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

        return root;
    }

    //Initialise the recyclerview
    private void initRecyclerView() {
        adapter = new RestaurantAdapter(restaurantList, getActivity(), this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }


    /*
    * Filter method from https://www.geeksforgeeks.org/searchview-in-android-with-recyclerview/
    * Modified to work with project
    * */
    private void filter(String text) {
        //Temp arraylist
        ArrayList<Restaurant> filteredList = new ArrayList<Restaurant>();
        text = text.toLowerCase();
        //Loop through the arraylist using the model
        for (Restaurant item : restaurantList) {
            //Filter through all options
            if (item.getName().toLowerCase().contains(text) ||
                    item.getAddress().toLowerCase().contains(text) ||
                    item.getRating().contains(text)) {
                //Got data, add it to temp arraylist
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            //No data
        } else {
            //Update the adapter to show the data in the temporary arraylist
            adapter.filterList(filteredList);
        }
    }

    //On clicking item, create a new fragment and pass the place name and location
    @Override
    public void selectedRestaurant(Restaurant restaurant) {
        //Pass data into new fragment
        Fragment fragment = SelectedPubFragment.newInstance(restaurant.getName(), restaurant.getLat().toString(), restaurant.getLng().toString());
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.pubsTab, fragment, "selected_pub_fragment");
        transaction.addToBackStack(null);
        transaction.commit();

    }

}