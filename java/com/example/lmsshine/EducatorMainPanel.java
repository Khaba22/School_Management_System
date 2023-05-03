package com.example.lmsshine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EducatorMainPanel extends AppCompatActivity implements View.OnClickListener {

    private Button btnContent, btnLeaderBoard;
    String string1, string2, string3, string4, string5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_educator_main_panel);

        btnContent = (Button)findViewById(R.id.manageContent);
        btnContent.setOnClickListener(this);

        btnLeaderBoard = (Button)findViewById(R.id.viewSubmissions);
        btnLeaderBoard.setOnClickListener(this);

    }

    public void goToProfile(View view) {
        Intent intent = new Intent(EducatorMainPanel.this, UserProfile.class);
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
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.manageContent:
                Intent intent = new Intent(this, EducatorContent.class);
                string2 = getIntent().getExtras().getString("sName");
                intent.putExtra("sName", string2);
                startActivity(intent);
                break;
            case R.id.viewSubmissions:
                Intent intent1 = new Intent(this, SubmissionsMain.class);
                string2 = getIntent().getExtras().getString("sName");
                intent1.putExtra("sName", string2);
                startActivity(intent1);
        }
    }
}