package com.team.focus.ui.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.team.focus.R;
import com.team.focus.data.model.OverviewItem;
import com.team.focus.data.model.Usage;
import com.team.focus.ui.overview.AppMonitorFragment;

import java.util.List;

public class OverviewRecycleAdaptor extends RecyclerView.Adapter<OverviewRecycleAdaptor.MyViewHolder> {

    private Context context;
    private List<OverviewItem> items;
    private Fragment parentFragment;
    private FragmentManager fragmentManager;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


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

        @Override
        public void onClick(View v) {
        }

    }

    public OverviewRecycleAdaptor(Context context, List<OverviewItem> items,
                                  Fragment parentFragment, FragmentManager fragmentManager) {
        this.items = items;
        this.context = context;
        this.parentFragment = parentFragment;
        this.fragmentManager = fragmentManager;
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
        final OverviewItem item = items.get(position);
        holder.appName.setText(item.getAppName());
        holder.packageName.setText(item.getPackageName());
        String percentage = item.getExpectedUsage().toMinute() == 0 ?
                "N/A" : item.getUsagePercentage() + "%";
        holder.usagePercentage.setText(percentage);
        Usage expect = item.getExpectedUsage();
        Usage actual = item.getActualUsage();
        holder.expectedUsage.setText("Expected  " + expect.toString());
        holder.actualUsage.setText("Actual  " + actual.toString());
        holder.appIcon.setImageDrawable(item.getIcon());

        if (actual.compareTo(expect) > 0) {
            holder.actualUsage.setTextColor(context.getResources().getColor(R.color.colorAccent));
        } else if (actual.compareTo(expect) == 0) {
            holder.actualUsage.setTextColor(context.getResources().getColor(R.color.yellow));
        }

        holder.itemView.setClickable(true);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                AppMonitorFragment itemOperation = new AppMonitorFragment(item, activity);
                itemOperation.setTargetFragment(parentFragment,
                        v.getResources().getInteger(R.integer.item_operation));
                itemOperation.show(fragmentManager, "onItemOperation");
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
