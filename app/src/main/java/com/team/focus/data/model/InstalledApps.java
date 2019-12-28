package com.team.focus.data.model;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class InstalledApps {

    public static ArrayList<AppInfo> getMonitorAppInfo(Set<String> packageNames, Context context) {
        ArrayList<AppInfo> apps = new ArrayList<>();
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> installedApps = packageManager.getInstalledPackages(0);

        for (PackageInfo installedApp : installedApps) {
            String packageName = installedApp.packageName;
            if (packageNames.contains(packageName)) {
                apps.add(new AppInfo(installedApp.applicationInfo.
                                loadLabel(packageManager).toString(),
                                packageName,
                                installedApp.applicationInfo.loadIcon(packageManager))
                        );
            }
        }
        return apps;
    }

    private static ArrayList<AppInfo> getAllApps(Context context) {
        ArrayList<AppInfo> apps = new ArrayList<>();
        PackageManager packageManager = context.getPackageManager();

        List<PackageInfo> installedApps = packageManager.getInstalledPackages(0);

        for (PackageInfo installedApp : installedApps) {
            if (installedApp.applicationInfo.icon != 0 &&
                    ((installedApp.applicationInfo.flags & 1) != ApplicationInfo.FLAG_SYSTEM)) {
                apps.add(new AppInfo(installedApp.applicationInfo.
                        loadLabel(packageManager).toString(),
                        installedApp.packageName,
                        installedApp.applicationInfo.loadIcon(packageManager))
                );
            }
        }

        return apps;
    }

    public static ArrayList<AppInfo> getUnMonitorAppInfo(Context context) {
        ArrayList<AppInfo> res = new ArrayList<>();
        ArrayList<AppInfo> allApps = getAllApps(context);
        ArrayList<AppInfo> monitoredApps = getMonitorAppInfo(
                SharedPreferenceAccessUtils.getMonitoredApps(context), context);

        for (AppInfo app : allApps) {
            if (!monitoredApps.contains(app)) {
                res.add(app);
            }
        }
        Collections.sort(res);
        return res;
    }
}
