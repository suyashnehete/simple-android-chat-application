package com.suyash.chatapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    TextView forgetPassword, registerNow;
    EditText userName, userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        forgetPassword = findViewById(R.id.forget_password);
        registerNow = findViewById(R.id.new_user);
        userName = findViewById(R.id.user_email);
        userPassword = findViewById(R.id.user_password);

    }
}