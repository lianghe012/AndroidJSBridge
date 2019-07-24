package com.lianghe.androidbridge;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Set;


/**
 * @author wanglianghe
 * @createTime 2019/7/21 1:27 PM
 * @class describe
 */
public class JSAndroidActivity extends Activity {
    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.js_android);
        initWebView();
        javaToJS();
        JStoJava();
    }

    private void initWebView() {
        mWebView = (WebView) findViewById(R.id.webview);
        WebSettings webset = mWebView.getSettings();
        webset.setJavaScriptEnabled(true);
        webset.setDomStorageEnabled(true);
        webset.setAppCacheEnabled(true);
        webset.setJavaScriptCanOpenWindowsAutomatically(true);
    }


    private void javaToJS() {
        /**
         * 第一种方式
         */
        mWebView.loadUrl("file:///android_asset/ShowToast.html");
//        /**
//         * 第二种方式
//         */
//        mWebView.evaluateJavascript("javascript:callJSTest()", new ValueCallback<String>() {
//            @Override
//            public void onReceiveValue(String value) {
//                Toast.makeText(JSAndroidActivity.this, "onReceiveValue Java To JS", Toast.LENGTH_SHORT).show();
//            }
//        });
    }


    private void JStoJava() {
        /**
         * 第一种调用方式
         */
        mWebView.addJavascriptInterface(new JSCallJava(this), "caller");

        /**
         * 第二种调用方式
         */
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri uri = Uri.parse(url);
                if ("js".equals(uri.getScheme())) {
                    if ("webview".equals(uri.getAuthority())) {
                        Toast.makeText(JSAndroidActivity.this, "JSCallJava second", Toast.LENGTH_SHORT).show();
                    }
                    //不进行跳转
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        /**
         * 第三种调用方式
         */

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                // 根据协议的参数，判断是否是所需要的url(原理同方式2)
                // 一般根据scheme（协议格式） & authority（协议名）判断（前两个参数）
                //假定传入进来的 url = "js://webview?arg1=111&arg2=222"（同时也是约定好的需要拦截的）
                Uri uri = Uri.parse(message);
                // 如果url的协议 = 预先约定的 js 协议
                // 就解析往下解析参数
                if (uri.getScheme().equals("js")) {

                    // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
                    // 所以拦截url,下面JS开始调用Android需要的方法
                    if (uri.getAuthority().equals("demo")) {

                        //
                        // 执行JS所需要调用的逻辑
                        Toast.makeText(JSAndroidActivity.this, "JSCallJavathird", Toast.LENGTH_SHORT).show();
                        // 可以在协议上带有参数并传递到Android上
                        HashMap<String, String> params = new HashMap<>();
                        Set<String> collection = uri.getQueryParameterNames();

                        //参数result:代表消息框的返回值(输入值)
                        result.confirm("fromPrompt");
                    }
                    return true;
                }
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }


        });
    }


}
