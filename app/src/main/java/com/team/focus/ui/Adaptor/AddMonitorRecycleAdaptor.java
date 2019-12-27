package com.team.focus.ui.Adaptor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.team.focus.MainActivity;
import com.team.focus.R;
import com.team.focus.data.model.AppInfo;
import com.team.focus.data.model.SharedPreferenceAccessUtils;
import com.team.focus.data.model.Usage;

import java.util.Arrays;
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
                String appPackageName = item.getPackageName();
                Map<String, Usage> expectedUsage = new HashMap<>();
                Map<String, Usage> actualUsage = new HashMap<>();

                expectedUsage.put(appPackageName, new Usage(0));

                // Todo: assign to @Xueting, gather actual usage from Monitor module
                actualUsage.put(appPackageName, new Usage(0));

                SharedPreferenceAccessUtils.addMonitoredApps(context,
                        new HashSet<String>(Arrays.asList(appPackageName)),
                        expectedUsage, actualUsage);

                /*
                 //cloud update
                 //Todo: assign to @Xueting, upload newly added monitored list to cloud
                //make a HTTP POST request to send newly added monitored app to backend
                RequestQueue queue = Volley.newRequestQueue(context);
                final String url = "localhost:8080/overview";//assumed servlet name
                final Usage aUsage = actualUsage.get(appPackageName);
                final Usage eUsage = expectedUsage.get(appPackageName);
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response
                                Log.d("Response", response);
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Log.d("Error.Response", error.toString());
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("appName", item.getAppName());
                        params.put("packageName", item.getPackageName());
                        params.put("icon", item.getIcon().toString());
                        params.put("actualUsage", aUsage.toString());
                        params.put("expectedUsage", eUsage.toString());
                        return params;
                    }
                };
                queue.add(postRequest);
                 */
                Intent intent = new Intent(context, MainActivity.class);
                Toast.makeText(context, "App " + item.getAppName()
                        + "has successfully add to monitor list", Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
                Activity parent = (Activity)context;
                parent.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}
