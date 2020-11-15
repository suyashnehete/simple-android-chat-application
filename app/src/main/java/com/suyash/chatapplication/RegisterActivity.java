package com.suyash.chatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.suyash.chatapplication.Model.User;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    TextView loginNow;
    EditText userName, userEmail, userPassword;
    Button registerBtn;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loginNow = findViewById(R.id.old_user);
        userName = findViewById(R.id.user_name);
        userEmail = findViewById(R.id.user_email);
        userPassword = findViewById(R.id.user_password);
        registerBtn = findViewById(R.id.register_user);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        loginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser(userName.getText().toString().trim(), userEmail.getText().toString().trim(), userPassword.getText().toString().trim());
            }
        });

    }

    private void registerUser(final String name, final String email, String password){
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please Enter User Name", Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please Enter User Email", Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please Enter User Password", Toast.LENGTH_LONG).show();
        }else if(password.length() < 6){
            Toast.makeText(this, "Password length should be greater than 5", Toast.LENGTH_SHORT).show();
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Please enter valid email address", Toast.LENGTH_SHORT).show();
        }else{
            final ProgressDialog dialog = new ProgressDialog(RegisterActivity.this);
            dialog.setMessage("Registering...");
            dialog.setCancelable(false);
            dialog.show();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                FirebaseUser mUser = task.getResult().getUser();
                                mDatabase.child("Users").child(mUser.getUid()).setValue(
                                        new User(
                                                name,
                                                email,
                                                name.toLowerCase()
                                        )
                                );
                                Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                            dialog.dismiss();
                        }
                    });
        }
    }
}