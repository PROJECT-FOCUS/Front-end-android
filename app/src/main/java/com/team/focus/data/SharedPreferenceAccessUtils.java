package com.team.focus.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.team.focus.data.model.LoggedInUser;

public class SharedPreferenceAccessUtils {

    public static void setLoginUser(Context context, LoggedInUser user) {
        SharedPreferences.Editor editor = context.getSharedPreferences("FOCUS",
                Context.MODE_PRIVATE).edit();
        editor.putBoolean("didUserLogin", true);
        editor.putString("userID", user.getUserId());
        editor.putString("username", user.getDisplayName());
        editor.commit();
    }

    public static void removeLoginUser(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("FOCUS",
                Context.MODE_PRIVATE).edit();
        editor.putBoolean("didUserLogin", false);
        editor.putString("userID", null);
        editor.putString("username", null);
        editor.commit();
    }
}
