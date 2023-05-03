package com.example.lmsshine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button admin, learner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        admin = (Button)findViewById(R.id.admin);
        admin.setOnClickListener(this);

        learner = (Button)findViewById(R.id.learner);
        learner.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.admin:
                startActivity(new Intent(this, AdminLogin.class));
                break;
            case R.id.learner:
                startActivity(new Intent(this, LearnerLogin.class));
                break;
        }
        finish();
    }
}