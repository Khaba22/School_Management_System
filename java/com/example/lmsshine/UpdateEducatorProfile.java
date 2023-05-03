package com.example.lmsshine;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateEducatorProfile extends AppCompatActivity {

  EditText upName, upSName, upEmail;
  Button btnUpdate;
  ProgressDialog dialog;

  FirebaseDatabase rootNode;
  DatabaseReference reference;

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_update_educator_profile);

    dialog = new ProgressDialog(this);
    dialog.setMessage("Please Wait...");
    dialog.setCancelable(false);

    // Hooks
    upName = findViewById(R.id.uFullName);
    upSName = findViewById(R.id.uSchoolName);
    upEmail = findViewById(R.id.uEmail);
    btnUpdate = findViewById(R.id.update);

    rootNode = FirebaseDatabase.getInstance();
    reference = rootNode.getReference("Users/Educator");

    Intent intent = getIntent();
    String user_name = intent.getStringExtra("name");
    String user_s_name = intent.getStringExtra("sName");
    String user_email = intent.getStringExtra("email");

    upName.setText(user_name);
    upSName.setText(user_s_name);
    upEmail.setText(user_email);
  }

  private Boolean validateName() {
    String val = upName.getText().toString().trim();

    if (val.isEmpty()) {
      upName.setError("Field can not be empty");
      return false;
    } else if (val.length() > 30) {
      upName.setError("Field Too Long");
      return false;
    } else {
      upName.setError(null);
      return true;
    }
  }

  private Boolean validateSchName() {
    String val = upSName.getText().toString().trim();

    if (val.isEmpty()) {
      upSName.setError("Field can not be empty");
      return false;
    } else {
      upSName.setError(null);
      return true;
    }
  }

  private Boolean validateEmail() {
    String val = upEmail.getText().toString().trim();
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+";

    if (val.isEmpty()) {
      upEmail.setError("Field can not be empty");
      return false;
    } else if (!val.matches(emailPattern)) {
      upEmail.setError("Invalid email address");
      return false;
    } else {
      upEmail.setError(null);
      return true;
    }
  }

  public void updatePfl(View view) {

    dialog.show();

    // Get all values in String
    String name = upName.getText().toString().toUpperCase().trim();
    String email = upEmail.getText().toString().toLowerCase().trim();
    String schoolName = upSName.getText().toString().toUpperCase().trim();

    if (!validateName() | !validateEmail() | !validateSchName()) {
      dialog.dismiss();
    } else {

      Intent intent = getIntent();
      String username = intent.getStringExtra("username");
      String password = intent.getStringExtra("password");

      EducatorClass educatorClass =
          new EducatorClass(name, username, email, schoolName, password);
      reference
          .child(username)
          .setValue(educatorClass)
          .addOnSuccessListener(
              new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                  dialog.dismiss();
                  Toast.makeText(
                          UpdateEducatorProfile.this, "Updated Successfully", Toast.LENGTH_SHORT)
                      .show();
                  finish();
                }
              })
          .addOnFailureListener(
              new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                  dialog.dismiss();
                  Toast.makeText(getApplicationContext(), "Failed To Update", Toast.LENGTH_LONG)
                      .show();
                }
              });
    }
  }
}
