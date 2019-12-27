package com.team.focus.ui.utils;

import android.widget.TextView;

import com.team.focus.R;
import com.team.focus.data.model.SharedPreferenceAccessUtils;
import com.team.focus.data.model.Usage;

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

    public static void updateUsageTextView(TextView textView, Usage usage) {
        textView.setText(usage.toString());
    }
}