package com.team.focus;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.widget.Button;
import com.team.focus.ui.monitor.UsageStatsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.team.focus.data.model.SharedPreferenceAccessUtils;
import com.team.focus.ui.utils.TimePickerFragment;


public class MainActivity extends AppCompatActivity {

    private Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_overview, R.id.navigation_recommendation, R.id.navigation_account)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        b1=findViewById(R.id.button);

        b1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UsageStatsActivity.class));

            }
        });
    }

    /**
     * settings button/switch onClickerListener group
     */

    /**
     * log out button onClickListener
     * @param view
     */
    public void bntLogout(View view) {
        SharedPreferenceAccessUtils.removeLoginUser(view.getContext());
        Intent login = new Intent(view.getContext(), LoginActivity.class);
        startActivity(login);
        finish();
    }

    /**
     * push notification button toggle
     * @param view
     */
    public void btnNotification(View view) {
        boolean prev = SharedPreferenceAccessUtils.updateNotification(view.getContext());
        String toast = prev ? "Turn off notification" : "Turn on notification";
        Toast.makeText(view.getContext(), toast, Toast.LENGTH_SHORT).show();
    }

    /**
     * select active hours
     * @param v
     */
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment;
        if (v.getId() == R.id.start_time_bar) {
            newFragment = new TimePickerFragment(v.getContext(),
                    true, (TextView) v.findViewById(R.id.start_time));
        } else {
            newFragment = new TimePickerFragment(v.getContext(),
                    false, (TextView) v.findViewById(R.id.end_time));
        }
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
}
