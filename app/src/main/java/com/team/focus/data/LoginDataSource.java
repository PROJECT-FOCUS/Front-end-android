package com.team.focus.data;

import android.content.Context;

import com.team.focus.data.model.LoggedInUser;
import com.team.focus.data.model.SharedPreferenceAccessUtils;
import com.team.focus.data.pipeline.Account;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private Context context;
    private LoggedInUser user;

    public void setContext(Context context) {
        this.context = context;
    }

    public Result<LoggedInUser> login(final String username, final String password) {
        try {

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        user = Account.login(username, password);
                    } catch (Exception e) {
                        e.printStackTrace();
                        user = null;
                    }
                }
            });
            thread.start();
            LoggedInUser user = Account.login(username, password);
            SharedPreferenceAccessUtils.setLoginUser(context, user);
            return new Result.Success<>(user);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        SharedPreferenceAccessUtils.removeLoginUser(context);
    }
}
