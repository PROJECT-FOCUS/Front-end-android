package com.team.focus.ui.overview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.team.focus.R;
import com.team.focus.data.model.OverviewItem;
import com.team.focus.data.model.SharedPreferenceAccessUtils;
import com.team.focus.data.model.Usage;
import com.team.focus.ui.utils.ExpectedUsagePickerFragment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class AppMonitorFragment extends DialogFragment {

    private OverviewItem item;
    private Context context;

    public AppMonitorFragment(OverviewItem item, Context context) {
        this.item = item;
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_item_operation, container, false);
        final Fragment current = this;
        final Fragment targetFragment = getTargetFragment();

        final ImageView icon = root.findViewById(R.id.icon_op);
        final TextView appName = root.findViewById(R.id.appName_op);
        final TextView packageName = root.findViewById(R.id.packageName_op);
        final TextView actualUsage = root.findViewById(R.id.act_usage_time);
        final TextView expectedUsage = root.findViewById(R.id.exp_usage_time);

        actualUsage.setText(item.getActualUsage().toString());
        expectedUsage.setText(item.getExpectedUsage().toString());

        final Usage cache = new Usage(item.getExpectedUsage().getHour(),
                item.getExpectedUsage().getMinute());

        if (cache.compareTo(item.getActualUsage()) < 0) {
            actualUsage.setTextColor(getResources().getColor(R.color.colorAccent));
        } else if (cache.compareTo(item.getActualUsage()) == 0) {
            actualUsage.setTextColor(getResources().getColor(R.color.yellow));
        }

        expectedUsage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current.setUserVisibleHint(false);
                ExpectedUsagePickerFragment fragment = new ExpectedUsagePickerFragment(expectedUsage,
                        cache);
                fragment.show(getFragmentManager(), "usageTimePicker");
                current.setUserVisibleHint(true);
            }
        });

        final Button save = root.findViewById(R.id.save);
        final Button delete = root.findViewById(R.id.delete);

        icon.setImageDrawable(item.getIcon());
        appName.setText(item.getAppName());
        packageName.setText(item.getPackageName());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // update local
                SharedPreferenceAccessUtils.updateAppExpectedUsage(context, item.getPackageName(), cache.toMinute());

               /* // sync cloud
                // Todo: assign to @Xueting
                //use POST request for update for now; may change to PUT request if database servlet finds necessary
                RequestQueue queue = Volley.newRequestQueue(context);
                final String url = "localhost:8080/update";//assumed servlet name
                final Usage newExpectedUsage = cache;
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
                        params.put("packageName", item.getPackageName());
                        params.put("expectedUsage", newExpectedUsage.toString());
                        return params;
                    }
                };
                queue.add(postRequest);
*/

                Toast.makeText(context, "Expected Usage has changed from " +
                        item.getExpectedUsage().toString() + " to " +
                        cache.toString(), Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("packageName", item.getPackageName());
                bundle.putInt("expected", cache.toMinute());
                Intent intent = new Intent().putExtras(bundle);
                targetFragment.onActivityResult(getTargetRequestCode(),
                        getResources().getInteger(R.integer.item_changed), intent);
                dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // local update
                SharedPreferenceAccessUtils.deleteMonitoredApps(context, new HashSet<String>(
                        Arrays.asList(packageName.getText().toString())));

                /*// Todo: assign to @Xueting
                // cloud sync
                RequestQueue queue = Volley.newRequestQueue(context);
                final String url = "localhost:8080/update";//assumed servlet name
                StringRequest deleteRequest = new StringRequest(Request.Method.POST, url,
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
                ){
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("appName", item.getAppName());
                        params.put("packageName", item.getPackageName());
                        params.put("icon", item.getIcon().toString());
                        params.put("actualUsage", item.getActualUsage().toString());
                        params.put("expectedUsage", item.getExpectedUsage().toString());
                        return params;
                    }
                    //DELETE request in volley cannot take body; use POST request for a workaround for deleting item now; may optimize later
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String>  headers = new HashMap<String, String> ();
                        headers.put("X-HTTP-Method-Override", "DELETE");
                        headers.put("Accept", "application/json");
                        headers.put("Content-Type", "application/json");

                        return headers;
                    }
                };
                queue.add(deleteRequest);*/

                Bundle bundle = new Bundle();
                bundle.putString("packageName", item.getPackageName());
                Intent intent = new Intent().putExtras(bundle);
                targetFragment.onActivityResult(getTargetRequestCode(),
                        getResources().getInteger(R.integer.item_deleted), intent);
                Toast.makeText(context, "App " + item.getAppName() + " has been removed from" +
                        " monitor list", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        return root;
    }

}
