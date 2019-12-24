package com.team.focus;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.team.focus.R;
import com.team.focus.data.model.LoggedInUser;
import com.team.focus.data.model.SharedPreferenceAccessUtils;
import com.team.focus.data.pipeline.Account;
import com.team.focus.ui.customfonts.EditText_SF_Pro_Display_Medium;
import com.team.focus.ui.utils.EditTextValidator;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final EditText userFirstName = findViewById(R.id.first_name);
        final EditText userLastName = findViewById(R.id.last_name);
        final EditText usernameEditText = findViewById(R.id.username_register);
        final EditText passwordEditText = findViewById(R.id.password_register);
        final Button registerButton = findViewById(R.id.register);
        final TextView login = findViewById(R.id.switch2Login);

        usernameEditText.addTextChangedListener(new EditTextValidator(usernameEditText) {
            @Override
            public void validate(TextView textView, String text) {
                if (text == null) {
                    textView.setError(textView.getContext().getString(R.string.invalid_username));
                    registerButton.setClickable(false);
                    return;
                }
                if (text.contains("@") && !Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
                    textView.setError(textView.getContext().getString(R.string.invalid_username));
                    registerButton.setClickable(false);
                } else if (text.trim().isEmpty()) {
                    textView.setError(textView.getContext().getString(R.string.invalid_username));
                    registerButton.setClickable(false);
                } else {
                    registerButton.setClickable(true);
                }
            }
        });

        passwordEditText.addTextChangedListener(new EditTextValidator(passwordEditText) {
            @Override
            public void validate(TextView textView, String text) {
                if (!(text != null && text.trim().length() > 5)) {
                    textView.setError(textView.getContext().getString(R.string.invalid_password));
                    registerButton.setClickable(false);
                } else {
                    registerButton.setClickable(true);
                }
            }
        }) ;


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(intent);
                setResult(Activity.RESULT_OK);
                finish();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoggedInUser user = Account.register(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(), userFirstName.getText().toString(),
                        userLastName.getText().toString());
                if (user == null) {
                    Toast.makeText(getApplicationContext(), "Register Failed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    SharedPreferenceAccessUtils.setLoginUser(v.getContext(), user);
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    startActivity(intent);
                    setResult(Activity.RESULT_OK);
                    finish();
                }
            }
        });
    }
}
