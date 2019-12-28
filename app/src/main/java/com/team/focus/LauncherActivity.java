package com.team.focus;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LauncherActivity extends AppCompatActivity {

    private Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        if (!checkPermission()) {
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            Toast.makeText(this, "Please turn usage access permission for FOCUS",
                    Toast.LENGTH_LONG).show();
            startActivityForResult(intent, 0);
        } else {
            startNextActivity();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (!checkPermission()) {
            Toast.makeText(this, "Usage access permission is critical to FOCUS." +
                            "Without it, FOCUS is not able to work",
                    Toast.LENGTH_LONG).show();
            finish();
        } else {
            startNextActivity();
        }
    }

    private void startNextActivity() {
        SharedPreferences sharedPreferences = getSharedPreferences("FOCUS", Context.MODE_PRIVATE);
        boolean didUserLogin = sharedPreferences.getBoolean("didUserLogin", false);

        final Intent nextActivity = didUserLogin ? new Intent(this, MainActivity.class) :
                new Intent(this, LoginActivity.class);
        if (didUserLogin) {
            String username = sharedPreferences.getString("username", "");
            String welcome = getString(R.string.welcome_back) + username;
            Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
            startActivity(nextActivity);
            finish();
        }

        setContentView(R.layout.splash_screen);
        final Button start = findViewById(R.id.start_btn);
        final TextView term = findViewById(R.id.terms);
        final CheckBox checkBox = findViewById(R.id.checkBox3);

        term.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Term of use");
                builder.setMessage(R.string.terms);
                builder.setPositiveButton("Accept",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                checkBox.setChecked(true);
                                dialog.dismiss();
                            }
                        });
                builder.show();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    activity.startActivity(nextActivity);
                    activity.finish();
                } else {
                    Toast.makeText(activity, "Please review Terms of use", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkPermission() {
        try {
            PackageManager packageManager = getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
            AppOpsManager appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
            int mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, applicationInfo.uid, applicationInfo.packageName);
            return (mode == AppOpsManager.MODE_ALLOWED);

        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
