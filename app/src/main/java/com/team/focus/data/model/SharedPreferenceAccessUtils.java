package com.team.focus.data.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.team.focus.data.model.LoggedInUser;

public class SharedPreferenceAccessUtils {
    /**
     * Local database utils functions
     * SharedPreference default name "FOCUS"
     */

    /**
     * register the user in local database to avoid constantly asking login when app restarts
     * save UI (probably used for data transmission with server)
     * save username
     */
    public static void setLoginUser(Context context, LoggedInUser user) {
        SharedPreferences.Editor editor = context.getSharedPreferences("FOCUS",
                Context.MODE_PRIVATE).edit();
        editor.putBoolean("didUserLogin", true);
        editor.putString("username", user.getDisplayName());
        editor.putBoolean("pushNotification", true);
        editor.putInt("startHour", 0);
        editor.putInt("endHour", 0);
        editor.commit();
    }

    /**
     * remove user credentials when one opt to log out
     * reset didUserLogin
     */
    public static void removeLoginUser(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("FOCUS",
                Context.MODE_PRIVATE).edit();
        editor.putBoolean("didUserLogin", false);
        editor.putString("username", null);
        editor.commit();
    }

    /**
     * get logged in username
     */
    public static String getUsername(Context context) {
        return context.getSharedPreferences("FOCUS", Context.MODE_PRIVATE)
                .getString("username", "");
    }

    public static boolean updateNotification(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("FOCUS",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        boolean prevPreference = preferences.getBoolean("pushNotification", true);
        editor.putBoolean("pushNotification", !prevPreference);
        editor.commit();
        return prevPreference;
    }

    public static boolean getNotification(Context context) {
        return context.getSharedPreferences("FOCUS", Context.MODE_PRIVATE)
                .getBoolean("pushNotification", true);
    }

    public static void updateTimeIntervalStart(Context context, int hour) {
        SharedPreferences.Editor preferences = context.getSharedPreferences("FOCUS",
                Context.MODE_PRIVATE).edit();
        preferences.putInt("startHour", hour);
        preferences.commit();
    }

    public static void updateTimeIntervalEnd(Context context, int hour) {
        SharedPreferences.Editor preferences = context.getSharedPreferences("FOCUS",
                Context.MODE_PRIVATE).edit();
        preferences.putInt("endHour", hour);
        preferences.commit();
    }

    public static int getTimeIntervalStart(Context context) {
        return context.getSharedPreferences("FOCUS", Context.MODE_PRIVATE)
                .getInt("startHour", 0);
    }

    public static int getTimeIntervalEnd(Context context) {
        return context.getSharedPreferences("FOCUS", Context.MODE_PRIVATE)
                .getInt("endHour", 0);
    }
}
