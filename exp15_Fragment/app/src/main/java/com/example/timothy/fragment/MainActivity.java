package com.example.timothy.fragment;

import android.graphics.Point;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.Button;

// 静态声明fragment：
// xml写fragment布局
// 创建类继承Fragment
// 重写onCreatView
// 在activity_main写fragment，用LinearLayout

// 动态加载fragment：
// xml写fragment布局
// 创建类继承Fragment
// 重写onCreatView
// 在MainActivity获取fragmentmanager
// 开启fragment事务
// replace把对应fragment放入content
// commit提交

// Fragment生命周期
// onCreatView：初始化方法
// onDestroy： 释放资源
// onStop：保存数据

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_a;
    private Button btn_b;
    private Button btn_c;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 横竖屏切换
//        int width = getWindowManager().getDefaultDisplay().getWidth();
//        int height = getWindowManager().getDefaultDisplay().getHeight();
//        // 获取fragmentmanager
//        FragmentManager manager = getSupportFragmentManager();
//        // 开启fragment事务
//        FragmentTransaction transaction = manager.beginTransaction();
//        if(width>height){
//            // 横屏
//            // 把fragment替换到viewgroup
//            // 第一个参数：放置fragment的viewgroup的id
//            // 第二个参数：要显示的fragment对象
//            transaction.replace(R.id.fragment_container, new FourthFragment());
//        }else {
//            // 竖屏
//            transaction.replace(R.id.fragment_container, new ThirdFragment());
//        }
//        // 设置完要用commit提交设置
//        transaction.commit();

        btn_a = findViewById(R.id.btn_a);
        btn_b = findViewById(R.id.btn_b);
        btn_c = findViewById(R.id.btn_c);

        btn_a.setOnClickListener(this);
        btn_b.setOnClickListener(this);
        btn_c.setOnClickListener(this);

        // 初始时让自动点中A
        btn_a.performClick();
    }

    private AFragment aFragment;
    private BFragment bFragment;
    private CFragment cFragment;
    @Override
    public void onClick(View v) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (v.getId()){
            case R.id.btn_a:
                if(aFragment == null){
                    aFragment = new AFragment();
                }
                transaction.replace(R.id.fragment_container, aFragment);
                break;
            case R.id.btn_b:
                if(bFragment == null){
                    bFragment = new BFragment();
                }
                transaction.replace(R.id.fragment_container, bFragment);
                break;
            case R.id.btn_c:
                if(cFragment == null){
                    cFragment = new CFragment();
                }
                transaction.replace(R.id.fragment_container, cFragment);
                break;
            default:break;
        }
        transaction.commit();
    }

    // fragment_a的方法，因为fragment是mainactivity创建的
    // 不推荐的写法
//    public void click(View view){
//        Log.v("MainActivity", "a_click");
//    }
}
