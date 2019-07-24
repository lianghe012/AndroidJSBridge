package com.lianghe.androidbridge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

/**
 * @author wanglianghe
 * @createTime 2019/7/21 4:59 PM
 * @class describe
 */
public class MainActivty extends Activity implements View.OnClickListener {
    private Button js_java_demo;
    private Button js_bridge;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        js_java_demo = (Button) findViewById(R.id.js_java_demo);
        js_bridge = (Button) findViewById(R.id.js_bridge);
        js_java_demo.setOnClickListener(this);
        js_bridge.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.js_java_demo:
                startActivity(new Intent(MainActivty.this,JSAndroidActivity.class));
            break;
            case R.id.js_bridge:
                startActivity(new Intent(MainActivty.this,JSBridgeActivity.class));
                break;
            default:
                break;
        }
    }
}
