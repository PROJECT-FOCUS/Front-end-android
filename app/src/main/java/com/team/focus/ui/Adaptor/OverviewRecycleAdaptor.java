package com.team.focus.ui.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team.focus.R;
import com.team.focus.data.model.OverviewItem;
import com.team.focus.data.model.Usage;

import java.util.List;

public class OverviewRecycleAdaptor extends RecyclerView.Adapter<OverviewRecycleAdaptor.MyViewHolder> {

    private Context context;
    private List<OverviewItem> items;

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView appName, packageName, expectedUsage, actualUsage, usagePercentage;
        ImageView appIcon;


        public MyViewHolder(View view) {
            super(view);

            appName = view.findViewById(R.id.appName);
            packageName = view.findViewById(R.id.packageName);
            expectedUsage = view.findViewById(R.id.expected_usage);
            actualUsage = view.findViewById(R.id.actual_usage);
            usagePercentage = view.findViewById(R.id.percentage);
            appIcon = view.findViewById(R.id.icon);
        }

    }

    public OverviewRecycleAdaptor(Context context, List<OverviewItem> items) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public OverviewRecycleAdaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_overview_list, parent, false);

        return new OverviewRecycleAdaptor.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OverviewRecycleAdaptor.MyViewHolder holder, int position) {
        OverviewItem item = items.get(position);
        holder.appName.setText(item.getAppName());
        holder.packageName.setText(item.getPackageName());
        holder.usagePercentage.setText(item.getUsagePercentage() + "%");
        Usage expect = item.getExpectedUsage();
        Usage actual = item.getActualUsage();
        holder.expectedUsage.setText("Expected  " + expect.toString());
        holder.actualUsage.setText("Actual  " + actual.toString());
        holder.appIcon.setImageDrawable(item.getIcon());

        if (actual.compareTo(expect) > 0) {
            holder.actualUsage.setTextColor(context.getResources().getColor(R.color.colorAccent));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
