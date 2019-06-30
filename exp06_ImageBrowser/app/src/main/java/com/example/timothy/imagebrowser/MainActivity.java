package com.example.timothy.imagebrowser;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    private EditText et_url;
    private Button btn_show;
    private ImageView iv_img;

    // 使用最终变量判定连接情况
    private final int GET_DATA_SUCESS = 1;
    private final int BAD_REQUEST = 2;
    private final int NETWORK_ERR = 3;



    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GET_DATA_SUCESS:
                    Bitmap bm = (Bitmap) msg.obj;
                    iv_img.setImageBitmap(bm);
                    Toast.makeText(MainActivity.this, "CONNECTED", Toast.LENGTH_SHORT).show();
                    break;
                case BAD_REQUEST:
                    Toast.makeText(MainActivity.this, "BAD REQUEST", Toast.LENGTH_SHORT).show();
                    break;
                case NETWORK_ERR:
                    Toast.makeText(MainActivity.this, "NETWORK ERR", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(MainActivity.this, "UNKNOWN ERR", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_url = findViewById(R.id.et_url);
        btn_show = findViewById(R.id.btn_show);
        iv_img = findViewById(R.id.iv_img);
        et_url.setText("https://timothy-wangs.github.io/portrait.jpg");

        btn_show.setOnClickListener(new MyOnClickListener());
    }

    private class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            // 保存图片缓存
            final File file = new File(getCacheDir(),"img.jpg");

            // 如果已经有缓存，直接读取
            if(file != null && file.length()>0){
                Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
                iv_img.setImageBitmap(bm);
                Toast.makeText(MainActivity.this, "COOKIE", Toast.LENGTH_SHORT).show();
                return;
            }

            // 如果没有缓存，开线程联网读取
            new Thread(){
                @Override
                public void run() {
                    // 获取网址，详见exp05
                    String path = et_url.getText().toString();
                    try {
                        URL url = new URL(path);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setConnectTimeout(10000);
                        int code = connection.getResponseCode();
                        if (code == 200){
                            InputStream inputStream = connection.getInputStream();
                            {
                                // 图片缓存输出流
                                FileOutputStream fos = new FileOutputStream(file);
                                int len = -1;
                                byte[] buffer = new byte[1024];
                                // 读取输入流
                                try{
                                    while((len = inputStream.read(buffer))!=-1){
                                        fos.write(buffer, 0, len);
                                    }
                                    fos.close();
                                    inputStream.close();
                                }catch (IOException e){
                                    e.printStackTrace();
                                    fos.close();
                                    inputStream.close();
                                }
                            }
                            // 请求图片对象，需要用BitmapFactory，它也可以解析文件，字符串
//                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            // 如果加了缓存，图片先存到缓存，然后bitmap直接从缓存读
                            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                            // 创建消息
                            Message msg = Message.obtain();
                            // 消息携带bitmap
                            msg.what = GET_DATA_SUCESS;
                            msg.obj = bitmap;
                            // 发送消息
                            handler.sendMessage(msg);
                        }else{
                            Message msg = Message.obtain();
                            msg.what = BAD_REQUEST;
                            handler.sendMessage(msg);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Message msg = Message.obtain();
                        msg.what = NETWORK_ERR;
                        handler.sendMessage(msg);
                    }
                }
            }.start();
        }
    }
}
