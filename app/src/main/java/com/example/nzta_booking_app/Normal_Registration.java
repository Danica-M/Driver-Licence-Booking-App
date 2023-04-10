package com.example.nzta_booking_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nzta_booking_app.models.Controller;
import com.example.nzta_booking_app.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Normal_Registration extends AppCompatActivity {
    EditText ed_fname, ed_lname, ed_email, ed_pass, ed_pass2;
    Button registerBtn, cancelBtn;
    Controller controller;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_registration);
        ed_fname = findViewById(R.id.fname);
        ed_lname = findViewById(R.id.lname);
        ed_email = findViewById(R.id.email);
        ed_pass = findViewById(R.id.pass);
        ed_pass2 = findViewById(R.id.pass2);
        registerBtn = findViewById(R.id.registerBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        mAuth = FirebaseAuth.getInstance();
        controller = new Controller();
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname, lname, email, pass, pass2;
                fname = ed_fname.getText().toString();
                lname = ed_lname.getText().toString();
                email = ed_email.getText().toString();
                pass = ed_pass.getText().toString();
                pass2 = ed_pass2.getText().toString();

                if (TextUtils.isEmpty(fname) || TextUtils.isEmpty(lname) || TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
                    Toast.makeText(Normal_Registration.this, "Registration form incomplete", Toast.LENGTH_SHORT).show();
                } else if (!pass.equals(pass2)) {
                    Toast.makeText(Normal_Registration.this, "Password does not match", Toast.LENGTH_SHORT).show();
                } else {

                    mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        /**
                                         * user object userid is the same as the authentication userid
                                         * if user is successfully added using the registerUser method in controller
                                         * user will be redirected to the login page
                                         */

                                        String userid = mAuth.getCurrentUser().getUid();
                                        User newUser = controller.registerUser(userid, fname, lname, "", email, pass);
                                        if (newUser != null) {
                                            Toast.makeText(Normal_Registration.this, "Registration Successful.", Toast.LENGTH_SHORT).show();
                                            FirebaseAuth.getInstance().signOut();
                                            Intent nlIntent = new Intent(Normal_Registration.this, Normal_Login.class);
                                            startActivity(nlIntent);
                                        }
                                        else{
                                            Toast.makeText(Normal_Registration.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                                        }

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(Normal_Registration.this, "Password or Email Validation failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
    public void normalLogin(View view) {
        Intent nlIntent = new Intent(this, Normal_Login.class);
        startActivity(nlIntent);
    }
}