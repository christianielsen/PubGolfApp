package aston.cs3mdd.pubgolf.ui.map;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import aston.cs3mdd.pubgolf.databinding.FragmentRestaurantTabBinding;

public class RestaurantTab extends Fragment {

    private FragmentRestaurantTabBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRestaurantTabBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

}