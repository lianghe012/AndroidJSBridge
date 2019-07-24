package com.lianghe.androidbridge;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * @author wanglianghe
 * @createTime 2019/7/7 3:54 PM
 * @class describe
 */
public class JSCallJava {
    private Context mContext;

    public JSCallJava(Context context){
        mContext = context;
    }

    @JavascriptInterface
    public String callJava(){
        Toast.makeText(mContext, "JSCallJava first", Toast.LENGTH_SHORT).show();
        return "fromJavascriptInterface";
    }



}
