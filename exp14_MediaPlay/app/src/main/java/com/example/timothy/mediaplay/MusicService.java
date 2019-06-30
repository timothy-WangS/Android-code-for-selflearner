package com.example.timothy.mediaplay;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;

public class MusicService extends Service {
    private String path = "mnt/sdcard/b_7.mp3";
    private MediaPlayer player;

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public class MyBinder extends Binder{
        public void play(){
            // 里面填入业务逻辑
            if (player.isPlaying()){
                player.pause();
            }else {
                player.start();
            }
        }

        public boolean state(){
            return player.isPlaying();
        }

        // 获取总时长毫秒值
        public int getDuration(){
            return player.getDuration();
        }

        // 获取当前播放进度
        public int getCurrentPosition(){
            return player.getCurrentPosition();
        }

        // 修改进度方法，传入毫秒值
        public void seekTo(int msec){
            player.seekTo(msec);
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();

        player = new MediaPlayer();
        try {
            // 设置数据源
            player.setDataSource(path);
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
