package com.example.lmsshine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ResetUserPassword extends AppCompatActivity {

  EditText username, password;
  CheckBox checkBox;
  Button resetPassword;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_reset_user_password);

    username = findViewById(R.id.username);
    password = findViewById(R.id.password);

    checkBox = findViewById(R.id.checkBox);
    checkBox.setOnCheckedChangeListener(
        (buttonView, isChecked) -> {
          if (isChecked) {
            password.setTransformationMethod(null);
          } else {
            password.setTransformationMethod(new PasswordTransformationMethod());
          }
        });

    resetPassword = findViewById(R.id.resetButton);
  }

  private Boolean validateUsername() {
    String val = username.getText().toString().toUpperCase().trim();

    if (val.isEmpty()) {
      username.setError("Field can not be empty");
      return false;
    } else {
      username.setError(null);
      return true;
    }
  }

  private Boolean validatePassword() {
    String val = password.getText().toString().trim();

    if (val.isEmpty()) {
      password.setError("Field can not be empty");
      return false;
    } else if (val.length() < 6) {
      password.setError("Enter at least 6 characters");
      return false;
    } else {
      password.setError(null);
      return true;
    }
  }

  public void resetPassword(View view) {

    // Validate login info

    if (!validateUsername() | !validatePassword()) {

    } else {
      isUser();
    }
  }

  private void isUser() {

    String user_name = username.getText().toString().toUpperCase().trim();
    String user_password = password.getText().toString().trim();

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users/Learner");

    Query checkUser = reference.orderByChild("username").equalTo(user_name);

    checkUser.addListenerForSingleValueEvent(
        new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {

              HashMap hashMap = new HashMap();
              hashMap.put("password", user_password);

              reference
                  .child(user_name)
                  .updateChildren(hashMap)
                  .addOnSuccessListener(
                      new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                          Toast.makeText(
                                  ResetUserPassword.this, "Password Reset", Toast.LENGTH_SHORT)
                              .show();
                        }
                      });
            } else if (!snapshot.exists()) {

              DatabaseReference reference =
                  FirebaseDatabase.getInstance().getReference("Users/Educator");

              Query checkUser = reference.orderByChild("username").equalTo(user_name);

              checkUser.addListenerForSingleValueEvent(
                  new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                      if (snapshot.exists()) {

                        HashMap hashMap = new HashMap();
                        hashMap.put("password", user_password);

                        reference
                            .child(user_name)
                            .updateChildren(hashMap)
                            .addOnSuccessListener(
                                o ->
                                    Toast.makeText(
                                            ResetUserPassword.this,
                                            "Password Reset",
                                            Toast.LENGTH_SHORT)
                                        .show());
                      } else {
                          Toast.makeText(ResetUserPassword.this, "Username Not Found In The DB", Toast.LENGTH_SHORT)
                                  .show();
                      }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                  });
            }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {}
        });
  }
}
