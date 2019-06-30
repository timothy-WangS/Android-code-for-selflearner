package com.example.timothy.exp09_brdcstipdial;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class OrdBCReceiver_2 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String resultData = getResultData();
        Log.v("MainActivity", resultData);
        setResultData("Ha");
        // 终止广播，后续优先级无法接收，只有有序广播可以终止
        abortBroadcast();
    }
}
