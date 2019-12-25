package com.team.focus.ui.utils;

import android.app.Activity;
import android.widget.TextView;

import com.team.focus.R;
import com.team.focus.data.model.SharedPreferenceAccessUtils;

import java.util.TimerTask;

public class updateTextField {
    public static void updateText(final TextView textView) {
        String text = "";
        if (textView.getId() == R.id.start_time) {
            text += SharedPreferenceAccessUtils.getTimeIntervalStart(textView.getContext());
        } else {
            text += SharedPreferenceAccessUtils.getTimeIntervalEnd(textView.getContext());
        }
        textView.setText(text + " ");
    }
}