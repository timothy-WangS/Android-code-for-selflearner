package com.example.timothy.exp09_brdcstipdial;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class InstallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Uri data = intent.getData();
        if("android.intent.action.PACKAGE_ADDED".equals(action)){
            Log.v("MainActivity", "Added "+data);
        }else if("android.intent.action.PACKAGE_REMOVED".equals(action)){
            Log.v("MainActivity", "Removed "+data);
        }else{
            Log.v("MainActivity", "Installation Changed");
        }
    }
}
