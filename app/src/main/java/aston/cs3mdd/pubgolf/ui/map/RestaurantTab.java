package aston.cs3mdd.pubgolf.ui.map;

import static aston.cs3mdd.pubgolf.ui.map.MapTab.restaurantList;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import aston.cs3mdd.pubgolf.R;
import aston.cs3mdd.pubgolf.databinding.FragmentRestaurantTabBinding;

public class RestaurantTab extends Fragment {

    private FragmentRestaurantTabBinding binding;
    private ListView lv;

    SearchView searchView;
    private ArrayAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRestaurantTabBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        searchView = root.findViewById(R.id.searchbar);

        lv = root.findViewById(R.id.listview);
        //String and int array for ListAdapter
//        String[] from = {"name", "address", "rating", "open"};
//        int[] to = {R.id.name, R.id.address, R.id.rating, R.id.open};
//        ListAdapter adapter = new SimpleAdapter(
//                getActivity(),
//                restaurantList,
//                R.layout.row_layout,
//                from,
//                to);

        adapter = new ArrayAdapter(getActivity(), R.layout.row_layout, restaurantList);
        lv.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                (RestaurantTab.this).adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                (RestaurantTab.this).adapter.getFilter().filter(newText);
                return false;
            }
        });

        return root;
    }

}