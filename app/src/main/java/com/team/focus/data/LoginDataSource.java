package com.team.focus.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.team.focus.data.model.LoggedInUser;
import com.team.focus.data.pipeline.Account;
import com.team.focus.ui.login.LoginActivity;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public Result<LoggedInUser> login(String username, String password) {
        try {
            LoggedInUser user = Account.loginOrRegister(username, password);
            SharedPreferences.Editor editor = context.getSharedPreferences("FOCUS",
                    Context.MODE_PRIVATE).edit();
            editor.putBoolean("didUserLogin", true);
            editor.putString("userID", user.getUserId());
            editor.putString("username", user.getDisplayName());
            editor.commit();
            return new Result.Success<>(user);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        SharedPreferences.Editor editor = context.getSharedPreferences("FOCUS",
                Context.MODE_PRIVATE).edit();
        editor.putBoolean("didUserLogin", false);
        editor.putString("userID", null);
        editor.putString("username", null);
        editor.commit();
    }
}
