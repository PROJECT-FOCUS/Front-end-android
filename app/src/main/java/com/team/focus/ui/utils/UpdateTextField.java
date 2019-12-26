package com.team.focus.ui.utils;

import android.widget.TextView;

import com.team.focus.R;
import com.team.focus.data.model.SharedPreferenceAccessUtils;

public class UpdateTextField {
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