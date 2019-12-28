package com.team.focus.data.pipeline;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class Overview {

    // return set of expected usage to expUsageResult[0]
    // and set of actual usage to actUsageResult[0]
    public void getExpectedAndActualUsage(String username,
                                          Set<BackendExpectedUsageItem>[] expUsageResult,
                                          Set<BackendActualUsageItem>[] actUsageResult)   {


        try {
            URL url = new URL(BackendUtility.URL_OVERVIEW);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("user_id", username);
            conn.connect();

            JSONObject response = BackendUtility.readJsonObjectFromResponse(conn);
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // process response: set of overtimed_apps (by their package name)
                expUsageResult[0] = BackendUtility.readExpectedUsage(response);
                actUsageResult[0] = BackendUtility.readActualUsage(response);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
