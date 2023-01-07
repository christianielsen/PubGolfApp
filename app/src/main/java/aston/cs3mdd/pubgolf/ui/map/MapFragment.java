package aston.cs3mdd.pubgolf.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import aston.cs3mdd.pubgolf.R;
import aston.cs3mdd.pubgolf.databinding.FragmentMapBinding;

public class MapFragment extends Fragment {

    private FragmentMapBinding binding;
    ViewPager2 viewPager;
    TabLayout tabLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        MyTabPagerAdapter tabPager = new MyTabPagerAdapter(getActivity());

        viewPager = getActivity().findViewById(R.id.view_pager);
        viewPager.setAdapter(tabPager);
        viewPager.setUserInputEnabled(false);

        tabLayout = getActivity().findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.select()).attach();
        tabLayout.getTabAt(0).setText("Map");
        tabLayout.getTabAt(1).setText("Restaurants");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public class MyTabPagerAdapter extends FragmentStateAdapter {

        public MyTabPagerAdapter(@NonNull FragmentActivity fa) {
            super(fa);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new MapTab();
                case 1:
                    return new RestaurantTab();
                default:
                    return new MapTab();
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}