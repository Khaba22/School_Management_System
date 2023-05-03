package com.example.lmsshine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SubmissionsMain extends AppCompatActivity implements View.OnClickListener {

    Button g5, g6, g7;
    String string, string1, string2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submissions_main);

        g5 = findViewById(R.id.g5);
        g5.setOnClickListener(this);
        g6 = findViewById(R.id.g6);
        g6.setOnClickListener(this);
        g7 = findViewById(R.id.g7);
        g7.setOnClickListener(this);

    }

    public void onClick( View view ) {

        switch (view.getId()) {
            case R.id.g5:
                Intent intent = new Intent(this, ViewG5Submissions.class);
                string = getIntent().getExtras().getString("sName");
                intent.putExtra("sName", string);
                startActivity(intent);
                break;
            case R.id.g6:
                Intent intent1 = new Intent(this, ViewG6Submissions.class);
                string1 = getIntent().getExtras().getString("sName");
                intent1.putExtra("sName", string1);
                startActivity(intent1);
                break;
            case R.id.g7:
                Intent intent2 = new Intent(this, ViewG7Submissions.class);
                string2 = getIntent().getExtras().getString("sName");
                intent2.putExtra("sName", string2);
                startActivity(intent2);
        }

    }

}