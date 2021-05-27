package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignUp_Form extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__form);
        setTitle("Sign Up ");

        final String TAG = "YOUR-TAG-NAME";
        Button register = findViewById(R.id.register);
        TextView back = findViewById(R.id.back);
        final EditText username = findViewById(R.id.username);
        final EditText password = findViewById(R.id.password);
        final EditText confirmpassword = findViewById(R.id.confirmpassword);
        final EditText emailId = findViewById(R.id.emailId);
        final View progressbar = findViewById(R.id.progressbar);
        final FirebaseAuth fAuth = FirebaseAuth.getInstance();
        final FirebaseFirestore db= FirebaseFirestore.getInstance() ;


        if(fAuth.getCurrentUser()!=null){
            Intent main_activity = new Intent(SignUp_Form.this, MainActivity.class);
            startActivity(main_activity);
        }



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main_activity = new Intent(SignUp_Form.this,LogIn_Form.class);
                startActivity(main_activity);
            }
        });



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username1 = username.getText().toString().trim();
                final String emailId1 = emailId.getText().toString().trim();
                String password1 = password.getText().toString().trim();
                String confirmpassword1 = confirmpassword.getText().toString().trim();

                if (TextUtils.isEmpty(username1))
                {
                    username.setError("Username is required");
                    return;
                }
                if (TextUtils.isEmpty(emailId1))
                {
                    emailId.setError("Email Id is required");
                    return;
                }
                if (password1.length()<8)
                {
                    password.setError("Password is required to have more than or equal to 8 Characters");
                    return;
                }
                if (!password1.equals(confirmpassword1))
                {
                    confirmpassword.setError("ConfirmPassword and Password are different");
                    return;
                }



                fAuth.createUserWithEmailAndPassword(emailId1,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        final String userID = fAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = db.collection("users").document(userID);

                        Map<String, Object> user = new HashMap<>();
                        user.put("Username", username1);
                        user.put("EmailId",emailId1);
                        documentReference.set(user)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("TAG","Onsuccess: User profile is created for"+userID);
                                    }

                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("TAG","on Failure"+e.toString());
                            }
                        });


                        Toast. makeText(getApplicationContext(),"Registration Succesfull ",Toast. LENGTH_SHORT).show();
                        Intent main_activity = new Intent(SignUp_Form.this, MainActivity.class);
                        startActivity(main_activity);
                    }
                    else{
                        Toast. makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage(),Toast. LENGTH_SHORT).show();
                    }
                    }
                });

                }
            });


        }




}
