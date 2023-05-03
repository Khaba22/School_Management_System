package com.example.lmsshine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AppMainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button administrator, educator, learner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        administrator = findViewById(R.id.administrator);
        administrator.setOnClickListener(this);

        educator = findViewById(R.id.educator);
        educator.setOnClickListener(this);

        learner = findViewById(R.id.learner);
        learner.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.administrator:
                startActivity(new Intent(this, AdministratorLogin.class));
                break;
            case R.id.educator:
                startActivity(new Intent(this, EducatorLogin.class));
                break;
            case R.id.learner:
                startActivity(new Intent(this, LearnerLogin.class));
                break;
        }
    }
}