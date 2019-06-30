package com.example.timothy.hideintent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ThirdActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 加载界面，创建xml文件组件对应的java对象
        setContentView(R.layout.activity_third);
    }
}

