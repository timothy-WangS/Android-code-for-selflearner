package com.example.timothy.hideintent;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 加载界面，创建xml文件组件对应的java对象
        setContentView(R.layout.activity_main);
    }

    public void call(View v){
        // 打电话意图
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+110));
        startActivity(intent);
    }

    public void  secact(View v){
        Intent intent = new Intent();
        // 与Manifest文件的 action android:name 匹配
        // 匹配的条目为需要开启的activity
        intent.setAction("com.example.timothy.second");
        // Uri.parse("example:" 的example是与Manifest的data android:scheme匹配的
        // 设置数据内容，会自动清除setType数据
        // 数据内容123456在这里是乱写的，以后会用来做数据传递
//        intent.setData(Uri.parse("example:"+123456));
        // 设置数据格式，会自动清除setData数据
//        intent.setType("timothy/example");
        // 同时设置
        intent.setDataAndType(Uri.parse("example:"+123456),"timothy/example");
        startActivity(intent);
    }
}
