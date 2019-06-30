package com.example.timothy.exp09_brdcstipdial;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TurnonReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("MainActivity", "Boot");
        // 开机自启，需要创建任务栈
        Intent intent2 = new Intent(context, MainActivity.class);
        // 创建任务栈
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 开启主程序，此处为避免以后开机自启注释掉了启动语句
        //context.startActivity(intent2);
    }
}
