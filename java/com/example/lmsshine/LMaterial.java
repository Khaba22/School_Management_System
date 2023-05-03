package com.example.lmsshine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LMaterial extends AppCompatActivity implements View.OnClickListener {

    private Button btnSchool, btnEd;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l_material);

        btnSchool = (Button) findViewById(R.id.school);
        btnSchool.setOnClickListener(this);

        btnEd = (Button) findViewById(R.id.educational);
        btnEd.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.school:
                startActivity(new Intent(this, SchoolContent.class));
                break;
            case R.id.educational:
                startActivity(new Intent(this, EdContent.class));
                break;
        }
    }
}
