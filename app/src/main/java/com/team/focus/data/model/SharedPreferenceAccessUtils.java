package com.team.focus.data.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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
        int endTime = context.getSharedPreferences("FOCUS", Context.MODE_PRIVATE)
                .getInt("endHour", 24);
        return endTime == 0 ? 24 : endTime;
    }

    public static boolean getIsActiveMode(Context context) {
        return context.getSharedPreferences("FOCUS", Context.MODE_PRIVATE)
                .getBoolean("isActiveMode", true);
    }

    public static boolean updateIsActiveMode(Context context, boolean activeMode) {
        SharedPreferences preferences = context.getSharedPreferences("FOCUS",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        boolean prevPreference = preferences.getBoolean("isActiveMode", true);
        editor.putBoolean("isActiveMode", activeMode);
        editor.commit();
        return !activeMode;
    }

    /**
     * utils to get packageName
     * @param context
     * @return a set of packageNames of monitored apps
     */
    public static Set<String> getMonitoredApps(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("FOCUS", Context.MODE_PRIVATE);
        return preferences.getStringSet("savedMonitoredApps", new HashSet<String>());
    }

    /**
     * utils to update packageName
     * @param context
     * @param toDeletePackageNames to delete (stop monitoring) apps
     */
    public static void deleteMonitoredApps(Context context, Set<String> toDeletePackageNames) {
        SharedPreferences preferences = context.getSharedPreferences("FOCUS",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Set<String> prev = preferences.getStringSet("savedMonitoredApps", new HashSet<String>());
        Iterator<String> iterator = prev.iterator();

        while (iterator.hasNext()) {
            String curr = iterator.next();
            if (toDeletePackageNames.contains(curr)) {
                iterator.remove();
            }
        }
        editor.putStringSet("savedMonitoredApps", prev);
        editor.apply();
    }

    public static void addMonitoredApps(Context context, Set<String> toAddPackageNames,
                                        Map<String, Usage> expectedUsage) {

        SharedPreferences pFocus = context.getSharedPreferences("FOCUS",
                Context.MODE_PRIVATE);
        SharedPreferences pExpected = context.getSharedPreferences("FOCUS.Expected",
                Context.MODE_PRIVATE);
        SharedPreferences pActual = context.getSharedPreferences("FOCUS.Actual",
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editorExpected = pExpected.edit();
        SharedPreferences.Editor editorActual = pActual.edit();
        SharedPreferences.Editor editorFocus = pFocus.edit();

        for (String packageName : toAddPackageNames) {
            if (expectedUsage.containsKey(packageName)) {
                editorExpected.putInt(packageName, expectedUsage.get(packageName).toMinute());
            }
        }

        Set<String> prev = pFocus.getStringSet("savedMonitoredApps", new HashSet<String>());
        prev.addAll(toAddPackageNames);
        editorFocus.putStringSet("savedMonitoredApps", prev);
        editorFocus.apply();
        editorActual.apply();
        editorExpected.apply();
    }

    /**
     * utils to get minute of expected usage for passed in packageName
     * @param context
     * @return
     */
    public static Map<String, Usage> getExpectedUsage(Context context) {
        Set<String> packageNames = getMonitoredApps(context);
        Map<String, Usage> expectedUsage = new HashMap<>();
        SharedPreferences preferences = context.getSharedPreferences("FOCUS.Expected", Context.MODE_PRIVATE);
        for (String packageName : packageNames) {
            int minute = preferences.getInt(packageName, -1);
            if (minute >= 0) {
                expectedUsage.put(packageName, new Usage(minute));
            }
        }
        return expectedUsage;
    }

    public static Map<String, Usage> getActualUsage(Context context) {
        Set<String> packageNames = getMonitoredApps(context);
        Map<String, Usage> actualUsage = new HashMap<>();
        SharedPreferences preferences = context.getSharedPreferences("FOCUS.Actual", Context.MODE_PRIVATE);
        for (String packageName : packageNames) {
            int minute = preferences.getInt(packageName, -1);
            if (minute >= 0) {
                actualUsage.put(packageName, new Usage(minute));
            }
        }
        return actualUsage;
    }

    public static Usage updateAppExpectedUsage(Context context, String packageName, int minute) {
        SharedPreferences preferences = context.getSharedPreferences("FOCUS.Expected", Context.MODE_PRIVATE);
        Usage prev = new Usage(preferences.getInt(packageName, 0));
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(packageName, minute);
        editor.apply();
        return prev;
    }

    public static Pair<Long, Long> getUserInterval(Context context) {
        boolean isActiveMode = getIsActiveMode(context);
        int startTime = getTimeIntervalStart(context);
        int endTime = getTimeIntervalEnd(context);
        Calendar calendar = Calendar.getInstance();

        if (isActiveMode) {
            if (calendar.get(Calendar.HOUR_OF_DAY) < startTime) {
                // return yesterday's system query

                //turn to yesterday first
                calendar.add(Calendar.DATE, -1);
                // set start time
                calendar.set(Calendar.HOUR_OF_DAY, startTime);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                Long start = calendar.getTimeInMillis();
                calendar.set(Calendar.HOUR_OF_DAY, endTime);
                Long end = calendar.getTimeInMillis();

                return new Pair<>(start, end);

            } else if (calendar.get(Calendar.HOUR_OF_DAY) >= startTime &&
                    calendar.get(Calendar.HOUR_OF_DAY) < endTime) {
                // get current
                Long end = calendar.getTimeInMillis();

                // get start today
                calendar.set(Calendar.HOUR_OF_DAY, startTime);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                Long start = calendar.getTimeInMillis();

                return new Pair<>(start, end);
            } else {
                calendar.set(Calendar.HOUR_OF_DAY, startTime);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                Long start = calendar.getTimeInMillis();
                calendar.set(Calendar.HOUR_OF_DAY, endTime);
                Long end = calendar.getTimeInMillis();
                return new Pair<>(start, end);
            }
        } else {
            if (calendar.get(Calendar.HOUR_OF_DAY) < startTime) {
                Long end = calendar.getTimeInMillis();

                //turn to yesterday
                calendar.add(Calendar.DATE, -1);
                // set start time
                calendar.set(Calendar.HOUR_OF_DAY, startTime);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                Long start = calendar.getTimeInMillis();

                return new Pair<>(start, end);
            } else {
                Long end = calendar.getTimeInMillis();

                calendar.set(Calendar.HOUR_OF_DAY, startTime);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                Long start = calendar.getTimeInMillis();

                return new Pair<>(start, end);
            }
        }
    }
}
