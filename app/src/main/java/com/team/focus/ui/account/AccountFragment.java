package com.team.focus.ui.account;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.team.focus.R;
import com.team.focus.data.model.SharedPreferenceAccessUtils;
import com.team.focus.ui.utils.updateTextField;

public class AccountFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_account, container, false);

        final Switch notification = root.findViewById(R.id.switch2);
        final TextView textStart = root.findViewById(R.id.start_time);
        final TextView textEnd = root.findViewById(R.id.end_time);
        updateTextField.updateText(textStart);
        updateTextField.updateText(textEnd);

        notification.setChecked(SharedPreferenceAccessUtils.getNotification(root.getContext()));
        return root;
    }
}