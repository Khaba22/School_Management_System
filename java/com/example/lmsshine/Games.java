package com.example.lmsshine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Games extends AppCompatActivity {

  private Button playCoding, playTicTacToe;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_games);

    playCoding = findViewById(R.id.coding);
    playCoding.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            startActivity(new Intent(Games.this, CodingActivities.class));
          }
        });

    playTicTacToe = findViewById(R.id.tictactoe);
    playTicTacToe.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(Games.this, TicTacToeActivity.class));
        }
    });
  }
}
