package com.team.focus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.team.focus.data.model.SharedPreferenceAccessUtils;
import com.team.focus.ui.utils.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class MainActivity extends AppCompatActivity {

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
    }

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

    public void btnNotification(View view) {
        boolean prev = SharedPreferenceAccessUtils.updateNotification(view.getContext());
        String toast = prev ? "Turn off notification" : "Turn on notification";
        Toast.makeText(view.getContext(), toast, Toast.LENGTH_SHORT).show();
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment(v.getContext(),
                v.getId() == R.id.start_time);
        newFragment.show(getSupportFragmentManager(), "timePicker");

        final TextView textView = (TextView) findViewById(v.getId());
        updateTextField.updateText(textView);
    }

}
