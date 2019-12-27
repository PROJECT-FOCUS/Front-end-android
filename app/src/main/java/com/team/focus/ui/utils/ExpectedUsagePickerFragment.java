package com.team.focus.ui.utils;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import com.team.focus.data.model.Usage;

public class ExpectedUsagePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    private TextView textView;
    private Usage cache;

    public ExpectedUsagePickerFragment(TextView textView, Usage cache) {
        this.textView = textView;
        this.cache = cache;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, cache.getHour(),
                cache.getMinute(), true);
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        cache.setHour(hourOfDay);
        cache.setMinute(minute);
        UpdateTextField.updateUsageTextView(textView, new Usage(hourOfDay, minute));
    }
}
