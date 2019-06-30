package com.example.timothy.exp09_brdcstipdial;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

// 写一个广播接收者
// 创建class，继承BroadcastReceiver
// 重写onReceive
// Manifest注册，可能需要静态权限
// 动态权限，调用registerReceiver，还需要unregister；见Turnon

public class IPDialReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // 获取前缀
        SharedPreferences sp = context.getSharedPreferences("info", Context.MODE_PRIVATE);
        // 取保存的值，若没有默认123456
        String pref = sp.getString("prefix", "123456");
        // 获取拨打的号码
        String number = getResultData();
        // 显示
        Log.v("MainActivity", "Dial Phone-"+number);
        // 更改拨打的号码
        setResultData(pref+number);
    }

}
