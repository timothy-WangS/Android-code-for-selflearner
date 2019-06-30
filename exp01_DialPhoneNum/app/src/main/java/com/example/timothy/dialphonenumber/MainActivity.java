package com.example.timothy.dialphonenumber;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {
    //每一个activity代表一个用户界面，用于用户交互
    private EditText ph_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //通过log输出verbose级别(.v); 在Logcat用过滤器显示"MainActivity"的log
        Log.v("MainActivity", "onCreat run!");
        //当activity创建时执行
        setContentView(R.layout.activity_main);
        ph_num = findViewById(R.id.editText1);
        Button btn_call = findViewById(R.id.button);
        btn_call.setOnClickListener(new MyOnclickListener());
    }

    private class MyOnclickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            //获取输入
            String num = ph_num.getText().toString();
            //判断合法并提示
            if (TextUtils.isEmpty(num)) {
                //Toast.makeText(this, text, time).show()向界面输出提示，参数：
                //上下文，传入当前Activity
                //提示信息字符串
                //提示时间长度
                Toast.makeText(MainActivity.this, "EMPTY!", Toast.LENGTH_SHORT).show();  // 向界面输出提示
            } else {
                //打开拨号界面
                //意图：要进行的操作的抽象描述
                Intent intent = new Intent();
                //给意图设置操作
                intent.setAction(Intent.ACTION_CALL);
                //设置数据为Uri格式，以tel:为协议
                Uri data = Uri.parse("tel:" + num);
                intent.setData(data);
                startActivity(intent);
            }
        }
    }
}
