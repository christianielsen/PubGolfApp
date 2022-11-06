package aston.cs3mdd.pubgolf;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import aston.cs3mdd.pubgolf.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    EditText drink1, par1;
    Button button;
    SharedPreferences sp;
    String drink1Str, par1Str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        drink1 = findViewById(R.id.drink1);
        par1 = findViewById(R.id.par1);
        button = findViewById(R.id.button);

        sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drink1Str = drink1.getText().toString();
                par1Str = par1.getText().toString();

                SharedPreferences.Editor editor = sp.edit();

                editor.putString("drink", drink1Str);
                editor.putString("par1", par1Str);
                editor.commit();
                Toast.makeText(MainActivity.this, "Information Saved.", Toast.LENGTH_LONG).show();
            }
        });
    }

}