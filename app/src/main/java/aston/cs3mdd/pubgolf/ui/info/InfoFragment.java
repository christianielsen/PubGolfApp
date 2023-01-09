package aston.cs3mdd.pubgolf.ui.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import aston.cs3mdd.pubgolf.R;
import aston.cs3mdd.pubgolf.databinding.FragmentInformationBinding;

public class InfoFragment extends Fragment {

    private FragmentInformationBinding binding;
    TextView title, body, title1, body1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentInformationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        title = root.findViewById(R.id.text_title);
        title1 = root.findViewById(R.id.text_title1);
        body = root.findViewById(R.id.text_information);
        body1 = root.findViewById(R.id.text_information1);

        title.setText("Pub Golf");
        body.setText("Pub Golf is a drinking activity to play with friends. " +
                "The concept is simple, there will be 9 holes in total which will be pubs in this case. " +
                "Each hole will have a drink, set by the group or the leader. " +
                "The par for the hole is also set, the number set will correspond to how many sips each player takes " +
                "to finish their drink. " +
                "The score is how many sips the player actually takes. " +
                "A rule is something the group has to follow while on that hole. " +
                "Clicking save will save the data inserted into the scorecard. " +
                "The Total for score and par will also be calculated so each player can see how " +
                "well they did.");

        title1.setText("Map");
        body1.setText("In the Map tab, there are two buttons, location and finding restaurants. " +
                "Once the player has made their scorecard, they can then get their location which will display " +
                "a marker to show where they are. Once the location has been set, the user can then find the nearest " +
                "pubs to them. " +
                "The pubs will be marked with a marker on the map to show where they are. " +
                "Clicking on a marker will also show the name of the pub. " +
                "There will also be a list of Pubs in the pub tab which will show the name, address, and rating of " +
                "each pub nearby.");




        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}