package com.example.timothy.exp11_threadprcsdial;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }

    public void startService(View view){
        // 显式意图开启Service
        Intent service = new Intent(this, MyService.class);
        // startService方式用intent开启服务
        // onCreat -> onStartCommand
        // 再次调用只走onStartCommand
        startService(service);
    }

    public void stopService(View view){
        Intent service = new Intent(this, MyService.class);
        // stopService关闭服务
        // onDestroy
        // 再次调用不执行
        stopService(service);
    }

    private MyConnection conn;
    private class MyConnection implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 只有onBind返回值不为null才会调用，传来的service就是onBind的返回值
            Log.v("MainActivity", "onConnected");
            // 不能直接用new MyBinder去调用，必须通过bindService创建对象(见下)，然后再onServiceConnected调用
            BindService.MyBinder mybinder = (BindService.MyBinder) service;
            mybinder.showLog("used by service connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // 一般来说服务正常退出不会调用这个方法
            Log.v("MainActivity", "onDisConnected");
        }
    }

    public void bindService(View view) {
        Intent service = new Intent(this, BindService.class);
        conn = new MyConnection();
        // 用bind开启service，
        // 意图，ServiceConnection接口(获取服务开始停止消息)，BIND_AUTO_CREATE
        // onCreat->onBind，再次调用不执行
        bindService(service, conn, BIND_AUTO_CREATE);
    }

    public void bindStop(View view) {
        // 在activity关闭时应同时关闭，也就是在activity的onDestroy要调用unbindService，见上上上方
        // 只能点一次，再调用会报错
        unbindService(conn);
    }
}
