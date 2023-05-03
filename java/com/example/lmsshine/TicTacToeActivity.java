package com.example.lmsshine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TicTacToeActivity extends AppCompatActivity {

    private WebView ticWebView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_tic_tac_toe);

    ticWebView = findViewById(R.id.ticTacToeWebView);
    WebSettings webSettings1 = ticWebView.getSettings();
    webSettings1.setBuiltInZoomControls(false);
    webSettings1.setJavaScriptEnabled(true);
    ticWebView.getSettings().setAllowContentAccess(true);
    ticWebView.getSettings().setAllowFileAccess(true);
    ticWebView.loadUrl("file///android_asset/tictactoe.html");

    }
}