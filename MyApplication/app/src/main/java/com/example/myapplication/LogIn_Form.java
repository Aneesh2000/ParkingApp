package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import java.util.Objects;

public class LogIn_Form extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in__form);
        setTitle("Log In ");
        checkPermission();

        final EditText emailId = findViewById(R.id.emailId);
        final EditText password = findViewById(R.id.password);
        Button login = findViewById(R.id.login);
        TextView signup = findViewById(R.id.signup);
        final View progressbar = findViewById(R.id.progressbar);
        final FirebaseAuth fAuth = FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser() != null) {
            Intent main_activity = new Intent(LogIn_Form.this, MainActivity.class);
            startActivity(main_activity);
        }


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main_activity = new Intent(LogIn_Form.this, SignUp_Form.class);
                startActivity(main_activity);
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailId1 = emailId.getText().toString().trim();
                final String password1 = password.getText().toString().trim();

                if (TextUtils.isEmpty(emailId1)) {
                    emailId.setError("Email Id is required");
                }
                if (password1.length() < 8) {
                    password.setError("Password is required to have more than or equal to 8 Characters");
                }
                progressbar.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(emailId1, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Log In Succesfull ", Toast.LENGTH_SHORT).show();
                            Intent main_activity = new Intent(LogIn_Form.this, MainActivity.class);
                            startActivity(main_activity);
                        } else {
                            Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(LogIn_Form.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            int LOCATION_PERMISSION = 1;
            ActivityCompat.requestPermissions(LogIn_Form.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION);

        }
    }
}

