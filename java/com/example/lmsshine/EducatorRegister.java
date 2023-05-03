package com.example.lmsshine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EducatorRegister extends AppCompatActivity {

  TextView regName, regUsername, regEmail, regSchName, regPassword, cPassword;
  Button regBtn;
  CheckBox checkBox;
  ProgressDialog dialog;

  FirebaseDatabase rootNode;
  DatabaseReference reference;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_educator_register);

    dialog = new ProgressDialog(this);
    dialog.setMessage("Please Wait...");
    dialog.setCancelable(false);

    // Hooks
    regName = findViewById(R.id.fullName);
    regUsername = findViewById(R.id.userName);
    regEmail = findViewById(R.id.email);
    regSchName = findViewById(R.id.schoolName);
    regPassword = findViewById(R.id.password);
    cPassword = findViewById(R.id.c_password);
    regBtn = findViewById(R.id.btn_register);

    checkBox = findViewById(R.id.checkBox);
    checkBox.setOnCheckedChangeListener(
            (buttonView, isChecked) -> {
              if (isChecked) {
                regPassword.setTransformationMethod(null);
                cPassword.setTransformationMethod(null);
              } else {
                regPassword.setTransformationMethod(new PasswordTransformationMethod());
                cPassword.setTransformationMethod(new PasswordTransformationMethod());
              }
            });

    rootNode = FirebaseDatabase.getInstance();
    reference = rootNode.getReference("Users/Educator");
  }

  // Validate entered data
  private Boolean validateName() {

    dialog.show();

    String val = regName.getText().toString().trim();

    if (val.isEmpty()) {
      regName.setError("Field Can Not Be Empty");
      dialog.dismiss();
      return false;
    } else if (val.length() > 30) {
      regName.setError("Field Too Long");
      dialog.dismiss();
      return false;
    } else {
      regName.setError(null);
      return true;
    }
  }

  private Boolean validateUsername() {

    dialog.show();

    String val = regUsername.getText().toString().trim();
    String noWhiteSpace = "(?=\\s+$)";

    if (val.isEmpty()) {
      regUsername.setError("Field Can Not Be Empty");
      dialog.dismiss();
      return false;
    } else if (val.length() > 15) {
      regUsername.setError("Username too long");
      dialog.dismiss();
      return false;
    } else if (val.matches(noWhiteSpace)) {
      regUsername.setError("Whitespaces Are Not Allowed");
      return false;
    } else {
      regUsername.setError(null);
      return true;
    }
  }

  private Boolean validateEmail() {
    String val = regEmail.getText().toString().trim();
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+";

    if (val.isEmpty()) {
      regEmail.setError("Field Can Not Be Empty");
      return false;
    } else if (!val.matches(emailPattern)) {
      regEmail.setError("Invalid email address");
      return false;
    } else {
      regEmail.setError(null);
      return true;
    }
  }

  private Boolean validateSchName() {

    dialog.show();

    String val = regSchName.getText().toString().trim();

    if (val.isEmpty()) {
      regSchName.setError("Field Can Not Be Empty");
      dialog.dismiss();
      return false;
    } else if (val.length() > 40) {
      regSchName.setError("Field Too Long");
      dialog.dismiss();
      return false;
    } else {
      regSchName.setError(null);
      return true;
    }
  }

  private Boolean validatePassword() {
    String val = regPassword.getText().toString().trim();
    String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{6,12}$";

    if (val.isEmpty()) {
      regPassword.setError("Field can not be empty");
      return false;
    } else if (val.length() < 6) {
      regPassword.setError("Enter at least 6 characters");
      return false;
    } else if (val.length() > 12) {
      regPassword.setError("Field Too Long");
      return false;
    } else if (!val.matches(passwordPattern)) {
      regPassword.setError(
          "Password Must Also Have:"
              + "\n 1 Alphabet "
              + "\n 1 Number "
              + "\n 1 Special Character");
      return false;
    } else {
      regPassword.setError(null);
      return true;
    }
  }

  private Boolean confirmPassword() {

    dialog.show();

    String password = regPassword.getText().toString().trim();
    String c_password = cPassword.getText().toString().trim();

    if (c_password.isEmpty()) {
      cPassword.setError("Field Can Not Be Empty");
      dialog.dismiss();
      return false;
    } else if (!password.equals(c_password)) {
      cPassword.setError("Passwords Don't Match");
      dialog.dismiss();
      return false;
    } else {
      cPassword.setError(null);
      dialog.dismiss();
      return true;
    }
  }

  // Save data in Firebase on button click
  public void registerUser(View view) {

    dialog.show();

    // Get all values from EditText in String
    String name = regName.getText().toString().toUpperCase().trim();
    String username = regUsername.getText().toString().toUpperCase().trim();
    String email = regEmail.getText().toString().toLowerCase().trim();
    String schoolName = regSchName.getText().toString().toUpperCase().trim();
    String password = regPassword.getText().toString().trim();

    // Check if user filled all the fields
    if (!validateName()
        | !validateUsername()
        | !validateEmail()
        | !validateSchName()
        | !validatePassword()
        | !confirmPassword()) {
      dialog.dismiss();
    } else {

      Query checkUser = reference.orderByChild("username").equalTo(username);

      // Save data in Database, using username as unique identifier
      checkUser.addListenerForSingleValueEvent(
          new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

              // Check if user username exist or not
              if (snapshot.exists()) {

                Toast.makeText(EducatorRegister.this, "Username is registered", Toast.LENGTH_SHORT)
                    .show();
                dialog.dismiss();

              } else {

                // Register user in DB
                EducatorClass  educatorClass =
                    new EducatorClass(name, username, email, schoolName, password);
                reference.child(username).setValue(educatorClass);
                Toast.makeText(EducatorRegister.this, "Registered Successfully", Toast.LENGTH_SHORT)
                    .show();
                dialog.dismiss();
                finish();
              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
          });
    }
  }
}
