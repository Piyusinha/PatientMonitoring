package com.aron.patientmonitoring;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class mapactivity extends AppCompatActivity {
private  WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapactivity);
        webView =(WebView)findViewById(R.id.hospital);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.google.com/maps/search/hospital/@28.6392176,77.3751049,14z");
        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
}
