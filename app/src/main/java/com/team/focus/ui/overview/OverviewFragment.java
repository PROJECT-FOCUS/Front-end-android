package com.team.focus.ui.overview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.team.focus.MainActivity;
import com.team.focus.R;
import com.team.focus.data.model.OverviewItem;
import com.team.focus.data.model.SharedPreferenceAccessUtils;
import com.team.focus.data.model.Usage;
import com.team.focus.ui.Adaptor.OverviewRecycleAdaptor;

import java.util.ArrayList;
import java.util.Arrays;

public class OverviewFragment extends Fragment {

    private ArrayList<OverviewItem> items;
    private RecyclerView recyclerView;
    private OverviewRecycleAdaptor adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_overview, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        final TextView textViewUser = root.findViewById(R.id.overview_header_username);
        final TextView textViewActive = root.findViewById(R.id.active_hours);
        final TextView textViewInterval = root.findViewById(R.id.interval);
        final ImageButton addMonitor = root.findViewById(R.id.bntAddMonitor);

        // testing purpose
        // ToDo: build real data pipeline either from local (use SharedPreference) or from server
        items = new ArrayList<>(Arrays.asList(new OverviewItem("Youtube",
                "com.google.youtube", new Usage(1, 0), new Usage(2, 0)),
                new OverviewItem("Wechat",
                        "com.tecent.wechat", new Usage(2, 30), new Usage(2, 0))));

        adapter = new OverviewRecycleAdaptor(root.getContext(), items);
        recyclerView.setAdapter(adapter);

        int startTime = SharedPreferenceAccessUtils.getTimeIntervalStart(root.getContext());
        int endTime = SharedPreferenceAccessUtils.getTimeIntervalEnd(root.getContext());
        int interval = endTime - startTime;
        String textActive = "Active from " + startTime + " to " + endTime;
        String textInterval = interval + " hours in total";

        textViewActive.setText(textActive);
        textViewInterval.setText(textInterval);

        textViewUser.setText(SharedPreferenceAccessUtils.getUsername(root.getContext()));

        addMonitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ToDO : start add monitor object(s)
            }
        });

        return root;
    }
}