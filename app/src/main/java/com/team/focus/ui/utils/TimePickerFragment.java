package com.team.focus.ui.utils;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.team.focus.R;
import com.team.focus.data.model.SharedPreferenceAccessUtils;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    private Context parentContext;
    private boolean isStartTime;
    private TextView view;

    public TimePickerFragment(Context context, boolean isStartTime, TextView view) {
        parentContext = context;
        this.isStartTime = isStartTime;
        this.view = view;
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
            int endTime = SharedPreferenceAccessUtils.getTimeIntervalEnd(parentContext);
            if (endTime >= hourOfDay) {
                SharedPreferenceAccessUtils.updateTimeIntervalStart(parentContext, hourOfDay);
                updateTextField.updateText(this.view);
                Toast.makeText(parentContext, "Start time changed to " + hourOfDay,
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(parentContext, "Start time must be earlier than end",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            int startTime = SharedPreferenceAccessUtils.getTimeIntervalStart(parentContext);
            hourOfDay = hourOfDay == 0 ? 24 : hourOfDay;
            if (startTime <= hourOfDay) {
                SharedPreferenceAccessUtils.updateTimeIntervalEnd(parentContext, hourOfDay);
                updateTextField.updateText(this.view);
                Toast.makeText(parentContext, "End time changed to " + hourOfDay,
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(parentContext, "End time must be later than start",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
