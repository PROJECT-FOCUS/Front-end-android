package com.team.focus.data.model;

import androidx.annotation.NonNull;

import java.util.Comparator;

public class Usage implements Comparable<Usage> {
    private int hour;
    private int minute;

    public Usage(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    @NonNull
    @Override
    public String toString() {
        String min = minute / 10 == 0 ? "0" + Integer.toString(minute) : Integer.toString(minute);
        return hour + " : " +min;
    }

    @Override
    public int compareTo(Usage o) {
        if (this.hour == o.hour) {
            if (this.minute == o.minute) {
                return 0;
            }
            return this.minute < o.minute ? -1 : 1;
        } else {
            return this.hour < o.hour ? -1 : 1;
        }
    }
}
