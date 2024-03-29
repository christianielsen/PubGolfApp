package aston.cs3mdd.pubgolf.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.maps.MapView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import aston.cs3mdd.pubgolf.R;
import aston.cs3mdd.pubgolf.databinding.FragmentMapBinding;

/*
* Top level fragment for MapTab and PubsTab
* Created with the help of an answer on a stackoverflow question
* https://stackoverflow.com/a/53959182/20435153
* */
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

    /*
    * Two tabs, map and pubs tab using ViewPager to show their corresponding fragments
    * */
    @Override
    public void onStart() {
        super.onStart();
        TabPagerAdapter tabPager = new TabPagerAdapter(getActivity());

        viewPager = getActivity().findViewById(R.id.view_pager);
        viewPager.setAdapter(tabPager);
        //Remove swiping feature since it activates while trying to navigate the map
        viewPager.setUserInputEnabled(false);

        tabLayout = getActivity().findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.select()).attach();
        //Set tab names
        tabLayout.getTabAt(0).setText("Map");
        tabLayout.getTabAt(1).setText("Pubs");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //Tab adapter used to navigate between the two tabs
    public class TabPagerAdapter extends FragmentStateAdapter {

        public TabPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new MapTab();
                case 1:
                    return new PubsTab();
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