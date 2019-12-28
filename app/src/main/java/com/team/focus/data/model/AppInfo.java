package com.team.focus.data.model;

import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;

public class AppInfo implements Comparable<AppInfo> {

    private String appName;
    private String packageName;
    private Drawable icon;


    public AppInfo(String appName, String packageName, Drawable icon) {
        this.appName = appName;
        this.packageName = packageName;
        this.icon = icon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof AppInfo)) {
            return false;
        }
        AppInfo a = (AppInfo) obj;
        return this.packageName.equals(a.packageName);
    }

    @Override
    public int compareTo(AppInfo o) {
        return this.appName.compareTo(o.appName);
    }
}
