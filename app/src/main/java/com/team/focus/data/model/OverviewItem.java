package com.team.focus.data.model;

import android.graphics.drawable.Drawable;

public class OverviewItem {

    private String appName;
    private String packageName;
    private Usage expectedUsage;
    private Usage actualUsage;
    private Drawable icon;

    public OverviewItem(String appName, String packageName, Usage expectedUsage, Usage actualUsage, Drawable icon) {
        this.appName = appName;
        this.packageName = packageName;
        this.expectedUsage = expectedUsage;
        this.actualUsage = actualUsage;
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

    public Usage getExpectedUsage() {
        return expectedUsage;
    }

    public void setExpectedUsage(Usage expectedUsage) {
        this.expectedUsage = expectedUsage;
    }

    public Usage getActualUsage() {
        return actualUsage;
    }

    public void setActualUsage(Usage actualUsage) {
        this.actualUsage = actualUsage;
    }

    public double getUsagePercentage() {
        float total = expectedUsage.getHour() * 60 + expectedUsage.getMinute();
        float actual = actualUsage.getHour() * 60 + actualUsage.getMinute();

        return Math.round(actual/total*1000)/10.0;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
