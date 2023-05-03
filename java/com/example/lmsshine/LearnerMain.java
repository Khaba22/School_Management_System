package com.example.lmsshine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LearnerMain extends AppCompatActivity implements View.OnClickListener {

    private Button btnProfile, btnContent, btnGames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learner_main);

        btnProfile = (Button)findViewById(R.id.lProfile);
        btnProfile.setOnClickListener(this);

        btnContent = (Button)findViewById(R.id.lContent);
        btnContent.setOnClickListener(this);

        btnGames = (Button)findViewById(R.id.gaming);
        btnGames.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch(view.getId()) {
            case R.id.lProfile:
                startActivity(new Intent(this, LearnerProfile.class));
                break;
            case R.id.lContent:
                startActivity(new Intent(this, LearnerContent.class));
                break;
            case R.id.gaming:
                startActivity(new Intent(this, Games.class));
        }

    }
}