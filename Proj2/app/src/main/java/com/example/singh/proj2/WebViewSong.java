package com.example.singh.proj2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewSong extends AppCompatActivity {
    WebView mwebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_web_view_song);
        mwebView = (WebView) findViewById(R.id.songWeb);
        String url = this.getIntent().getExtras().getString("SongURL");
        mwebView.getSettings().setJavaScriptEnabled(true);
        mwebView.setWebViewClient(new WebViewClient());
        mwebView.setWebChromeClient(new WebChromeClient());         //to set the webview client to chrome
        mwebView.loadUrl(url);


    }

}