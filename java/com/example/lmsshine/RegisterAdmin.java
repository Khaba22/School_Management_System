package com.example.lmsshine;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;

public class RegisterAdmin extends AppCompatActivity implements View.OnClickListener {
  FirebaseAuth mAuth;
  DatabaseReference userRef;
  FirebaseFirestore mStore;

  TextView banner, registerAdmin;
  EditText editTextFullName, editTextSchoolName, editTextEmail, editTextPassword;
  ProgressBar progressBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register_admin);

    mAuth = FirebaseAuth.getInstance();
    userRef = FirebaseDatabase.getInstance().getReference("Users/Admin");
    mStore = FirebaseFirestore.getInstance();

    banner = (TextView)findViewById(R.id.banner);
    banner.setOnClickListener(view -> {
      startActivity(new Intent(RegisterAdmin.this, AdminLogin.class));
    });

    registerAdmin = (Button)findViewById(R.id.registerAdmin);
    registerAdmin.setOnClickListener(view -> {
      registerAdmin();
    });

    editTextFullName = (EditText) findViewById(R.id.fullName);
    editTextSchoolName = (EditText) findViewById(R.id.schoolName);
    editTextEmail = (EditText) findViewById(R.id.email);
    editTextPassword = (EditText) findViewById(R.id.password);

    progressBar = (ProgressBar) findViewById(R.id.progressBar);
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
        startActivity(new Intent(this, AdminLogin.class));
        finish();
        break;
      case R.id.registerAdmin:
        registerAdmin();
        break;
    }
    finish();

  }

  private void registerAdmin() {
    String fullName = editTextFullName.getText().toString().trim();
    String schoolName = editTextSchoolName.getText().toString().trim();
    String email = editTextEmail.getText().toString().trim();
    String password = editTextPassword.getText().toString().trim();
    String type = "Admin";


    if (fullName.isEmpty()) {
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

             User user = new User( fullName, schoolName, email, type );
             userRef.push().setValue(user);
             Toast.makeText(RegisterAdmin.this, "Admin Added to database", Toast.LENGTH_SHORT).show();

             Toast.makeText(RegisterAdmin.this, "Admin registered successfully!", Toast.LENGTH_SHORT).show();
             startActivity(new Intent(RegisterAdmin.this, AdminLogin.class));
             finish();

           }
           else {
             Toast.makeText(RegisterAdmin.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
           }
         }
      });
    }
  }
}