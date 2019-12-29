package com.team.focus.data.pipeline;

import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.MalformedURLException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashSet;
import java.util.Set;
import java.time.Duration;


//@TargetApi(11)
public class BackendUtility {

    // Backend URLs

    // https://developer.android.com/studio/run/emulator-networking
    // use 10.0.2.2 if running on Android emulator:
    private static final String HOSTNAME = "http://10.0.2.2";
    // use 127.0.0.1 or localhost if running on Android cell phone
    //private static final String HOSTNAME = "http://127.0.0.1";
    private static final String PORT_NUM = "8080"; // change it to your mysql port number
    private static final String PROJ_NAME = "Focus";

    public static final String URL = HOSTNAME + ":" + PORT_NUM + "/" + PROJ_NAME;

    public static final String URL_LOGIN = URL + "/" + "login";
    public static final String URL_LOGOUT = URL + "/" + "logout";
    public static final String URL_REGISTER = URL + "/" + "register";
    public static final String URL_OVERVIEW = URL + "/" + "overview";
    public static final String URL_UPDATE = URL + "/" + "update";
    public static final String URL_MONITOR = URL + "/" + "monitor";
    public static final String URL_RECOMMEND = URL + "/" + "recommend";

    // extract HTTP response body into a JSONObject, no matter what status
    public static JSONObject readJsonObjectFromResponse(HttpURLConnection conn) {
        if (null==conn) {
            return new JSONObject();
        }
        StringBuilder responseBody = new StringBuilder();
        try {
            // Create a BufferedReader to help read text from a character-input stream.
            // Provide for the efficient reading of characters, arrays, and lines.
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                responseBody.append(line);
            }
            reader.close();
            return new JSONObject(responseBody.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e)   {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    // read full name from JSONObject
    public static String readFullname(JSONObject obj)    {
        try {
            return obj.getString("first_name") + " " + obj.getString("last_name");
        } catch (JSONException e)   {
            e.printStackTrace();
        }
        return new String();
    }

    // read expected usage from JSONObject
    public static Set<BackendExpectedUsageItem>   readExpectedUsage(JSONObject obj) {
        Set<BackendExpectedUsageItem> usageSet = new HashSet<>();
        try {
            JSONArray expUsageArray = obj.getJSONArray("exp_usage");
            for (int i = 0; i < expUsageArray.length(); ++i) {
                // get JASONArray from JASONArray of JASONArray
                JSONArray expUsagePair = expUsageArray.getJSONArray(i);
                // get appId and expected usage time
                String appId = expUsagePair.getString(0);
                String usage = expUsagePair.getString(1);
                // create an ExpectedUsageItem
                BackendExpectedUsageItem usageItem = new BackendExpectedUsageItem(appId, usage);
                // add it into the set
                usageSet.add(usageItem);
            }
        } catch (JSONException e)   {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usageSet;
    }

    // write expected usage into a JSONArray
    public static JSONArray writeExpectedUsage(Set<BackendExpectedUsageItem> usageSet) {
        JSONArray usageArray = new JSONArray();
        try {
            // create JSONArray
            for (BackendExpectedUsageItem usageItem : usageSet) {
                JSONArray usagePair = new JSONArray();
                usagePair.put(usageItem.getAppId());
                Duration usage = Duration.ofSeconds(usageItem.getUsage().getSeconds());
                usagePair.put(DurationHelper.reformatDuration(usage));
                // put JSONArray into JSONArray
                usageArray.put(usagePair);
            }
            return usageArray;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usageArray;
    }

    // read actual usage from JSONObject
    public static Set<BackendActualUsageItem>   readActualUsage(JSONObject obj) {
        Set<BackendActualUsageItem> usageSet = new HashSet<>();
        try {
            JSONArray actUsageArray = obj.getJSONArray("act_usage");
            for (int i = 0; i < actUsageArray.length(); ++i) {
                // get JASONArray from JASONArray of JASONArray
                JSONArray actUsagePair = actUsageArray.getJSONArray(i);
                // get appId and expected usage time
                String appId = actUsagePair.getString(0);
                String usage = actUsagePair.getString(1);
                // create an ExpectedUsageItem
                BackendActualUsageItem usageItem = new BackendActualUsageItem(appId, usage);
                // add it into the set
                usageSet.add(usageItem);
            }
        } catch (JSONException e)   {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usageSet;
    }

    // write actual usage into JSONObject
    public static JSONArray writeActualUsage(Set<BackendActualUsageItem> usageSet) {
        JSONArray usageArray = new JSONArray();
        try {
            // create JSONArray
            for (BackendActualUsageItem usageItem : usageSet) {
                JSONArray usagePair = new JSONArray();
                usagePair.put(usageItem.getAppId());
                Duration usage = Duration.ofSeconds(usageItem.getUsage().getSeconds());
                usagePair.put(DurationHelper.reformatDuration(usage));
                // put JSONArray into JSONArray
                usageArray.put(usagePair);
            }
            return usageArray;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usageArray;
    }
    /*
    public static void writeActualUsage(JSONObject obj, Set<BackendActualUsageItem> usageSet) {
        try {
            // create JSONArray
            JSONArray usageArray = new JSONArray();
            for (BackendActualUsageItem usageItem : usageSet) {
                JSONArray usagePair = new JSONArray();
                usagePair.put(usageItem.getAppId());
                Duration usage = Duration.ofSeconds(usageItem.getUsage().getSeconds());
                usagePair.put(DurationHelper.reformatDuration(usage));
                // put JSONArray into JSONArray
                usageArray.put(usagePair);
            }
            // put JSONArray into JSONObject(output)
            obj.put("act_usage", usageArray);
        } catch (JSONException e)   {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    // read overtimed apps from JSONObject
    public static Set<String>   readOvertimedApps(JSONObject obj) {
        Set<String> appSet = new HashSet<>();
        try {
            JSONArray appArray = obj.getJSONArray("overtimed_apps");
            for (int i = 0; i < appArray.length(); ++i) {
                appSet.add(appArray.getString(i));
            }
        } catch (JSONException e)   {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appSet;
    }


    // read recommendations from JSONObject
    public static Set<String>   readRecommendations(JSONObject obj) {
        Set<String> recSet = new HashSet<>();
        try {
            JSONArray recArray = obj.getJSONArray("recommendation");
            for (int i = 0; i < recArray.length(); ++i) {
                recSet.add(recArray.getString(i));
            }
        } catch (JSONException e)   {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recSet;
    }

}
