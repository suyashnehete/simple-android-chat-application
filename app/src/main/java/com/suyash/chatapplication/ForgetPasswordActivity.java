package com.suyash.chatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText email;
    Button sendBtn;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        email = findViewById(R.id.email);
        sendBtn = findViewById(R.id.send_btn);

        mAuth = FirebaseAuth.getInstance();

        getSupportActionBar().setTitle("Reset Password");

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });

    }

    private void sendEmail(){
        String e = email.getText().toString().trim();
        if (TextUtils.isEmpty(e)){
            Toast.makeText(this, "Please Enter Email Address", Toast.LENGTH_SHORT).show();
        }else if(!Patterns.EMAIL_ADDRESS.matcher(e).matches()){
            Toast.makeText(this, "Please Enter Valid Email Address", Toast.LENGTH_SHORT).show();
        }else{
            mAuth.sendPasswordResetEmail(e)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ForgetPasswordActivity.this, "Email Sent Successfully", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(ForgetPasswordActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}