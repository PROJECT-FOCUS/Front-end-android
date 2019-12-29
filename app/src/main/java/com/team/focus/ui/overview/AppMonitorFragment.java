package com.team.focus.ui.overview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import com.team.focus.R;
import com.team.focus.data.model.OverviewItem;
import com.team.focus.data.model.SharedPreferenceAccessUtils;
import com.team.focus.data.model.Usage;
import com.team.focus.data.pipeline.BackendExpectedUsageItem;
import com.team.focus.data.pipeline.Update;
import com.team.focus.ui.utils.ExpectedUsagePickerFragment;

import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
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

                Toast.makeText(context, "Expected Usage has changed from " +
                        item.getExpectedUsage().toString() + " to " +
                        cache.toString(), Toast.LENGTH_SHORT).show();

                // update local
                SharedPreferenceAccessUtils.updateAppExpectedUsage(context, item.getPackageName(), cache.toMinute());

                // sync cloud
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Update.updateExpectedUsage(SharedPreferenceAccessUtils.getUserId(context),
                                new Date().toString(), new HashSet<BackendExpectedUsageItem>(
                                        Arrays.asList(new BackendExpectedUsageItem(item.getPackageName(),
                                                Duration.ofMinutes(cache.toMinute())))
                                ));
                    }
                });
                thread.start();

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

                // cloud sync

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
