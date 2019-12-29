package com.team.focus.data.pipeline;

import com.team.focus.data.model.LoggedInUser;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Account {

    public static LoggedInUser login(String username, String password) throws IOException {
        LoggedInUser user = null;
        try {
            URL url = new URL(BackendUtility.URL_LOGIN);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("user_id", username);
            conn.setRequestProperty("password", password);
            conn.connect();

            JSONObject response = BackendUtility.readJsonObjectFromResponse(conn);
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                user = new LoggedInUser(username, BackendUtility.readFullname(response));
            } else {
                user = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            // test purpose without backend
            return new LoggedInUser("111", "John Smith");
        }

        return user;
    }

    public static void logout(String username) throws IOException {
        try {
            URL url = new URL(BackendUtility.URL_LOGOUT);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("user_id", username);
            conn.connect();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                /// warn out ?
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static LoggedInUser register(String username, String password, String first, String last) {
        LoggedInUser user = null;
        try {
            URL url = new URL(BackendUtility.URL_REGISTER);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            conn.setRequestProperty("user_id", username);
            conn.setRequestProperty("password", password);
            conn.setRequestProperty("first_name", first);
            conn.setRequestProperty("last_name", last);
            conn.connect();

            JSONObject response = BackendUtility.readJsonObjectFromResponse(conn);
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                user = new LoggedInUser(username, first+" "+last);
            } else {
                user = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            // test purpose without backend
            return new LoggedInUser("111", first+" "+last);
        }
        return user;
    }
}
