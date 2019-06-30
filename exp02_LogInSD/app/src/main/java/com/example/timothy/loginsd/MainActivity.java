package com.example.timothy.loginsd;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;

public class MainActivity extends AppCompatActivity {
    private EditText et_username;
    private EditText et_password;
    private CheckBox cb_isSave;
    private Button btn_login;
    private TextView tv_free;
    private TextView tv_total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //找到组件
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        cb_isSave = findViewById(R.id.cb_isSave);
        btn_login = findViewById(R.id.btn_login);
        tv_free = findViewById(R.id.tv_freespace);
        tv_total = findViewById(R.id.tv_total);

        //设置事件
        btn_login.setOnClickListener(new MyListener());
        //获取保存的用户名密码
        String[] info = Utils.readInfo(MainActivity.this);
        if(info!=null){
            et_username.setText(info[0]);
            et_password.setText(info[1]);
            cb_isSave.setChecked(true);
            Log.v("MainActivity", "auto fill");
        }else{
            Log.v("MainActivity", "no info");
        }
    }

    private class MyListener implements View.OnClickListener{

        @Override
        public void onClick(View v){
            String usn = et_username.getText().toString();
            String pwd = et_password.getText().toString();
            if(TextUtils.isEmpty(usn)||TextUtils.isEmpty(pwd)){  // 输入不可为空
                Toast.makeText(MainActivity.this,"EMPTY!",Toast.LENGTH_SHORT).show();
            }else {
                Log.v("MainActivity", "Try log in");
                boolean checked = cb_isSave.isChecked();  // 是否保存用户名密码
                if(checked){
                    boolean saveInfo = Utils.saveInfo(MainActivity.this, usn, pwd);  // 调用自己写的保存方法
                    if(saveInfo){
                        Toast.makeText(MainActivity.this,"SAVE SUCCESS!",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(MainActivity.this,"SAVE FAILED!",Toast.LENGTH_SHORT).show();
                    }
                    Log.v("MainActivity", "Save name and key");
                }
                if(usn.equals("1111") && pwd.equals("111111")){  // 如果用户名密码输入正确
                    File storageDirectory = Environment.getExternalStorageDirectory();  // 获取存储目录
                    long freespace = storageDirectory.getFreeSpace();  // 获取空间字符大小
                    long totalspace = storageDirectory.getTotalSpace();

                    String free = Formatter.formatFileSize(MainActivity.this, freespace);  // 转换为KB, MB, GB等格式
                    String total = Formatter.formatFileSize(MainActivity.this, totalspace);

                    tv_free.setText("Free = "+free);
                    tv_total.setText("Total = "+total);
                }else {
                    Toast.makeText(MainActivity.this,"USERNAME OR PASSWORD ERR!",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void MyClick(View v){  // 直接写的点击动作，会传入View参数
        Toast.makeText(this,"CLICK!",Toast.LENGTH_SHORT).show();
    }

}
