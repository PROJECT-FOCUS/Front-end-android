package com.team.focus.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.team.focus.R;
import com.team.focus.data.model.SharedPreferenceAccessUtils;
import com.team.focus.ui.utils.UpdateTextField;

public class AccountFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_account, container, false);

        final Switch notification = root.findViewById(R.id.switch2);
        final Switch isActiveModeSwitch = root.findViewById(R.id.switch_active_mode);
        final ConstraintLayout layoutEnd = root.findViewById(R.id.end_time_bar);
        final TextView textStart = root.findViewById(R.id.start_time);
        final TextView textEnd = root.findViewById(R.id.end_time);

        boolean isActiveMode = SharedPreferenceAccessUtils.getIsActiveMode(root.getContext());

        UpdateTextField.updateText(textStart);
        UpdateTextField.updateText(textEnd);

        notification.setChecked(SharedPreferenceAccessUtils.getNotification(root.getContext()));
        isActiveModeSwitch.setChecked(isActiveMode);

        if (!isActiveMode) {
            textEnd.setTextColor(root.getResources().getColor(R.color.light_gray));
            layoutEnd.setClickable(false);
        }

        isActiveModeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isActiveMode = isActiveModeSwitch.isChecked();
                boolean prev = SharedPreferenceAccessUtils.updateIsActiveMode(v.getContext(), isActiveMode);
                if (prev) {
                    textEnd.setTextColor(v.getResources().getColor(R.color.light_gray));
                    layoutEnd.setClickable(false);
                    Toast.makeText(v.getContext(), "Switch to one-day mode", Toast.LENGTH_SHORT).show();
                } else {
                    textEnd.setTextColor(v.getResources().getColor(R.color.black));
                    layoutEnd.setClickable(true);
                    Toast.makeText(v.getContext(), "Switch to active hours mode", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;
    }
}