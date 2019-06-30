package com.example.timothy.exp09_brdcstipdial;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SDCardReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // 获取动作，装上sd卡和拔下sd卡
        String action = intent.getAction();
        if ("android.intent.action.MEDIA_MOUNTED".equals(action)){
            Log.v("MainActivity", "SD Card Mounted");
        }else if ("android.intent.action.MEDIA_UNMOUNTED".equals(action)){
            Log.v("MainActivity", "SD Card UnMounted");
        }else{
            Log.v("MainActivity", "SD Card Changed");
        }
    }
}
