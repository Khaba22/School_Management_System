package com.example.lmsshine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdministratorMainPanel extends AppCompatActivity implements View.OnClickListener {

  private TextView regEducator, regLearner, resetPassword;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_administrator_main_panel);

    regEducator = findViewById(R.id.regEducator);
    regEducator.setOnClickListener(this);

    regLearner = findViewById(R.id.regLearner);
    regLearner.setOnClickListener(this);

    resetPassword = findViewById(R.id.resetPassword);
    resetPassword.setOnClickListener(this);

  }

  @Override
  public void onClick(View view) {
    switch(view.getId()) {
      case R.id.regEducator:
        startActivity(new Intent(this, EducatorRegister.class));
        break;
      case R.id.regLearner:
        startActivity(new Intent(this, LearnerRegister.class));
        break;
      case R.id.resetPassword:
        startActivity(new Intent(this, ResetUserPassword.class));
    }
  }
}