package com.example.lmsshine;

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

import androidx.appcompat.app.AppCompatActivity;

public class AdministratorLogin extends AppCompatActivity {

  Button signInBtn;
  CheckBox checkBox;
  TextView usernameTV, passwordTV;
  ProgressDialog dialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // line hide status bar from the screen
    getWindow()
        .setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_adminstrator_login);

    dialog = new ProgressDialog(this);
    dialog.setMessage("Please Wait...");
    dialog.setCancelable(false);

    // Hooks
    signInBtn = findViewById(R.id.signIn);
    usernameTV = findViewById(R.id.userName);
    passwordTV = findViewById(R.id.password);

    checkBox = findViewById(R.id.checkBox);
    checkBox.setOnCheckedChangeListener(
            (buttonView, isChecked) -> {
              if (isChecked) {
                passwordTV.setTransformationMethod(null);
              } else {
                passwordTV.setTransformationMethod(new PasswordTransformationMethod());
                String v = usernameTV.getText().toString().toUpperCase().trim();
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

    dialog.show();

    // Validate login info

    if (!validateUsername() | !validatePassword()) {
      dialog.dismiss();
    } else {
      isUser();
    }
  }

  private void isUser() {

    dialog.show();

    String userEnteredUsername = usernameTV.getText().toString().toUpperCase().trim();
    String userEnteredPassword = passwordTV.getText().toString().trim();

    if ((userEnteredUsername.equals("ADMIN")) && (userEnteredPassword.equals("UZ_Adm1n"))) {
      Intent intent = new Intent(AdministratorLogin.this, AdministratorMainPanel.class);
      startActivity(intent);
      finish();
    } else {
      if (!userEnteredPassword.equals("UZ_Adm1n")) {
        Toast.makeText(this, "Wrong Password", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
      }
    }
  }
}
