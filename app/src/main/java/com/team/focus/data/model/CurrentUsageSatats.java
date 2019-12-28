package com.team.focus.data.model;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.pm.PackageManager;
import android.util.Pair;

import java.util.HashMap;
import java.util.List;

public class CurrentUsageSatats {
    private UsageStatsManager mUsageStatsManager;

    public HashMap<String, Integer> getCurrentUsageStatistics() {
            /*//Setting default start time
            int BEGIN_HOUR = 0;
            int BEGIN_MINUTE = 0;
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR, BEGIN_HOUR); // Change BEGIN_HOUR to the user designated hour
            cal.set(Calendar.MINUTE, BEGIN_MINUTE); //Change BEGIN_MINUTE to the user designated minute*/

        Pair<Long, Long> timeInterval = SharedPreferenceAccessUtils.getUserInterval();

        List<UsageStats> queryUsageStats = mUsageStatsManager
                .queryUsageStats(UsageStatsManager.INTERVAL_DAILY,timeInterval.first,
                        timeInterval.second);

        HashMap<String, Integer> map = new HashMap<>();
        final int statCount = queryUsageStats.size();
        for (int i = 0; i < statCount; i++){
            final android.app.usage.UsageStats pkgStats = queryUsageStats.get(i);
            try {
                Integer actualTime = (int) pkgStats.getTotalTimeVisible() / 60000;
                map.put(pkgStats.getPackageName(), Integer.valueOf(actualTime));
            } catch(PackageManager.NameNotFoundException e){
                //package may be gone.
            }

        }

        return map;
    }
}
