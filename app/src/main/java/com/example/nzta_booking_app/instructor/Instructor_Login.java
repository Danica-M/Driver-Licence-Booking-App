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

import com.example.nzta_booking_app.user.Normal_Login;
import com.example.nzta_booking_app.user.Normal_Registration;
import com.example.nzta_booking_app.R;
import com.example.nzta_booking_app.models.Controller;
import com.example.nzta_booking_app.models.Instructor;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Instructor_Login extends AppCompatActivity {

    EditText l_email, l_pass;
    Button login;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructor_login);
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
                    Toast.makeText(Instructor_Login.this, "Login form incomplete", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                checkLoggedInInstructor();
                            } else {
                                Toast.makeText(Instructor_Login.this, "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    public void checkLoggedInInstructor() {
        String instructorID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        DatabaseReference insRef = FirebaseDatabase.getInstance().getReference().child("instructors").child(instructorID);
        insRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Controller.setCurrentInstructor(dataSnapshot.getValue(Instructor.class));
                if (Controller.getCurrentInstructor() != null) {
                    // retrieve the instructor object
                    finishAffinity();
                    Intent intent = new Intent(Instructor_Login.this, Instructor_Home.class);
                    startActivity(intent);
                    Toast.makeText(Instructor_Login.this, "You are logged in successfully!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Instructor_Login.this, "Instructor account does not exist", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Instructor_Login.this, "Error Occurred: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

    }

    public void normalRegister(View view) {
        Intent nrIntent = new Intent(this, Normal_Registration.class);
        startActivity(nrIntent);
    }

    public void normalLogin(View view) {
        Intent nrIntent = new Intent(this, Normal_Login.class);
        startActivity(nrIntent);
    }

    public void instructorRegister(View view) {
        Intent irIntent = new Intent(this, Instructor_Registration.class);
        startActivity(irIntent);
    }

}