package com.team.focus.data.pipeline;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;
import org.json.JSONArray;

public class Update {

    public void updateExpectedUsage(String username, String datetime, Set<BackendExpectedUsageItem> usageSet)   {

        try {
            URL url = new URL(BackendUtility.URL_UPDATE);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("user_id", username);
            conn.setRequestProperty("date_time", datetime);
            JSONArray usageArray = BackendUtility.writeExpectedUsage(usageSet);
            conn.setRequestProperty("exp_usage", usageArray.toString());

            conn.connect();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                /// warn out ?
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
