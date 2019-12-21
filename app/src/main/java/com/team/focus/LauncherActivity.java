package com.team.focus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.team.focus.ui.login.LoginActivity;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("FOCUS", Context.MODE_PRIVATE);
        boolean didUserLogin = sharedPreferences.getBoolean("didUserLogin", false);

        Intent nextActivity = didUserLogin ? new Intent(this, MainActivity.class) :
                new Intent(this, LoginActivity.class);
        if (didUserLogin) {
            String username = sharedPreferences.getString("username", null);
            StringBuilder welcome = new StringBuilder("Welcome back, ");
            welcome.append(username);
            Toast.makeText(getApplicationContext(), welcome.toString(), Toast.LENGTH_LONG).show();
        }

        startActivity(nextActivity);
        setResult(Activity.RESULT_OK);
        finish();
    }
}
