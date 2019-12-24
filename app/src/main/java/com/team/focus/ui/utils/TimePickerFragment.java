package com.team.focus.ui.utils;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import com.team.focus.R;
import com.team.focus.data.model.SharedPreferenceAccessUtils;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    private Context parentContext;
    private boolean isStartTime;

    public TimePickerFragment(Context context, boolean isStartTime) {
        parentContext = context;
        this.isStartTime = isStartTime;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int hour = isStartTime ? SharedPreferenceAccessUtils.getTimeIntervalStart(parentContext):
                SharedPreferenceAccessUtils.getTimeIntervalEnd(parentContext);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, 0,
                true);
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (isStartTime) {
            SharedPreferenceAccessUtils.updateTimeIntervalStart(parentContext, hourOfDay);
        } else {
            SharedPreferenceAccessUtils.updateTimeIntervalEnd(parentContext, hourOfDay);
        }
    }
}
