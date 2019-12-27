package com.team.focus.ui.overview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.team.focus.MainActivity;
import com.team.focus.R;
import com.team.focus.data.model.OverviewItem;
import com.team.focus.data.model.SharedPreferenceAccessUtils;
import com.team.focus.data.model.Usage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

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

        final ImageView icon = root.findViewById(R.id.icon_op);
        final TextView appName = root.findViewById(R.id.appName_op);
        final TextView packageName = root.findViewById(R.id.packageName_op);
        final TextView actualUsage = root.findViewById(R.id.act_usage_time);
        final TextView expectedUsage = root.findViewById(R.id.exp_ussge_time);

        actualUsage.setText(item.getActualUsage().toString());
        expectedUsage.setText(item.getExpectedUsage().toString());

        final HashMap<String, Usage> cache = new HashMap<>();
        cache.put("changed", item.getExpectedUsage());

        expectedUsage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // Todo: create another time picker
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
            // Todo: configure save btn
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferenceAccessUtils.deleteMonitoredApps(context, new HashSet<String>(
                        Arrays.asList(packageName.getText().toString())));
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                AppCompatActivity activity = (AppCompatActivity) context;
                activity.setResult(Activity.RESULT_OK);
                activity.finish();
            }
        });

        return root;
    }

}
