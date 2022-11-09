package aston.cs3mdd.pubgolf.ui.home;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.w3c.dom.Text;

import aston.cs3mdd.pubgolf.R;
import aston.cs3mdd.pubgolf.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        showData();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void showData() {
        View root = binding.getRoot();
        TextView d1 = (TextView) root.findViewById(R.id.drink1);
        TextView d2 = (TextView) root.findViewById(R.id.drink2);
        TextView d3 = (TextView) root.findViewById(R.id.drink3);
        TextView d4 = (TextView) root.findViewById(R.id.drink4);
        TextView d5 = (TextView) root.findViewById(R.id.drink5);
        TextView d6 = (TextView) root.findViewById(R.id.drink6);
        TextView d7 = (TextView) root.findViewById(R.id.drink7);
        TextView d8 = (TextView) root.findViewById(R.id.drink8);
        TextView d9 = (TextView) root.findViewById(R.id.drink9);

        TextView p1 = (TextView) root.findViewById(R.id.par1);
        TextView p2 = (TextView) root.findViewById(R.id.par2);
        TextView p3 = (TextView) root.findViewById(R.id.par3);
        TextView p4 = (TextView) root.findViewById(R.id.par4);
        TextView p5 = (TextView) root.findViewById(R.id.par5);
        TextView p6 = (TextView) root.findViewById(R.id.par6);
        TextView p7 = (TextView) root.findViewById(R.id.par7);
        TextView p8 = (TextView) root.findViewById(R.id.par8);
        TextView p9 = (TextView) root.findViewById(R.id.par9);

        TextView s1 = (TextView) root.findViewById(R.id.score1);
        TextView s2 = (TextView) root.findViewById(R.id.score2);
        TextView s3 = (TextView) root.findViewById(R.id.score3);
        TextView s4 = (TextView) root.findViewById(R.id.score4);
        TextView s5 = (TextView) root.findViewById(R.id.score5);
        TextView s6 = (TextView) root.findViewById(R.id.score6);
        TextView s7 = (TextView) root.findViewById(R.id.score7);
        TextView s8 = (TextView) root.findViewById(R.id.score8);
        TextView s9 = (TextView) root.findViewById(R.id.score9);

        TextView r1 = (TextView) root.findViewById(R.id.rule1);
        TextView r2 = (TextView) root.findViewById(R.id.rule2);
        TextView r3 = (TextView) root.findViewById(R.id.rule3);
        TextView r4 = (TextView) root.findViewById(R.id.rule4);
        TextView r5 = (TextView) root.findViewById(R.id.rule5);
        TextView r6 = (TextView) root.findViewById(R.id.rule6);
        TextView r7 = (TextView) root.findViewById(R.id.rule7);
        TextView r8 = (TextView) root.findViewById(R.id.rule8);
        TextView r9 = (TextView) root.findViewById(R.id.rule9);

        TextView pTotal = (TextView) root.findViewById(R.id.parTotal);
        TextView sTotal = (TextView) root.findViewById(R.id.scoreTotal);

        SharedPreferences sp = getActivity().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);

        String drink1 = sp.getString("drink1", "");
        String drink2 = sp.getString("drink2", "");
        String drink3 = sp.getString("drink3", "");
        String drink4 = sp.getString("drink4", "");
        String drink5 = sp.getString("drink5", "");
        String drink6 = sp.getString("drink6", "");
        String drink7 = sp.getString("drink7", "");
        String drink8 = sp.getString("drink8", "");
        String drink9 = sp.getString("drink9", "");

        String par1 = sp.getString("par1", "");
        String par2 = sp.getString("par2", "");
        String par3 = sp.getString("par3", "");
        String par4 = sp.getString("par4", "");
        String par5 = sp.getString("par5", "");
        String par6 = sp.getString("par6", "");
        String par7 = sp.getString("par7", "");
        String par8 = sp.getString("par8", "");
        String par9 = sp.getString("par9", "");

        String score1 = sp.getString("score1", "");
        String score2 = sp.getString("score2", "");
        String score3 = sp.getString("score3", "");
        String score4 = sp.getString("score4", "");
        String score5 = sp.getString("score5", "");
        String score6 = sp.getString("score6", "");
        String score7 = sp.getString("score7", "");
        String score8 = sp.getString("score8", "");
        String score9 = sp.getString("score9", "");

        String rule1 = sp.getString("rule1", "");
        String rule2 = sp.getString("rule2", "");
        String rule3 = sp.getString("rule3", "");
        String rule4 = sp.getString("rule4", "");
        String rule5 = sp.getString("rule5", "");
        String rule6 = sp.getString("rule6", "");
        String rule7 = sp.getString("rule7", "");
        String rule8 = sp.getString("rule8", "");
        String rule9 = sp.getString("rule9", "");

        String parTotal = sp.getString("parTotal", "");
        String scoreTotal = sp.getString("scoreTotal", "");

        d1.setText(drink1);
        d2.setText(drink2);
        d3.setText(drink3);
        d4.setText(drink4);
        d5.setText(drink5);
        d6.setText(drink6);
        d7.setText(drink7);
        d8.setText(drink8);
        d9.setText(drink9);

        p1.setText(par1);
        p2.setText(par2);
        p3.setText(par3);
        p4.setText(par4);
        p5.setText(par5);
        p6.setText(par6);
        p7.setText(par7);
        p8.setText(par8);
        p9.setText(par9);

        s1.setText(score1);
        s2.setText(score2);
        s3.setText(score3);
        s4.setText(score4);
        s5.setText(score5);
        s6.setText(score6);
        s7.setText(score7);
        s8.setText(score8);
        s9.setText(score9);

        r1.setText(rule1);
        r2.setText(rule2);
        r3.setText(rule3);
        r4.setText(rule4);
        r5.setText(rule5);
        r6.setText(rule6);
        r7.setText(rule7);
        r8.setText(rule8);
        r9.setText(rule9);

        pTotal.setText(parTotal);
        sTotal.setText(scoreTotal);
    }
}