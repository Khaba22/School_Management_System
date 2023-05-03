package com.example.lmsshine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LearnerMaterial extends AppCompatActivity implements View.OnClickListener {

    private Button btnSchool, btnEd;
    String string2, string6;

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
                Intent intent = new Intent(LearnerMaterial.this, SchoolContent.class);
                string2 = getIntent().getExtras().getString("sName");
                intent.putExtra("sName", string2);
                string6 = getIntent().getExtras().getString("grade");
                intent.putExtra("grade", string6);
                startActivity(intent);
                break;
            case R.id.educational:
                startActivity(new Intent(this, LearnerEducationalContent.class));
                break;
        }
    }
}
