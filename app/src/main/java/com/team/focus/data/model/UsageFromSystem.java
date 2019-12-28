package com.team.focus.data.model;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class UsageFromSystem {

    static private Map<String, UsageStats> getAllUsage(Context context) {
        UsageStatsManager manager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        Pair<Long, Long> pair = SharedPreferenceAccessUtils.getUserInterval(context);
        return manager.queryAndAggregateUsageStats(pair.first, pair.second);
    }

    static public Map<String, Usage> getMonitoredUsage(Context context) {
        Map<String, UsageStats> allUsage = getAllUsage(context);
        Set<String> monitoredApp = SharedPreferenceAccessUtils.getMonitoredApps(context);

        Map<String, Usage> monitoredUsage = new HashMap<>();

        for (String packageName : monitoredApp) {
            if (allUsage.containsKey(packageName)) {
                UsageStats stats = allUsage.get(packageName);
                monitoredUsage.put(packageName,
                        new Usage((int) TimeUnit.MILLISECONDS.toMinutes(stats.getTotalTimeVisible())));
            }
        }
        return monitoredUsage;
    }
}
