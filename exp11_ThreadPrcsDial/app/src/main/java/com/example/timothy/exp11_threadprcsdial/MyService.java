package com.example.timothy.exp11_threadprcsdial;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.IOException;

public class MyService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        Log.v("MainActivity", "onBind");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("MainActivity", "onCreat");
        // 创建电话管理器对象
        TelephonyManager manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        // 电话状态监听器
        MyPhoneListener listener = new MyPhoneListener();
        // 调用listen方法，注册监听
        manager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    private class MyPhoneListener extends PhoneStateListener{
        private MediaRecorder recorder;
        @Override
        public void onCallStateChanged(int state, String phoneNumber) {
            super.onCallStateChanged(state, phoneNumber);
            switch (state){
                case TelephonyManager.CALL_STATE_IDLE:
                    Log.v("MainActivity", "Available， stop record");
                    if (recorder  != null){
                        // 停止录音
                        try {
                            recorder.setOnErrorListener(null);
                            recorder.setOnInfoListener(null);
                            recorder.setPreviewDisplay(null);
                            recorder.stop();
                        }catch (IllegalStateException e) {
                            // 如果当前java状态和jni里面的状态不一致，
                            e.printStackTrace();
                        }
                        // 重置recorder
                        recorder.reset();
                        // 释放对象，下次使用需要new对象
                        recorder.release();
                    }
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    Log.v("MainActivity", "Ring, preparing");
                    recorder = new MediaRecorder();
                    // 输入源，VOICE_MIC只录自己的，VOICE_CALL录双方(可能违法)
                    recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
                    // 音频输出3gp
                    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    // 编码方式，amr是早期手机铃声格式，体积小音质差；nb为窄带，wb宽带
                    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    // 文件路径
                    recorder.setOutputFile(getCacheDir()+"/"+phoneNumber+".3gp");
                    // 录音机准备
                    try {
                        recorder.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    Log.v("MainActivity", "Start record");
                    break;
            }
        }
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
