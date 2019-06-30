package com.example.timothy.mediaplay;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

import java.io.IOException;
import java.sql.Connection;

// 步骤：
// 混合方式开启服务
// 在onCreat创建mediaplayer
// 点击按钮播放音乐
// 一个按钮播放暂停，获得播放状态
// activity退出和重开更新图标状态
// 进度条展示进度，handler延时消息，总时长和当前进度
// 退出移除消息和重开更新消息
// 进度条监听修改，在onProgressChange里面处理，seekTo方法

public class MainActivity extends AppCompatActivity {

    private static final int UPDATE_PROGRESS = 0;
    private MyConnection conn;
    private ImageButton ib_play;
    private SeekBar sb_progress;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_PROGRESS:
                    updateProgress();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ib_play = findViewById(R.id.btn_play);
        sb_progress = findViewById(R.id.sb_progress);

        // 设置播放进度条监听
        sb_progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 进度变化
                if(fromUser){
                    // 如果是用户修改的，则执行修改进度操作
                    musicCtrl.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 开始触摸seekbar
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 停止触摸seekbar
            }
        });

        // 混合方式开启音乐播放
        Intent service = new Intent(this, MusicService.class);
        startService(service);
        conn = new MyConnection();
        bindService(service, conn, BIND_AUTO_CREATE);
    }

    public void play(View view) {
        musicCtrl.play();
        play_icon();
    }

    // 根据播放状态更新播放图标
    public void play_icon(){
        if(musicCtrl.state()){
            // 播放状态，按钮显示为暂停
            ib_play.setImageResource(R.drawable.pause);
            handler.sendEmptyMessageDelayed(UPDATE_PROGRESS, 500);
        }else {
            // 暂停状态，按钮显示为播放
            ib_play.setImageResource(R.drawable.play);
            // 暂停时不需要更新状态
            handler.removeMessages(UPDATE_PROGRESS);
        }
    }

    public void updateProgress(){
        // 获取进度
        int current = musicCtrl.getCurrentPosition();
        // 显示当前状态
        sb_progress.setProgress(current);
        // 延时500ms再发送消息，形成延时循环
        handler.sendEmptyMessageDelayed(UPDATE_PROGRESS, 500);
    }

    private MusicService.MyBinder musicCtrl;
    private class MyConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicCtrl = (MusicService.MyBinder) service;
            // 设置播放控制图标
            play_icon();
            // 设置总时长
            sb_progress.setMax(musicCtrl.getDuration());
            // 初始界面设置进度
            sb_progress.setProgress(musicCtrl.getCurrentPosition());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 在关闭页面时关闭进度条更新
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 在重开页面时调用进度条更新
        if (musicCtrl!=null){
            handler.sendEmptyMessage(UPDATE_PROGRESS);
        }
    }
}
