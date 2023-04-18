package com.example.nzta_booking_app.instructor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nzta_booking_app.R;
import com.example.nzta_booking_app.models.Controller;
import com.example.nzta_booking_app.models.Instructor;

import com.example.nzta_booking_app.user.Normal_Registration;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class Instructor_Registration extends AppCompatActivity {
    EditText ed_fname, ed_lname, ed_licence, ed_email, ed_pass, ed_pass2;
    Button registerBtn, cancelBtn;
    Controller controller;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructor_registration);
        ed_fname = findViewById(R.id.fname);
        ed_lname = findViewById(R.id.lname);
        ed_email = findViewById(R.id.email);
        ed_pass = findViewById(R.id.pass);
        ed_pass2 = findViewById(R.id.pass2);
        ed_licence = findViewById(R.id.licence);

        registerBtn = findViewById(R.id.instructRegBtn);
        cancelBtn = findViewById(R.id.instructRegCanBtn);

        mAuth = FirebaseAuth.getInstance();
        controller = new Controller();
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname, lname, licence, email, pass, pass2;
                fname = ed_fname.getText().toString().toUpperCase().trim();
                lname = ed_lname.getText().toString().toUpperCase().trim();
                licence = ed_licence.getText().toString().toUpperCase().trim();
                email = ed_email.getText().toString();
                pass = ed_pass.getText().toString();
                pass2 = ed_pass2.getText().toString();

                if (TextUtils.isEmpty(fname) || TextUtils.isEmpty(lname)|| TextUtils.isEmpty(licence) || TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(licence)) {
                    Toast.makeText(Instructor_Registration.this, "Registration form incomplete", Toast.LENGTH_SHORT).show();
                } else if (!controller.instructorLicenceValidation(licence)) {
                    Toast.makeText(Instructor_Registration.this, "Looks like this licence is already in the system.", Toast.LENGTH_SHORT).show();
                }else if (!pass.equals(pass2)) {
                    Toast.makeText(Instructor_Registration.this, "Password does not match", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                /*instructor object instructorID is the same as the authentication userid
                                  if instructor is successfully added using the registerInstructor method in controller
                                  user will be redirected to the login page
                                 */
                                String userid = mAuth.getCurrentUser().getUid();
                                Instructor newInstructor = controller.registerInstructor(userid, fname, lname, licence, email, pass);
                                if (newInstructor != null) {
                                    Toast.makeText(Instructor_Registration.this, "Registration Successful.", Toast.LENGTH_SHORT).show();
                                    FirebaseAuth.getInstance().signOut();
                                    Intent nlIntent = new Intent(Instructor_Registration.this, Instructor_Login.class);
                                    startActivity(nlIntent);
                                }
                                else{
                                    Toast.makeText(Instructor_Registration.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                                    mAuth.getCurrentUser().delete();}
                            } else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    // Display Toast message if email is already registered
                                    Toast.makeText(Instructor_Registration.this, "Email already registered", Toast.LENGTH_SHORT).show();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Instructor_Registration.this, "Invalid Email or Password.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
            }
        });
    }


    public void instructorLogin(View view) {
        Intent ilIntent = new Intent(this, Instructor_Login.class);
        startActivity(ilIntent);
    }
}