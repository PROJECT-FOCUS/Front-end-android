package com.team.focus.ui.recommendation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.team.focus.R;

public class RecommendationFragment extends Fragment {

    private RecommendationViewModel recommendationViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recommendationViewModel =
                ViewModelProviders.of(this).get(RecommendationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_recommendation, container, false);
        final TextView textView = root.findViewById(R.id.text_recommendation);
        recommendationViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}