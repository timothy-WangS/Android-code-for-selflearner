package com.example.timothy.webbrowser;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends AppCompatActivity {

    private EditText et_url;
    private Button btn_show;
    private TextView tv_code;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        // Handler原理
        // -轮询器Looper：取出消息
        // -消息队列MessageQueue：管理消息并按要执行的先后顺序排序
        // -Handler：发消息，处理消息
        // -消息Message：Message.obtain获取消息；Message obj携带参数
        @Override
        public void handleMessage(android.os.Message msg){
            // 在主线程处理Handler消息
            String tmp = (String)msg.obj;
            tv_code.setText(tmp);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 不好，应该开子线程，防止响应请求等待卡死主线程
        // 安卓4.0之后不能再在主线程直接开http请求,NetWorkOnMainThread异常
        // 子线程不能修改界面，用Handler操作：主线程创建Handler，主线程设置接收Message方法，子线程给Handler发消息
//        if (android.os.Build.VERSION.SDK_INT > 9) {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//        }

        et_url = findViewById(R.id.et_url);
        btn_show = findViewById(R.id.btn_show);
        tv_code = findViewById(R.id.tv_code);

        btn_show.setOnClickListener(new MyOnClickListener());

        Thread.currentThread();
    }

    private class MyOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            // 创建子线程
            new Thread(){
                @Override
                public void run() {  // 重写run方法
                    // 获取url
                    String path = et_url.getText().toString();
                    // 联网
                    try {
                        URL url = new URL(path);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        // 设置请求方法，大写，默认GET，也可写POST
                        connection.setRequestMethod("POST");
                        // 设置连接超时，超时连接失败，单位ms
                        connection.setReadTimeout(10000);
                        // 获取响应码
                        int response_code = connection.getResponseCode();
                        // 判断响应码(成功为 200)
                        if (response_code == 200){
                            // 获取响应
                            InputStream inputStream = connection.getInputStream();
                            String result = Utils.getStringFromStream(inputStream);
                            // 显示内容
//                            tv_code.setText(result);
                            //用Handler发Message让主线程修改界面
                            Message msg = new Message();
                            msg.obj = result;
                            handler.sendMessage(msg);
                        }else{
                            Log.v("MainActivity", "Fail:"+response_code);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.v("MainActivity", "Fail: Excp");
                    }
                }
            }.start();
        }
    }
}
