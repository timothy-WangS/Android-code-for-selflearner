package com.example.timothy.exp08_comput;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {
    private EditText et_name;
    private RadioGroup rg_gender;
    private Button btn_calc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_name = findViewById(R.id.et_name);
        rg_gender = findViewById(R.id.rg_gender);
        btn_calc = findViewById(R.id.btn_calc);
    }

    public void calc(View v){
        String name = et_name.getText().toString();
        Boolean isMale = true;
        // 获取信息
        int checked_id = rg_gender.getCheckedRadioButtonId();
        switch (checked_id){
            case R.id.rb_male:
                isMale = true;
                break;
            case R.id.rb_female:
                isMale = false;
                break;
        }
        // 传递给下一个页面activity
        Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
        // 设置数据类型，可传基本数据类型及其数组
        // 以键值对形式
        intent.putExtra("name", name);
        intent.putExtra("gender", isMale);
        // 开启意图
        startActivity(intent);
    }
}
