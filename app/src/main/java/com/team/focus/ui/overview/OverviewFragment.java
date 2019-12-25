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

import com.team.focus.R;
import com.team.focus.data.model.SharedPreferenceAccessUtils;

public class OverviewFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_overview, container, false);

        final TextView textViewUser = root.findViewById(R.id.overview_header_username);
        final TextView textViewActive = root.findViewById(R.id.active_hours);
        final TextView textViewInterval = root.findViewById(R.id.interval);
        final ImageButton addMonitor = root.findViewById(R.id.bntAddMonitor);

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