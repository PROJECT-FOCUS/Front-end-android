package com.team.focus.data.pipeline;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class Monitor {
    // also return set of overtimed_apps (by their package name), for notification purpose
    public Set<String> updateActualUsageAndGetNotification(String username, String date, Set<BackendActualUsageItem> usageSet)   {

        Set<String>  overtimedApps = new HashSet<>();
        try {
            URL url = new URL(BackendUtility.URL_MONITOR);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("user_id", username);
            conn.setRequestProperty("date", date);
            JSONArray usageArray = BackendUtility.writeActualUsage(usageSet);
            conn.setRequestProperty("act_usage", usageArray.toString());

            conn.connect();

            JSONObject response = BackendUtility.readJsonObjectFromResponse(conn);
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // process response: set of overtimed_apps (by their package name)
                overtimedApps = BackendUtility.readOvertimedApps(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return overtimedApps;
    }
}
