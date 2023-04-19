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

import com.example.nzta_booking_app.Landing_Page;
import com.example.nzta_booking_app.R;
import com.example.nzta_booking_app.instructor.Instructor_Login;
import com.example.nzta_booking_app.instructor.Instructor_Registration;
import com.example.nzta_booking_app.models.Controller;
import com.example.nzta_booking_app.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Normal_Login extends AppCompatActivity {

    EditText l_email, l_pass;
    Button login;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_login);
        l_email = findViewById(R.id.email);
        l_pass = findViewById(R.id.pass);
        login = findViewById(R.id.loginBtn);
        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, pass;
                email = l_email.getText().toString();
                pass = l_pass.getText().toString();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
                    Toast.makeText(Normal_Login.this, "Login form incomplete", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                checkLoggedInUser();
                            } else {
                                Toast.makeText(Normal_Login.this, "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }


    public void checkLoggedInUser() {
        String userID = mAuth.getCurrentUser().getUid();

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userID);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Controller.setCurrentUser(dataSnapshot.getValue(User.class));
                // retrieve the user object
                if (Controller.getCurrentUser() != null) {
                    finishAffinity();
                    Intent intent = new Intent(Normal_Login.this, Normal_Home.class);
                    startActivity(intent);
                    Toast.makeText(Normal_Login.this, "you are logged in successfully!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Normal_Login.this, "Road User account does not exist", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Normal_Login.this, "Error Occurred: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

    public void landingPage(View view) {
        Intent lpIntent = new Intent(this, Landing_Page.class);
        startActivity(lpIntent);
    }


    public void normalRegister(View view) {
        Intent nrIntent = new Intent(this, Normal_Registration.class);
        startActivity(nrIntent);
    }

    public void instructorLogin(View view) {
        Intent ilIntent = new Intent(Normal_Login.this, Instructor_Login.class);
        startActivity(ilIntent);
    }

    public void instructorRegister(View view) {
        Intent irIntent = new Intent(Normal_Login.this, Instructor_Registration.class);
        startActivity(irIntent);
    }
}