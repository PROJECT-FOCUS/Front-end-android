package com.team.focus.data.pipeline;

import com.team.focus.data.model.LoggedInUser;

public class Account {

    private final static String AccountServerUrl = "";

    private static LoggedInUser login(String username, String password) {
        return null;
    }

    private static LoggedInUser register(String username, String password) {
        return new LoggedInUser("111", "Test");
    }

    public static LoggedInUser loginOrRegister(String username, String password) {
        try {
            LoggedInUser user = login(username, password);
            if (user != null) {
                return user;
            }
            user = register(username, password);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
