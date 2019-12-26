package com.team.focus.data.model;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class InstalledApps {

    public static List<AppInfo> getMonitorAppInfo(Set<String> packageNames, Context context) {
        List<AppInfo> apps = new ArrayList<>();
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

    public static List<AppInfo> getAllApps(Context context) {
        List<AppInfo> apps = new ArrayList<>();
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> installedApps = packageManager.getInstalledPackages(0);

        for (PackageInfo installedApp : installedApps) {
            if ((installedApp.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                apps.add(new AppInfo(installedApp.applicationInfo.
                        loadLabel(packageManager).toString(),
                        installedApp.packageName,
                        installedApp.applicationInfo.loadIcon(packageManager))
                );
            }
        }

        return apps;
    }
}
