package com.pkl.wartajazz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.Toast;

public class WebActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
//
//        webView = (WebView) findViewById(R.id.webView1);
//        webView.getSettings().setJavaScriptEnabled(true);
//
//        // getting parsed link from BeritaFragment
//        String url = getIntent().getStringExtra("url");
//        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
//        webView.loadUrl(url);
//        this.finish();
    }
}
