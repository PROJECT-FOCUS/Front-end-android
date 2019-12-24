package com.team.focus.data.pipeline;

import com.team.focus.data.model.LoggedInUser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class Account {

    private final static String ACCOUNT_LOGIN = "localhost:8080/login";
    private final static String ACCOUNT_SIGNUP = "localhost:8080/register";

    public static LoggedInUser login(String username, String password) throws IOException {
        LoggedInUser user = null;
        try {
            URL url = new URL(ACCOUNT_LOGIN);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("username", username);
            conn.setRequestProperty("password", password);

            conn.connect();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                user = new LoggedInUser(username, username);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return user;
    }

    public static LoggedInUser register(String username, String password, String first, String last) {
        LoggedInUser user = null;
        try {
            URL url = new URL(ACCOUNT_SIGNUP);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            conn.setRequestProperty("username", username);
            conn.setRequestProperty("password", password);

            conn.connect();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                user = new LoggedInUser(username, username);
            }
        } catch (Exception e) {
            return null;
        }
        return user;
    }
}
