package com.lianghe.androidbridge;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class JSBridgeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.js_android);
        WebView mWebView = (WebView) findViewById(R.id.webview);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new JSBridgeWebChromeClient());
        mWebView.loadUrl("file:///android_asset/index.html");

        JSBridge.register("bridge", BridgeImpl.class);

    }


}
