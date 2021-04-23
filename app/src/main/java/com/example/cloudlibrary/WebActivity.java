package com.example.cloudlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.example.cloudlibrary.util.JavaScriptObj;

public class WebActivity extends AppCompatActivity {
    private WebView webView;
    private String localHost="http://166.111.226.244:11352/accounts/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webView=(WebView)findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.addJavascriptInterface(new JavaScriptObj((CLApplication) getApplication()),"java_obj");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("WebActivity",url);
                if(url.endsWith("profile/")) view.loadUrl("javascript:window.java_obj.onSignin(document.documentElement.outerHTML);void(0)");
                else if(url.endsWith("signup/")||url.endsWith("login/")) view.loadUrl("javascript:window.java_obj.onSignout(document.documentElement.outerHTML);void(0)");
                super.onPageFinished(view, url);
            }
        });
        String category=getIntent().getStringExtra("category");
        webView.loadUrl(localHost+category+"/");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.clearCache(true);
    }

    @Override
    public void onBackPressed(){
        webView.clearCache(true);
        super.onBackPressed();
    }
}