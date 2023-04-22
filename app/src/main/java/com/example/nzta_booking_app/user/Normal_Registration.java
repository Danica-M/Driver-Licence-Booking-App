package com.example.nzta_booking_app.user;

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
import com.example.nzta_booking_app.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class Normal_Registration extends AppCompatActivity {
    EditText edNameF, edNameL, edEmail, edPass, edPass2, edLicence;
    Button registerBtn, cancelBtn;
    Controller controller;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_registration);
        edNameF = findViewById(R.id.fname);
        edNameL = findViewById(R.id.lname);
        edEmail = findViewById(R.id.email);
        edPass = findViewById(R.id.pass);
        edPass2 = findViewById(R.id.pass2);
        edLicence = findViewById(R.id.licence);
        registerBtn = findViewById(R.id.registerBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        mAuth = FirebaseAuth.getInstance();
        controller = new Controller();


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fName, lName, licence, email, pass, pass2;
                fName = edNameF.getText().toString().toUpperCase().trim();
                lName = edNameL.getText().toString().toUpperCase().trim();
                licence = edLicence.getText().toString().toUpperCase().trim();
                email = edEmail.getText().toString();
                pass = edPass.getText().toString();
                pass2 = edPass2.getText().toString();

                if (TextUtils.isEmpty(fName) || TextUtils.isEmpty(lName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(licence)) {
                    Toast.makeText(Normal_Registration.this, "Registration form incomplete", Toast.LENGTH_SHORT).show();
                } else if(!Controller.validateString(fName)){
                    Toast.makeText(Normal_Registration.this, "Invalid character in firstname field", Toast.LENGTH_SHORT).show();
                }else if(!Controller.validateString(lName)){
                    Toast.makeText(Normal_Registration.this, "Invalid character in lastname field", Toast.LENGTH_SHORT).show();
                } else if (!Controller.validateLicence(licence)) {
                    Toast.makeText(Normal_Registration.this, "Invalid licence format", Toast.LENGTH_SHORT).show();
                } else if (!pass.equals(pass2)) {
                    Toast.makeText(Normal_Registration.this, "Password does not match", Toast.LENGTH_SHORT).show();
                } else {
                   registerUser(fName, lName, licence, email, pass);
                }
            }
        });
    }

    //method if the user wants to login instead of register
    public void normalLogin(View view) {
        Intent nlIntent = new Intent(this, Normal_Login.class);
        startActivity(nlIntent);
    }

    //checks if the user inputted licence is already in the system
    //otherwise will register as new user
    public void registerUser(String fName, String lName, String licence, String email, String pass){
        DatabaseReference userRef = Controller.getReference().child("users");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int licenceCount = 0;
                for (DataSnapshot userItems : snapshot.getChildren()) {
                    User user = userItems.getValue(User.class);
                    if (user != null && user.getLicenceNum().equals(licence)) {
                        licenceCount++;
                    }
                }
                if (licenceCount > 0) {
                    Toast.makeText(Normal_Registration.this, "Looks like this licence is already in the system.", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                /*
                                  user object userid is the same as the authentication userid
                                  if user is successfully added using the registerUser method in controller
                                  user will be redirected to the login page
                                */
                                String userid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                                User newUser = controller.registerUser(userid, fName, lName, licence, email, pass);
                                if (newUser != null) {
                                    Toast.makeText(Normal_Registration.this, "Registration Successful.", Toast.LENGTH_SHORT).show();
                                    FirebaseAuth.getInstance().signOut();
                                    finishAffinity();
                                    Intent nlIntent = new Intent(Normal_Registration.this, Normal_Login.class);
                                    startActivity(nlIntent);
                                } else {
                                    Toast.makeText(Normal_Registration.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                                    mAuth.getCurrentUser().delete();
                                }
                            } else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    // Display Toast message if email is already registered
                                    Toast.makeText(getApplicationContext(), "Email already registered", Toast.LENGTH_SHORT).show();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Normal_Registration.this, "Invalid Email or Password.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplication(), "Error Occurred: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}