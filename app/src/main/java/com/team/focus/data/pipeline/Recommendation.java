package com.team.focus.data.pipeline;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class Recommendation {

    public Set<String> getRecommendations(String username)   {

        Set<String>  recommendations = new HashSet<>();
        try {
            URL url = new URL(BackendUtility.URL_RECOMMEND);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("user_id", username);
            conn.connect();

            JSONObject response = BackendUtility.readJsonObjectFromResponse(conn);
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // process response: set of overtimed_apps (by their package name)
                recommendations = BackendUtility.readRecommendations(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return recommendations;
    }

}
