package com.example.timothy.exp09_brdcstipdial;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class OrdBCReceiver_3 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String resultData = getResultData();
        Log.v("MainActivity", resultData);
    }
}
