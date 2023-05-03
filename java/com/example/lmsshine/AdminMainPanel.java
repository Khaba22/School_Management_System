package com.example.lmsshine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminMainPanel extends AppCompatActivity implements View.OnClickListener {

    private Button btnProfile, btnContent, btnSendNotification, btnLeaderBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_panel);

        btnProfile = (Button)findViewById(R.id.profile);
        btnProfile.setOnClickListener(this);

        btnContent = (Button)findViewById(R.id.manageContent);
        btnContent.setOnClickListener(this);

        btnSendNotification = (Button)findViewById(R.id.sendNotification);
        btnSendNotification.setOnClickListener(this);

        btnLeaderBoard = (Button)findViewById(R.id.leaderBoard);
        btnLeaderBoard.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.profile:
                startActivity(new Intent(this, AdminProfile.class));
                break;
            case R.id.manageContent:
                startActivity(new Intent(this, ContentManager.class));
                break;
            case R.id.sendNotification:
                startActivity(new Intent(this, SendNotification.class));
                break;
            case R.id.leaderBoard:
                startActivity(new Intent(this, ViewSubmissions.class));
        }
    }
}