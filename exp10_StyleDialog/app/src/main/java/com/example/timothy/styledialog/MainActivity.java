package com.example.timothy.styledialog;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dialog(View view){
        // 对话框创建的上下文必须用activity，比如用this，不能用getApplicationContext
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 对话框标题
        builder.setTitle("Dialog");
        // 对话框内容
        builder.setMessage("content of dialog");
        // 设置"确定"和"取消"按钮
        // 显示在按钮上的文字，点击后的操作
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.v("MainActivity","Yes");
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.v("MainActivity","No");
            }
        });
        // 设置完之后要调用show方法
        builder.show();
    }


    public void radDlg(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chose from below");
        // 等待选择的选项
        final String[] items = {"a", "b", "c"};
        // 默认选中的，为-1无选择
        int checked = -1;
        builder.setSingleChoiceItems(items, checked, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.v("MainActivity",items[which]);
                // 选择之后对话框消失
                dialog.dismiss();
            }
        });
        builder.show();
    }


    private boolean[] checked;
    public void chkDlg(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chose from below");
        // 等待选择的选项
        final String[] items = {"a", "b", "c"};
        // 默认选中的，元素数应等于items数
        checked = new boolean[]{false, false, false};
        builder.setMultiChoiceItems(items, checked, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checked[which] = isChecked;
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i=0;i<items.length;i++){
                    if(checked[i])
                        Log.v("MainActivity",items[i]+" is checked");
                }
            }
        });
        builder.show();
    }


    public void prgrsDlg(View view){
        // 现已弃置
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setTitle("Loading...");
        // 设置最大进度
        dialog.setMax(100);
        dialog.show();
        // 开子线程模拟耗时操作
        new Thread(){
            @Override
            public void run() {
                for(int i=0;i<100;i++){
                    // 进度条对话框可以在子线程更新进度
                    dialog.setProgress(i);
                    SystemClock.sleep(100);
                }
                dialog.dismiss();
            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void notif(View view) {
        // 通过notification.Builder创建Builder
        Notification.Builder builder = new Notification.Builder(this);
        // 通知第一次收到会在状态栏显示简要信息提示文字
        builder.setTicker("NOTIFICATION！");
        // 当用户触摸时自动消失
        builder.setAutoCancel(true);
        // 下拉之后的标题
        builder.setContentTitle("NOTIF!");
        // 下拉正文
        builder.setContentText("You have some message for check.");
        // 小图标
        builder.setSmallIcon(R.mipmap.ic_launcher);
        // 正在进行的通知，若为true用户不能清除，如音乐播放器通知栏进度条、下载操作等
        builder.setOngoing(false);
        // 设置点击通知事件
        int requestcode = 1;  // 一个应用可能有多个通知，这个作为不同通知识别码
        Intent[] intent2 = {new Intent(this, MainActivity.class)};  // 设置目标打开的activity
        int flags = PendingIntent.FLAG_UPDATE_CURRENT;  // 标志位，若识别码相同，用这个清除之前的
        PendingIntent intent = PendingIntent.getActivities(getApplicationContext(), requestcode, intent2, flags);
        builder.setContentIntent(intent);
        // build为API16之后才能用的方法
        Notification notification = builder.build();
        // 获取NotificationManager
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // 调用这个方法发送通知
        manager.notify(1, notification);
    }
}
