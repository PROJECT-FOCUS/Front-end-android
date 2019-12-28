package com.team.focus.ui.recommendation;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.team.focus.R;
import com.team.focus.data.model.RecommendationTip;
import com.team.focus.ui.Adaptor.RecommendationListRecycleAdaptor;

import java.util.ArrayList;
import java.util.Arrays;

public class RecommendationFragment extends Fragment {

    private ArrayList<RecommendationTip> suggestions;
    private RecyclerView recyclerView;
    private RecommendationListRecycleAdaptor adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recommendation, container, false);

        final TextView textView = root.findViewById(R.id.recommendation_banner);
        final Button button = root.findViewById(R.id.NLP);

        recyclerView = root.findViewById(R.id.recommendation_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // hardcoding for testing purpose
        textView.setText("You have 3 suggestions based on your statistics");
        Resources resourcesManager = root.getContext().getResources();
        suggestions = new ArrayList<>(Arrays.asList(
                new RecommendationTip("Total time of Game apps used was above expected",
                        resourcesManager.getDrawable(R.drawable.ic_error_red_24dp)),
                new RecommendationTip("Studying apps were used least frequently",
                        resourcesManager.getDrawable(R.drawable.ic_warning_yellow_24dp)),
                new RecommendationTip("Communication apps were used at average level",
                        resourcesManager.getDrawable(R.drawable.ic_check_box_black_24dp))));

        adapter = new RecommendationListRecycleAdaptor(suggestions);
        recyclerView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Developing feature", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}