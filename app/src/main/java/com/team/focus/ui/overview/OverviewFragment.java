package com.team.focus.ui.overview;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.team.focus.AddMonitorAppActivity;
import com.team.focus.R;
import com.team.focus.data.model.OverviewItem;
import com.team.focus.data.model.SharedPreferenceAccessUtils;
import com.team.focus.ui.Adaptor.OverviewRecycleAdaptor;

import java.util.ArrayList;

public class OverviewFragment extends Fragment {

    private ArrayList<OverviewItem> items;
    private RecyclerView recyclerView;
    private OverviewRecycleAdaptor adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_overview, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        final TextView textViewUser = root.findViewById(R.id.overview_header_username);
        final TextView textViewActive = root.findViewById(R.id.active_hours);
        final TextView textViewInterval = root.findViewById(R.id.interval);
        final ImageButton addMonitor = root.findViewById(R.id.bntAddMonitor);

        // ToDo: build data pipeline to server and monitor
        items = OverviewItem.OverviewItemUtils.getOverviewItemList(root.getContext());
//        items = new ArrayList<>(Arrays.asList(new OverviewItem("Youtube",
//                "com.google.youtube", new Usage(1, 0), new Usage(2, 0)),
//                new OverviewItem("Wechat",
//                        "com.tecent.wechat", new Usage(2, 30), new Usage(2, 0))));

        adapter = new OverviewRecycleAdaptor(root.getContext(), items);
        recyclerView.setAdapter(adapter);

        int startTime = SharedPreferenceAccessUtils.getTimeIntervalStart(root.getContext());
        int endTime = SharedPreferenceAccessUtils.getTimeIntervalEnd(root.getContext());
        Integer interval = null;
        boolean isActiveMode = SharedPreferenceAccessUtils.getIsActiveMode(root.getContext());
        String textActive = null;
        if (isActiveMode) {
            textActive = "Monitor from " + startTime + " to " + endTime + " today";
            interval = endTime - startTime;
        } else {
            textActive = "Monitor from " + startTime + " to " + startTime + " next day";
            interval = 24;
        }
        String textInterval = interval + " hours in total";
        textViewActive.setText(textActive);
        textViewInterval.setText(textInterval);

        textViewUser.setText(SharedPreferenceAccessUtils.getUsername(root.getContext()));

        addMonitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddMonitorAppActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        return root;
    }
}