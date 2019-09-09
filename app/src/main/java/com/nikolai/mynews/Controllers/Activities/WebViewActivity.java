package com.nikolai.mynews.Controllers.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.nikolai.mynews.R;

public class WebViewActivity extends AppCompatActivity {

        private WebView webview;
        private String url = "https://www.google.com";

        public WebViewActivity(String url) {
            this.url = url;
        }

        public WebViewActivity() {
        }

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_webview);

            webview = (WebView) findViewById(R.id.webview);
            webview.setWebViewClient(new WebViewClient());
            Intent intent = getIntent();
            url = intent.getStringExtra("URL");
            webview.loadUrl(url);
        }

        @Override
        public void onBackPressed() {
            if (webview.canGoBack()) {
                webview.goBack();
            } else {
                super.onBackPressed();
            }
        }
}

