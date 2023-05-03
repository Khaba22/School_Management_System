package com.example.lmsshine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ContentManager extends AppCompatActivity implements View.OnClickListener {

    private Button uploadContent, view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_manager);

        uploadContent = (Button) findViewById(R.id.buttonUpload);
        uploadContent.setOnClickListener(this);

        view = (Button) findViewById(R.id.buttonManageContent);
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonUpload:
            startActivity(new Intent(this, UploadContent.class));
            break;
            case R.id.buttonManageContent:
            startActivity(new Intent(this, ManageUploads.class));
            break;
        }
    }
}