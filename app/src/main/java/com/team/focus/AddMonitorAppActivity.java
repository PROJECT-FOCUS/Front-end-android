package com.team.focus;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.team.focus.data.model.AppInfo;
import com.team.focus.data.model.InstalledApps;
import com.team.focus.ui.Adaptor.AddMonitorRecycleAdaptor;

import java.util.ArrayList;

public class AddMonitorAppActivity extends AppCompatActivity {

    private ArrayList<AppInfo> apps;
    private RecyclerView recyclerView;
    private AddMonitorRecycleAdaptor adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_monitor_app);
        recyclerView = findViewById(R.id.recyclerView_select);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        apps = InstalledApps.getUnMonitorAppInfo(this);
        adapter = new AddMonitorRecycleAdaptor(this, apps);
        recyclerView.setAdapter(adapter);

        Toast.makeText(getApplicationContext(), "Tab to add an app to be monitored",
                Toast.LENGTH_LONG).show();
    }
}
