package com.example.timothy.loginsd;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class Utils {
    public static boolean saveInfo(Context context, String usn, String pwd){
        String info = usn+"##"+pwd;

//        File file = new File("data/data/com.example.timothy.loginsd/files/info.txt/");
        //使用上下文获取输出流，分开写法
//        File file = new File(context.getFilesDir().getAbsolutePath()+"/info.txt");
//        FileOutputStream fos = new FileOutputStream(file);

        //使用环境获取sd卡输出流，并判断是否正常挂载，分开写法
        //使用sd卡一定加权限(AndroidManifest.xml)
//        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
//            File filesd = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/info.txt");
//            FileOutputStream fosd = new FileOutputStream(filesd);
//        } else {  // 保存到手机或返回错误信息 //  }

        try {
            //使用上下文获取输出流，传入文件名和保存方式，MODE包括APPEND, PRIVATE等
            //存储位置在手机上，一步写法
            FileOutputStream fos = context.openFileOutput("info.txt", Context.MODE_PRIVATE);

            fos.write(info.getBytes());
            fos.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public static String[] readInfo(Context context){
//        File file = new File("data/data/com.example.timothy.loginsd/files/info.txt/");
        //用上下文的配套输入流分开写法
//        File file = new File(context.getFilesDir().getAbsolutePath()+"/info.txt");
//        FileInputStream fis = new FileInputStream(file);
        try {
            //上下文直接获取输入流
            FileInputStream fis = context.openFileInput("info.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String tmp = reader.readLine();
//            Log.v("MainActivity", tmp);
            String[] result = tmp.split("##");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


}
