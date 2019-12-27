package com.team.focus.ui.Adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team.focus.R;
import com.team.focus.data.model.RecommendationTip;

import java.util.List;

public class RecommendationListRecycleAdaptor extends RecyclerView.Adapter<RecommendationListRecycleAdaptor.MyViewHolder> {

    private List<RecommendationTip> tips;

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tip;
        ImageView type;


        public MyViewHolder(View view) {
            super(view);

            tip = view.findViewById(R.id.tip);
            type = view.findViewById(R.id.type);
        }
    }

    public RecommendationListRecycleAdaptor(List<RecommendationTip> tips) {
        this.tips = tips;
    }

    @NonNull
    @Override
    public RecommendationListRecycleAdaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recommendation_list, parent, false);

        return new RecommendationListRecycleAdaptor.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendationListRecycleAdaptor.MyViewHolder holder, int position) {
        final RecommendationTip tip = tips.get(position);
        holder.tip.setText(tip.getTip());
        holder.type.setImageDrawable(tip.getType());
    }

    @Override
    public int getItemCount() {
        return tips.size();
    }
}

