package com.example.lmsshine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class ViewDocumentActivity extends AppCompatActivity {

    WebView webView;
    String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_document);

        string = getIntent().getExtras().getString("EMAIL");

        webView = findViewById(R.id.vdWebView);
        webView.loadUrl(string);

    }
}