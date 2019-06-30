package com.example.timothy.exp08_comput;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Random;

public class ResultActivity extends AppCompatActivity {
    private TextView tv_info;
    private TextView tv_result;
    private String name;
    private Boolean isMale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tv_info = findViewById(R.id.tv_info);
        tv_result = findViewById(R.id.tv_result);

        // 获取传来的数据
        Intent intent = getIntent();
        // 取的时候要区分数据类型对应函数，用存数据的键去取数据
        name = intent.getStringExtra("name");
        isMale = intent.getBooleanExtra("gender", true);
        // 设置显示内容
        tv_info.setText("Name: "+ name+ "   Gender: "+ (isMale?"Male":"Female"));
        calc();
    }

    private void calc(){
        Random random = new Random();
        int result = random.nextInt(100);
        if (result>80){
            tv_result.setText("Excellent!");
        }else if(result>60){
            tv_result.setText("Good!");
        }else if(result>40){
            tv_result.setText("Just so so!");
        }else{
            tv_result.setText("Too bad!");
        }
    }
}
