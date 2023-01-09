package aston.cs3mdd.pubgolf.ui.map;

import static aston.cs3mdd.pubgolf.ui.map.MapTab.restaurantList;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Handler;

import aston.cs3mdd.pubgolf.R;
import aston.cs3mdd.pubgolf.databinding.FragmentRestaurantTabBinding;
import aston.cs3mdd.pubgolf.ui.map.models.Restaurant;

public class RestaurantTab extends Fragment {

    private FragmentRestaurantTabBinding binding;

    SearchView searchbar;
    RestaurantAdapter adapter, adapter1;
    ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRestaurantTabBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        searchbar = root.findViewById(R.id.searchbar);
        lv = root.findViewById(R.id.listview);

        adapter = new RestaurantAdapter(restaurantList, getActivity());
        lv.setAdapter(adapter);

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
            adapter1 = new RestaurantAdapter(filteredList, getActivity());
            lv.setAdapter(adapter1);
        }
    }

}