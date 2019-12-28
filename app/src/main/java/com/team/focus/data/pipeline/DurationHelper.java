package com.team.focus.data.pipeline;

import java.time.Duration;
import java.util.regex.Pattern;

public class DurationHelper {

    // "hh:mm:ss" to number of seconds
    public static int getSecondsFromFormattedDuration(String duration) {
        if (duration == null)
            return 0;
        try {

            Pattern patternDuration = Pattern.compile("\\d+(?::\\d+){0,2}");

            int hours = 0;
            int minutes = 0;
            int seconds = 0;
            if (patternDuration.matcher(duration).matches()){
                String[] tokens = duration.split(":");
                if (tokens.length==1){
                    seconds = Integer.parseInt(tokens[0]);
                } else if (tokens.length == 2){
                    minutes = Integer.parseInt(tokens[0]);
                    seconds = Integer.parseInt(tokens[1]);
                } else {
                    hours = Integer.parseInt(tokens[0]);
                    minutes = Integer.parseInt(tokens[1]);
                    seconds = Integer.parseInt(tokens[2]);
                }
                return 3600 * hours + 60 * minutes + seconds;
            } else {
                return 0;
            }
        } catch (NumberFormatException ignored){
            return 0;
        }
    }

    // Duration to "hh:mm:ss"
    public static String reformatDuration(Duration duration) {
        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        String positive = String.format(
                "%02d:%02d:%02d",
                absSeconds / 3600,
                (absSeconds % 3600) / 60,
                absSeconds % 60);
        return seconds < 0 ? "-" + positive : positive;
    }
}