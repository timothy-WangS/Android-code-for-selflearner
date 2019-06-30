package com.example.timothy.animation;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_pic = findViewById(R.id.iv_pic);

        // drawable图片帧动画
        // 先把资源放到drawable，写animation.xml文件配置动画
        // 在activity_main写ImageView，设置背景为animation
        // 在MainActivity获取ImageView对象，获取背景，start
        ImageView drawable_anim = findViewById(R.id.drawable_anim);
        AnimationDrawable animation = (AnimationDrawable) drawable_anim.getBackground();
        animation.start();
    }

    private ImageView iv_pic;
    // view图片动作动画
    // 创建图片ImageView
    // 获取图片对象
    // 设置动画效果(见下)
    // 给ImageView设置动画并开始

    // 注：view动画只是展示效果，不会修改view。比如设置onClick事件，必须要点在原位置上
    // 注：用xml也可以写动画，需要在res下新建anim文件夹
    public void alpha(View view) {
        // 透明度动画
        // 开始透明度，结束透明度(1.0不透明-0.0全透明)
        AlphaAnimation animation = new AlphaAnimation(0.9f, 0.1f);
        // 设置时长，毫秒
        animation.setDuration(2000);
        // 设置重复方式
        // RESTART：从头开始；REVERSE：反向执行
        animation.setRepeatMode(Animation.RESTART);
        // 设置重复次数，INFINITE：无限重复，0：只执行一次，此时为执行3次
        animation.setRepeatCount(2);
        // 设置最终保存为结束状态
        animation.setFillAfter(true);
        // 设置动画
        iv_pic.setAnimation(animation);
        // 开始播放
        iv_pic.startAnimation(animation);
    }

    public void rotate(View view) {
        // 旋转动画
        // 开始角度，结束角度，x类型，值，y类型，值
        // 类型和值确定旋转中心
        // 类型：RELATIVE_TO_SELF相对自己坐标，RELATIVE_TO_PARENT相对父容器坐标，ABSOLUTE绝对坐标(值为坐标值)
        // 值：小数:相对比例
        // 坐标原点始终是ImageView的左上角，这两个参数只是控制坐标值的大小
        RotateAnimation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(2000);
        animation.setRepeatMode(Animation.RESTART);
        animation.setRepeatCount(Animation.INFINITE);
        iv_pic.setAnimation(animation);
        iv_pic.startAnimation(animation);
    }

    public void scale(View view) {
        // 缩放动画
        // 大小因数，x前后，y前后(看比例)，中心点(同上)
        ScaleAnimation animation = new ScaleAnimation(1,2,1,2,Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(1);
        iv_pic.setAnimation(animation);
        iv_pic.startAnimation(animation);
    }

    public void translate(View view) {
        // 平移动画
        // 开始坐标x，结束坐标x，开始坐标y，结束坐标y
        // 比较复杂的构造(开始x类型，开始x，结束x类型，结束x，开始y类型，开始y，结束y类型，结束y)
        TranslateAnimation animation = new TranslateAnimation(0,400,0,400);
        animation.setDuration(1000);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(1);
        // 设置运动效果，如
        // new AccelerateDecelerateInterpolator()
        // new BounceInterpolator()
        animation.setInterpolator(new BounceInterpolator());
        iv_pic.setAnimation(animation);
        iv_pic.startAnimation(animation);
    }

    public void all(View view) {
        // 动画组，设为不共享
        AnimationSet animSet = new AnimationSet(false);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.9f, 0.3f);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setRepeatMode(Animation.RESTART);
        alphaAnimation.setRepeatCount(2);
        alphaAnimation.setFillAfter(true);
        // 添加到动画组
        animSet.addAnimation(alphaAnimation);

        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2000);
        rotateAnimation.setRepeatMode(Animation.RESTART);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        animSet.addAnimation(rotateAnimation);

        iv_pic.setAnimation(animSet);
        iv_pic.startAnimation(animSet);
    }

    // 用xml写view动画
    // 此处只用了alpha，其他的都可以实现。一般推荐xml写动画，不用代码
    // 比如坐标用 android:pivotX="50%"，指相对自己x轴50%；若"50%p"，指相对父容器
    // 设置运动效果，如android:interpolator = "@android:anim/bounce_interpolator"
    // 其他的xml属性还有duration, repeatMode, repeatCount, fromXScale, toXScale, fromDegrees, toDegrees, fromXDelta, toXDelta等
    public void xml(View view) {
        // 通过AnimationUtils加载动画
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
        iv_pic.setAnimation(animation);
        iv_pic.startAnimation(animation);
    }
}
