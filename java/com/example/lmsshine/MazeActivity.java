package com.example.lmsshine;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class MazeActivity extends AppCompatActivity {

  private WebView ticWebView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_gaming);

    ticWebView = findViewById(R.id.webView);
    WebSettings webSettings = ticWebView.getSettings();
    webSettings.setBuiltInZoomControls(true);
    webSettings.setJavaScriptEnabled(true);
    ticWebView.getSettings().setAllowContentAccess(true);
    ticWebView.getSettings().setAllowFileAccess(true);
    ticWebView.loadUrl("file:///android_asset/maze.html");
  }

  @Override
  public void onBackPressed() {
    if (ticWebView != null && ticWebView.canGoBack()) {
      ticWebView.goBack();
    } else {
      super.onBackPressed();
    }
  }
}
