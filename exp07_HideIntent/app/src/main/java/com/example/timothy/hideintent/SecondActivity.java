package com.example.timothy.hideintent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SecondActivity extends AppCompatActivity {
    // 创建新的activity
    // 1.创建一个新的类，继承AppCompatActivity或Activity
    // 2.重写onCreate方法
    // 3.在onCreate做初始化操作
    // 4.在Manifest申明
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 加载界面，创建xml文件组件对应的java对象
        setContentView(R.layout.activity_second);
    }

    public void thract(View v){
        // 显式意图(上下文，要开启的activity.class)
        Intent intent = new Intent(getApplicationContext(),ThirdActivity.class);
        startActivity(intent);
    }
}
