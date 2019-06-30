package com.example.timothy.exp09_brdcstipdial;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScreenLightReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String activity = intent.getAction();
        if("android.intent.action.SCREEN_ON".equals(activity)){
            Log.v("MainActivity", "Screen on");
        } else if ("android.intent.action.SCREEN_OFF".equals(activity)) {
            Log.v("MainActivity", "Screen off");
        }else {
            Log.v("MainActivity", "Screen action");
        }
    }
}
