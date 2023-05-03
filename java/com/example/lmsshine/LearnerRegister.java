package com.example.lmsshine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class LearnerRegister extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth mAuth;

    TextView banner, registerAdmin;
    EditText editTextFullName, editTextSchoolName, editTextEmail, editTextPassword;
    ProgressBar progressBar;

    DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learner_register);

        mAuth = FirebaseAuth.getInstance();

        userRef = FirebaseDatabase.getInstance().getReference("Users/Learner");

        banner = (TextView)findViewById(R.id.banner);
        banner.setOnClickListener(view -> {
            startActivity(new Intent(LearnerRegister.this, LearnerLogin.class));
        });

        registerAdmin = (Button)findViewById(R.id.registerAdmin);
        registerAdmin.setOnClickListener(view -> {
            registerLearner();
        });

        editTextFullName = (EditText) findViewById(R.id.fullName);
        editTextSchoolName = (EditText) findViewById(R.id.schoolName);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

    }

    private void registerLearner() {

        String fullName = editTextFullName.getText().toString().trim();
        String schoolName = editTextSchoolName.getText().toString().toUpperCase().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String types = "Learner";

        if (fullName.isEmpty() && schoolName.isEmpty() && email.isEmpty() && password.isEmpty() ) {
            editTextFullName.setError("Enter Full Name");
            editTextSchoolName.setError("Enter School Name");
            editTextEmail.setError("Enter Email Address");
            editTextPassword.setError("Enter Password");
        }

        else if (fullName.isEmpty()) {
            editTextFullName.setError("Enter Full Name!");
            editTextFullName.requestFocus();
            return;
        }

        else if (schoolName.isEmpty()) {
            editTextSchoolName.setError("Enter School Name!");
            editTextSchoolName.requestFocus();
            return;
        }

        else if (email.isEmpty()) {
            editTextEmail.setError("Enter email!");
            editTextEmail.requestFocus();
            return;
        }

        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter valid email!");
            editTextEmail.requestFocus();
            return;
        }

        else if (password.isEmpty()) {
            editTextPassword.setError("Enter Password!");
            editTextPassword.requestFocus();
            return;
        }

        else if (password.length() < 6) {
            editTextPassword.setError("Enter at least 6 characters!");
            editTextPassword.requestFocus();
            return;
        }
        else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        progressBar.setVisibility(View.VISIBLE);

                        User user = new User( fullName, schoolName, email, password, types );

                        //userRef.child(Objects.requireNonNull(mAuth.getUid())).setValue(user);
                        userRef.push().setValue(user);
                        //Toast.makeText(LearnerRegister.this, "Learner Added to database", Toast.LENGTH_SHORT).show();
                        Toast.makeText(LearnerRegister.this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LearnerRegister.this, LearnerLogin.class));
                        finish();

                    }
                    else {
                        Toast.makeText(LearnerRegister.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            return;
        }
    }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.banner:
        startActivity(new Intent(this, LearnerLogin.class));
        finish();
        break;
      case R.id.registerAdmin:
        registerLearner();
        break;
    }
    finish();
    }
}