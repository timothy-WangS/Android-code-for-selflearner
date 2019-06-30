package com.example.timothy.exp09_brdcstipdial;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("MainActivity", "SMS Received");
        // 获取短信内容，转为Obj数组，格式为"pdus
        Object[] objects = (Object[]) intent.getExtras().get("pdus");
        for (Object object:objects){
            SmsMessage message = SmsMessage.createFromPdu((byte[]) object);
            // 获取发送者
            String from = message.getOriginatingAddress();
            // 获取短信体
            String body = message.getMessageBody();
            Log.v("MainActivity", from+": "+body);
        }
    }
}
