package com.example.lmsshine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LearnerContent extends AppCompatActivity {

    private Button viewMaterial, viewActivities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learner_content);

        viewMaterial = findViewById(R.id.mMaterials);
        viewMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LearnerContent.this, LMaterial.class));
            }
        });

        viewActivities = findViewById(R.id.mActivity);
        viewActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LearnerContent.this, LActivities.class));
            }
        });

    }
}