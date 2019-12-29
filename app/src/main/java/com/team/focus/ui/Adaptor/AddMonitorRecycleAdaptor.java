package com.team.focus.ui.Adaptor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team.focus.R;
import com.team.focus.data.model.AppInfo;
import com.team.focus.data.model.SharedPreferenceAccessUtils;
import com.team.focus.data.model.Usage;
import com.team.focus.data.pipeline.BackendExpectedUsageItem;
import com.team.focus.data.pipeline.Update;

import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class AddMonitorRecycleAdaptor extends RecyclerView.Adapter<AddMonitorRecycleAdaptor.MyViewHolder> {

    final private Context context;
    private List<AppInfo> items;

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView appName, packageName;
        ImageView appIcon;


        public MyViewHolder(View view) {
            super(view);

            appName = view.findViewById(R.id.appName_select);
            packageName = view.findViewById(R.id.packageName_select);
            appIcon = view.findViewById(R.id.icon_select);
        }
    }

    public AddMonitorRecycleAdaptor(Context context, List<AppInfo> items) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public AddMonitorRecycleAdaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_select_list, parent, false);

        itemView.setClickable(true);
        return new AddMonitorRecycleAdaptor.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AddMonitorRecycleAdaptor.MyViewHolder holder, int position) {
        final AppInfo item = items.get(position);
        holder.appName.setText(item.getAppName());
        holder.packageName.setText(item.getPackageName());
        holder.appIcon.setImageDrawable(item.getIcon());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // time when adding an app


                // Actual usage -> monitor module
                // minute(int)

                // local update
                final String appPackageName = item.getPackageName();
                Map<String, Usage> expectedUsage = new HashMap<>();

                expectedUsage.put(appPackageName, new Usage(0));

                SharedPreferenceAccessUtils.addMonitoredApps(context,
                        new HashSet<String>(Arrays.asList(appPackageName)),
                        expectedUsage);

                // cloud update

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Update.updateExpectedUsage(SharedPreferenceAccessUtils.getUserId(context),
                                new Date().toString(), new HashSet<BackendExpectedUsageItem>(
                                        Arrays.asList(new BackendExpectedUsageItem(appPackageName,
                                                Duration.ofMinutes(0)))
                                ));
                    }
                });
                thread.start();

                Toast.makeText(context, "App " + item.getAppName()
                        + "has successfully add to monitor list", Toast.LENGTH_SHORT).show();
                Activity parent = (Activity)context;
                parent.setResult(122 , new Intent());
                parent.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
