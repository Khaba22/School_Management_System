package com.example.lmsshine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
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

public class EducatorLogin extends AppCompatActivity {

  Button signInBtn;
  CheckBox checkBox;
  TextView usernameTV, passwordTV, forgotPasswordTV;
  ProgressDialog dialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // line hide status bar from the screen
    getWindow()
        .setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_educator_login);

    dialog = new ProgressDialog(this);
    dialog.setMessage("Please Wait...");
    dialog.setCancelable(false);

    // Hookes
    signInBtn = findViewById(R.id.signIn);
    usernameTV = findViewById(R.id.userName);
    passwordTV = findViewById(R.id.password);

    checkBox = findViewById(R.id.checkBox);
    checkBox.setOnCheckedChangeListener(
        new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
              passwordTV.setTransformationMethod(null);
            } else {
              passwordTV.setTransformationMethod(new PasswordTransformationMethod());
            }
          }
        });

    forgotPasswordTV = findViewById(R.id.forgotPassword);
    forgotPasswordTV.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intent = new Intent(EducatorLogin.this, ForgotPassword.class);
            startActivity(intent);
          }
        });
  }

  private Boolean validateUsername() {
    String val = usernameTV.getText().toString().toUpperCase().trim();

    if (val.isEmpty()) {
      usernameTV.setError("Field can not be empty");
      return false;
    } else {
      usernameTV.setError(null);
      return true;
    }
  }

  private Boolean validatePassword() {
    String val = passwordTV.getText().toString().trim();

    if (val.isEmpty()) {
      passwordTV.setError("Field can not be empty");
      return false;
    } else if (val.length() < 6) {
      passwordTV.setError("Enter at least 6 characters");
      return false;
    } else {
      passwordTV.setError(null);
      return true;
    }
  }

  public void loginUser(View view) {

    // Validate login info

    if (!validateUsername() | !validatePassword()) {
      return;
    } else {
      isUser();
    }
  }

  private void isUser() {

    dialog.show();

    String userEnteredUsername = usernameTV.getText().toString().toUpperCase().trim();
    String userEnteredPassword = passwordTV.getText().toString().trim();

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users/Educator");

    // Check if user username entered exists in DB
    Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);

    checkUser.addListenerForSingleValueEvent(
        new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {

            if (snapshot.exists()) {

              usernameTV.setError(null);
              usernameTV.requestFocus();

              String passwordFromDB =
                  snapshot.child(userEnteredUsername).child("password").getValue(String.class);

              if (passwordFromDB.equals(userEnteredPassword)) {

                Toast.makeText(EducatorLogin.this, "Successfully Logged In", Toast.LENGTH_SHORT)
                    .show();

                usernameTV.setError(null);
                usernameTV.requestFocus();

                String nameFromDB =
                    snapshot.child(userEnteredUsername).child("name").getValue(String.class);
                String userNameFromDB =
                    snapshot.child(userEnteredUsername).child("username").getValue(String.class);
                String sNameFromDB =
                    snapshot.child(userEnteredUsername).child("sName").getValue(String.class);
                String emailFromDB =
                    snapshot.child(userEnteredUsername).child("email").getValue(String.class);

                Intent intent = new Intent(getApplicationContext(), EducatorMainPanel.class);
                intent.putExtra("username", userNameFromDB);
                intent.putExtra("name", nameFromDB);
                intent.putExtra("sName", sNameFromDB);
                intent.putExtra("email", emailFromDB);
                intent.putExtra("password", passwordFromDB);

                dialog.dismiss();
                startActivity(intent);
                finish();

              } else {
                passwordTV.setError("Wrong Password");
                passwordTV.requestFocus();
                dialog.dismiss();
              }

            } else {
              Toast.makeText(
                      EducatorLogin.this, "Not registered. Please Register", Toast.LENGTH_SHORT)
                  .show();
              dialog.dismiss();
            }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {}
        });
  }
}
