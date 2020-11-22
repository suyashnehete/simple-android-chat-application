package com.suyash.chatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextView forgetPassword, registerNow;
    EditText userName, userPassword;
    Button loginBtn;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        forgetPassword = findViewById(R.id.forget_password);
        registerNow = findViewById(R.id.new_user);
        userName = findViewById(R.id.user_email);
        userPassword = findViewById(R.id.user_password);
        loginBtn = findViewById(R.id.login_user);

        mAuth = FirebaseAuth.getInstance();

        registerNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser(userName.getText().toString().trim(), userPassword.getText().toString().trim());
            }
        });

    }

    private void loginUser(String email, String password){
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please Enter Email Address", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
        }else{
            final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
            dialog.setMessage("Authenticating...");
            dialog.setCancelable(false);
            dialog.show();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(i);
                                Toast.makeText(LoginActivity.this, "Login Successfull", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(LoginActivity.this, "Invalid Email or Password", Toast.LENGTH_LONG).show();
                            }
                            dialog.dismiss();
                        }
                    });

        }
    }
}