package com.team.focus.data;

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
        editor.putString("userID", user.getUserId());
        editor.putString("username", user.getDisplayName());
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
        editor.putString("userID", null);
        editor.putString("username", null);
        editor.commit();
    }
}
