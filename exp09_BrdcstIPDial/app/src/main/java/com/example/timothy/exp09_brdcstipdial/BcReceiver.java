package com.example.timothy.exp09_brdcstipdial;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BcReceiver extends BroadcastReceiver {
    @Override

    // 接收自定义无序广播
    public void onReceive(Context context, Intent intent) {
        String key = intent.getStringExtra("key");
        Log.v("MainActivity", "Received BC - "+key);
    }
}
