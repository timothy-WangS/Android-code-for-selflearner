package com.example.timothy.exp09_brdcstipdial;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// 四大组件：activity、service、content provider、broadcast receiver

public class MainActivity extends AppCompatActivity {
    private EditText et_prefix;
    private Button btn_save;
    private SharedPreferences sp;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_prefix = findViewById(R.id.et_prefix);
        btn_save = findViewById(R.id.btn_save);

        // 动态注册广播接收者，
        // 广播接收者对象
        receiver = new ScreenLightReceiver();
        // 意图过滤器
        IntentFilter filter = new IntentFilter();
        // 添加接收者需要的意图
        filter.addAction("android.intent.action.SCREEN_ON");
        filter.addAction("android.intent.action.SCREEN_OFF");
        // 注册广播接收者，需要注销，在onDestroy
        registerReceiver(receiver, filter);

        sp = getSharedPreferences("info", MODE_PRIVATE);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取输入
                String pref = et_prefix.getText().toString();
                if (TextUtils.isEmpty((pref))){
                    Toast.makeText(getApplicationContext(),"NO TEXT", Toast.LENGTH_SHORT).show();
                }else {
                    // 用SharedPreference保存数据并提交
                    sp.edit().putString("prefix", pref).apply();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销广播接收者
        unregisterReceiver(receiver);
    }

    public void sendBroadCast(View v){
        // 发送无序广播
        Intent intent = new Intent();
        intent.setAction("com.example.timothy.bcsend");
        // 携带数据
        intent.putExtra("key", "hello");
        sendBroadcast(intent);
    }

    public void sendOrdBC(View v) {
        // 发送有序广播，按Manifest规定的priority顺序接收
        Intent intent = new Intent();
        intent.setAction("com.example.timothy.ordsend");
        // 这四个一般为null
        // 收到广播需要的权限
        String receiverPermission = null;
        // 作为最终广播接收者，若被终止(见OrdBCReceiver_2)也收不到广播
        BroadcastReceiver resultReceiver = null;
        //处理最终的广播接收者用到的Handler，若null在主线程处理
        Handler scheduler = null;
        Bundle initialExtras = null;
        // 初始化数据
        String initialData = "Hahaha";
        sendOrderedBroadcast(intent, receiverPermission, resultReceiver, scheduler, Activity.RESULT_OK, initialData, initialExtras);
    }
}
