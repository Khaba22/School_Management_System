package com.example.lmsshine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LearnerMainPanel extends AppCompatActivity implements View.OnClickListener {

    private Button btnContent, btnGames;
    String string1, string2, string3, string4, string5, string6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learner_main);

        btnContent = (Button)findViewById(R.id.lContent);
        btnContent.setOnClickListener(this);

        btnGames = (Button)findViewById(R.id.gaming);
        btnGames.setOnClickListener(this);

    }

    public void goToProfile(View view) {
        Intent intent = new Intent(LearnerMainPanel.this, LearnerProfile.class);
        string5 = getIntent().getExtras().getString("username");
        intent.putExtra("username", string5);
        string1 = getIntent().getExtras().getString("name");
        intent.putExtra("name", string1);
        string2 = getIntent().getExtras().getString("sName");
        intent.putExtra("sName", string2);
        string3 = getIntent().getExtras().getString("email");
        intent.putExtra("email", string3);
        string4 = getIntent().getExtras().getString("password");
        intent.putExtra("password", string4);
        string6 = getIntent().getExtras().getString("grade");
        intent.putExtra("grade", string6);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()) {
            case R.id.lContent:
                Intent intent = new Intent(this, LearnerContent.class);
                string2 = getIntent().getExtras().getString("sName");
                intent.putExtra("sName", string2);
                string6 = getIntent().getExtras().getString("grade");
                intent.putExtra("grade", string6);
                startActivity(intent);
                break;
            case R.id.gaming:
                startActivity(new Intent(this, Games.class));
        }

    }
}