package com.example.lmsshine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class LearnerEducationalContent extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_ed_content);

    TextView textView1 = findViewById(R.id.txtView1);
    TextView textView2 = findViewById(R.id.txtView2);
    Button button1 = findViewById(R.id.txtButton1);
    Button button2 = findViewById(R.id.txtButton2);

    button1.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            String str = "";
            try {
              InputStream inputStream = getAssets().open("introToBlockCoding.txt");
              int size = inputStream.available();
              byte[] buffer = new byte[size];
              inputStream.read(buffer);
              str = new String(buffer);
            } catch (IOException e) {
              e.printStackTrace();
            }
            textView1.setText(str);
          }
        });

    button2.setOnClickListener(
        v -> {
          String str = "";
          try {
            InputStream inputStream = getAssets().open("career.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            str = new String(buffer);
          } catch (IOException e) {
            e.printStackTrace();
          }
          textView2.setText(str);
        });
  }
}
