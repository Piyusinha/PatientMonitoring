package com.aron.patientmonitoring;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ordermedicine extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordermedicine);
        webView =(WebView)findViewById(R.id.ordermedicine);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://m.netmeds.com/");
        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack())
        {
            webView.goBack();
        }
        else{
                super.onBackPressed();
        }
    }
}
