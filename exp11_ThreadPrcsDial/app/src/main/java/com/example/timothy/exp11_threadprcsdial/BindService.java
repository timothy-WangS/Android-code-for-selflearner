package com.example.timothy.exp11_threadprcsdial;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class BindService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        Log.v("MainActivity", "onBind");
        // 只有有了返回值，onServiceConnected才会被调用(MainActivity/MyConnection)
        return new MyBinder();
    }

    public class MyBinder extends Binder{
        public void showLog(String s){
            Log.v("MainActivity", s);
        }
    }

    @Override
    public void onCreate() {

        super.onCreate();
        Log.v("MainActivity", "onCreat");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("MainActivity", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("MainActivity", "onDestroy");
    }


}
