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
import com.example.nzta_booking_app.models.Instructor;

import com.example.nzta_booking_app.models.Licence;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
                fname = ed_fname.getText().toString();
                lname = ed_lname.getText().toString();
                licence = ed_licence.getText().toString();
                email = ed_email.getText().toString();
                pass = ed_pass.getText().toString();
                pass2 = ed_pass2.getText().toString();

                if (TextUtils.isEmpty(fname) || TextUtils.isEmpty(lname)|| TextUtils.isEmpty(licence) || TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
                    Toast.makeText(Instructor_Registration.this, "Registration form incomplete", Toast.LENGTH_SHORT).show();
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
                                else{Toast.makeText(Instructor_Registration.this, "Registration failed.", Toast.LENGTH_SHORT).show();}
                            } else {Toast.makeText(Instructor_Registration.this, "Password or Email Validation failed.", Toast.LENGTH_SHORT).show();}
                        }
                    });
                }
            }
        });
    }
//
//else if (!licence.isEmpty()) {
//        DatabaseReference licenseRef = FirebaseDatabase.getInstance().getReference("licenses");
//        licenseRef.orderByChild("licenceNumber").equalTo(licence).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (!snapshot.exists()) {
//                    Toast.makeText(getApplicationContext(), "Licence does not exist!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Licence licence = snapshot.getValue(Licence.class);
//                    int passedTests = licence.getPassedTests();
//                    if (passedTests < 3) {
//                        Toast.makeText(getApplicationContext(), "You are not qualified to be instructor", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {}
//        });
//    }

    public void instructorLogin(View view) {
        Intent ilIntent = new Intent(this, Instructor_Login.class);
        startActivity(ilIntent);
    }
}