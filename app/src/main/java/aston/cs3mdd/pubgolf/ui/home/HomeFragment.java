package aston.cs3mdd.pubgolf.ui.home;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    EditText drink1, drink2, drink3, drink4, drink5, drink6, drink7, drink8, drink9;
    EditText par1, par2, par3, par4, par5, par6, par7, par8, par9;
    EditText score1, score2, score3, score4, score5, score6, score7, score8, score9;
    EditText rule1, rule2, rule3, rule4, rule5, rule6, rule7, rule8, rule9;
    TextView parTotal, scoreTotal;
    Button btSave, btDelete;
    SharedPreferences sp;
    String drink1Str, drink2Str, drink3Str, drink4Str, drink5Str, drink6Str, drink7Str, drink8Str, drink9Str;
    String par1Str, par2Str, par3Str, par4Str, par5Str, par6Str, par7Str, par8Str, par9Str;
    String score1Str, score2Str, score3Str, score4Str, score5Str, score6Str, score7Str, score8Str, score9Str;
    String rule1Str, rule2Str, rule3Str, rule4Str, rule5Str, rule6Str, rule7Str, rule8Str, rule9Str;
    String parTotalStr, scoreTotalStr;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        showData();

        findData();

        btSave = root.findViewById(R.id.btSave);

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        btDelete = root.findViewById(R.id.btDelete);

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearData();
            }
        });

        return root;
    }

    public void onPause() {
        super.onPause();
        saveData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        saveData();
    }

    //Get the data from the SharedPreference and change the TextView to set the data
    private void showData() {
        View root = binding.getRoot();
        TextView d1 = root.findViewById(R.id.drink1);
        TextView d2 = root.findViewById(R.id.drink2);
        TextView d3 = root.findViewById(R.id.drink3);
        TextView d4 = root.findViewById(R.id.drink4);
        TextView d5 = root.findViewById(R.id.drink5);
        TextView d6 = root.findViewById(R.id.drink6);
        TextView d7 = root.findViewById(R.id.drink7);
        TextView d8 = root.findViewById(R.id.drink8);
        TextView d9 = root.findViewById(R.id.drink9);

        TextView p1 = root.findViewById(R.id.par1);
        TextView p2 = root.findViewById(R.id.par2);
        TextView p3 = root.findViewById(R.id.par3);
        TextView p4 = root.findViewById(R.id.par4);
        TextView p5 = root.findViewById(R.id.par5);
        TextView p6 = root.findViewById(R.id.par6);
        TextView p7 = root.findViewById(R.id.par7);
        TextView p8 = root.findViewById(R.id.par8);
        TextView p9 = root.findViewById(R.id.par9);

        TextView s1 = root.findViewById(R.id.score1);
        TextView s2 = root.findViewById(R.id.score2);
        TextView s3 = root.findViewById(R.id.score3);
        TextView s4 = root.findViewById(R.id.score4);
        TextView s5 = root.findViewById(R.id.score5);
        TextView s6 = root.findViewById(R.id.score6);
        TextView s7 = root.findViewById(R.id.score7);
        TextView s8 = root.findViewById(R.id.score8);
        TextView s9 = root.findViewById(R.id.score9);

        TextView r1 = root.findViewById(R.id.rule1);
        TextView r2 = root.findViewById(R.id.rule2);
        TextView r3 = root.findViewById(R.id.rule3);
        TextView r4 = root.findViewById(R.id.rule4);
        TextView r5 = root.findViewById(R.id.rule5);
        TextView r6 = root.findViewById(R.id.rule6);
        TextView r7 = root.findViewById(R.id.rule7);
        TextView r8 = root.findViewById(R.id.rule8);
        TextView r9 = root.findViewById(R.id.rule9);

        TextView pTotal = root.findViewById(R.id.parTotal);
        TextView sTotal = root.findViewById(R.id.scoreTotal);

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

    //Set ID for all TextViews
    private void findData() {
        View root = binding.getRoot();
        drink1 = root.findViewById(R.id.drink1);
        drink2 = root.findViewById(R.id.drink2);
        drink3 = root.findViewById(R.id.drink3);
        drink4 = root.findViewById(R.id.drink4);
        drink5 = root.findViewById(R.id.drink5);
        drink6 = root.findViewById(R.id.drink6);
        drink7 = root.findViewById(R.id.drink7);
        drink8 = root.findViewById(R.id.drink8);
        drink9 = root.findViewById(R.id.drink9);

        par1 = root.findViewById(R.id.par1);
        par2 = root.findViewById(R.id.par2);
        par3 = root.findViewById(R.id.par3);
        par4 = root.findViewById(R.id.par4);
        par5 = root.findViewById(R.id.par5);
        par6 = root.findViewById(R.id.par6);
        par7 = root.findViewById(R.id.par7);
        par8 = root.findViewById(R.id.par8);
        par9 = root.findViewById(R.id.par9);

        score1 = root.findViewById(R.id.score1);
        score2 = root.findViewById(R.id.score2);
        score3 = root.findViewById(R.id.score3);
        score4 = root.findViewById(R.id.score4);
        score5 = root.findViewById(R.id.score5);
        score6 = root.findViewById(R.id.score6);
        score7 = root.findViewById(R.id.score7);
        score8 = root.findViewById(R.id.score8);
        score9 = root.findViewById(R.id.score9);

        rule1 = root.findViewById(R.id.rule1);
        rule2 = root.findViewById(R.id.rule2);
        rule3 = root.findViewById(R.id.rule3);
        rule4 = root.findViewById(R.id.rule4);
        rule5 = root.findViewById(R.id.rule5);
        rule6 = root.findViewById(R.id.rule6);
        rule7 = root.findViewById(R.id.rule7);
        rule8 = root.findViewById(R.id.rule8);
        rule9 = root.findViewById(R.id.rule9);

        parTotal = root.findViewById(R.id.parTotal);
        scoreTotal = root.findViewById(R.id.scoreTotal);

    }

    //Save the data to the SharedPreference
    private void saveData() {
        drink1Str = drink1.getText().toString();
        drink2Str = drink2.getText().toString();
        drink3Str = drink3.getText().toString();
        drink4Str = drink4.getText().toString();
        drink5Str = drink5.getText().toString();
        drink6Str = drink6.getText().toString();
        drink7Str = drink7.getText().toString();
        drink8Str = drink8.getText().toString();
        drink9Str = drink9.getText().toString();

        par1Str = par1.getText().toString();
        par2Str = par2.getText().toString();
        par3Str = par3.getText().toString();
        par4Str = par4.getText().toString();
        par5Str = par5.getText().toString();
        par6Str = par6.getText().toString();
        par7Str = par7.getText().toString();
        par8Str = par8.getText().toString();
        par9Str = par9.getText().toString();

        score1Str = score1.getText().toString();
        score2Str = score2.getText().toString();
        score3Str = score3.getText().toString();
        score4Str = score4.getText().toString();
        score5Str = score5.getText().toString();
        score6Str = score6.getText().toString();
        score7Str = score7.getText().toString();
        score8Str = score8.getText().toString();
        score9Str = score9.getText().toString();

        rule1Str = rule1.getText().toString();
        rule2Str = rule2.getText().toString();
        rule3Str = rule3.getText().toString();
        rule4Str = rule4.getText().toString();
        rule5Str = rule5.getText().toString();
        rule6Str = rule6.getText().toString();
        rule7Str = rule7.getText().toString();
        rule8Str = rule8.getText().toString();
        rule9Str = rule9.getText().toString();

        SharedPreferences sp = getActivity().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString("drink1", drink1Str);
        editor.putString("drink2", drink2Str);
        editor.putString("drink3", drink3Str);
        editor.putString("drink4", drink4Str);
        editor.putString("drink5", drink5Str);
        editor.putString("drink6", drink6Str);
        editor.putString("drink7", drink7Str);
        editor.putString("drink8", drink8Str);
        editor.putString("drink9", drink9Str);

        editor.putString("par1", par1Str);
        editor.putString("par2", par2Str);
        editor.putString("par3", par3Str);
        editor.putString("par4", par4Str);
        editor.putString("par5", par5Str);
        editor.putString("par6", par6Str);
        editor.putString("par7", par7Str);
        editor.putString("par8", par8Str);
        editor.putString("par9", par9Str);

        editor.putString("score1", score1Str);
        editor.putString("score2", score2Str);
        editor.putString("score3", score3Str);
        editor.putString("score4", score4Str);
        editor.putString("score5", score5Str);
        editor.putString("score6", score6Str);
        editor.putString("score7", score7Str);
        editor.putString("score8", score8Str);
        editor.putString("score9", score9Str);

        editor.putString("rule1", rule1Str);
        editor.putString("rule2", rule2Str);
        editor.putString("rule3", rule3Str);
        editor.putString("rule4", rule4Str);
        editor.putString("rule5", rule5Str);
        editor.putString("rule6", rule6Str);
        editor.putString("rule7", rule7Str);
        editor.putString("rule8", rule8Str);
        editor.putString("rule9", rule9Str);

        total();
        editor.putString("parTotal", parTotalStr);
        editor.putString("scoreTotal", scoreTotalStr);

        editor.apply();
        editor.commit();
    }

    //Converts the string to an int
    private int getNumber(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    //Get the total par and score
    private void total() {
        int par1Int = getNumber(par1Str);
        int par2Int = getNumber(par2Str);
        int par3Int = getNumber(par3Str);
        int par4Int = getNumber(par4Str);
        int par5Int = getNumber(par5Str);
        int par6Int = getNumber(par6Str);
        int par7Int = getNumber(par7Str);
        int par8Int = getNumber(par8Str);
        int par9Int = getNumber(par9Str);

        int score1Int = getNumber(score1Str);
        int score2Int = getNumber(score2Str);
        int score3Int = getNumber(score3Str);
        int score4Int = getNumber(score4Str);
        int score5Int = getNumber(score5Str);
        int score6Int = getNumber(score6Str);
        int score7Int = getNumber(score7Str);
        int score8Int = getNumber(score8Str);
        int score9Int = getNumber(score9Str);

        int parSum = par1Int + par2Int + par3Int + par4Int + par5Int + par6Int + par7Int + par8Int + par9Int;
        int scoreSum = score1Int + score2Int + score3Int + score4Int + score5Int + score6Int + score7Int + score8Int + score9Int;
        parTotal.setText(Integer.toString(parSum));
        scoreTotal.setText(Integer.toString(scoreSum) + "/" + Integer.toString(parSum));

        parTotalStr = parTotal.getText().toString();
        scoreTotalStr = scoreTotal.getText().toString();
    }

    //Delete all data from the SharedPreference
    private void clearData() {
        sp = getActivity().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
        editor.commit();
    }
}