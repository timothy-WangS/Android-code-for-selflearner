package com.example.timothy.smscontprovd;

import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;

import org.xmlpull.v1.XmlSerializer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyOpenHelper helper = new MyOpenHelper(this);

        // 创建数据库并放入内容
        db = helper.getReadableDatabase();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    // 这个query方法可以在别的应用实现这个应用数据库查询
    public void query(View view) {
        // 创建一个内容解析者
        ContentResolver contentResolver = getContentResolver();
        // 与MyProvider的static块相同，先是authority，然后是path
        Uri uri = Uri.parse("content://com.example.timothy.provider/query");
        Cursor cursor = contentResolver.query(uri, null, null,null, null);
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            Log.v("MainActivity", name+"-"+phone);
        }
    }

    // 这个delete方法可以在别的应用实现这个应用数据库查询
    public void delete(View view) {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = Uri.parse("content://com.example.timothy.provider/delete");
        String where = "name = ?";
        String[] selectionArgs = {"Zhang San"};
        int del = contentResolver.delete(uri, where, selectionArgs);
        Log.v("MainActivity", "delete"+del+"notes");
    }

    // 这个insert方法可以在别的应用实现这个应用数据库查询
    public void insert(View view) {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = Uri.parse("content://com.example.timothy.provider/insert");
        ContentValues values = new ContentValues();
        values.put("name", "Li Si");
        values.put("phone","456789123");
        Uri insert = contentResolver.insert(uri, values);
        Log.v("MainActivity", String.valueOf(insert));
    }

    // 这个update方法可以在别的应用实现这个应用数据库查询
    public void update(View view) {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = Uri.parse("content://com.example.timothy.provider/update");
        ContentValues values = new ContentValues();
        values.put("phone","555555555");
        String where = "name = ?";
        String[] selectionArgs = {"Xiao Ming"};
        int up = contentResolver.update(uri, values, where, selectionArgs);
        Log.v("MainActivity", "updated"+up+"notes");
    }

    //查询短信
    public void smsquery(View view) {
        ContentResolver contentResolver = getContentResolver();
        // 系统短信的authority为sms，查询全部的短信路径null
        Uri uri = Uri.parse("content://sms");

        String[] projection = {"address","date","body"};
        Cursor cursor = contentResolver.query(uri, projection, null, null, null);
        while (cursor.moveToNext()){
            String address = cursor.getString(cursor.getColumnIndex("address"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String body = cursor.getString(cursor.getColumnIndex("body"));
            Log.v("MainActivity", "at "+date+", "+address+" sayed: "+body);
        }
    }

    // 保存短信
    private ArrayList<SmsBackup> smslist = new ArrayList<SmsBackup>();
    @SuppressLint("WrongConstant")
    public void smsbackup(View view) throws FileNotFoundException {
        ContentResolver contentResolver = getContentResolver();
        // 系统短信的authority为sms，查询全部的短信路径null
        Uri uri = Uri.parse("content://sms");

        String[] projection = {"address","date","body"};
        Cursor cursor = contentResolver.query(uri, projection, null, null, null);
        while (cursor.moveToNext()){
            SmsBackup sms = new SmsBackup();
            sms.address = cursor.getString(cursor.getColumnIndex("address"));
            sms.date = cursor.getString(cursor.getColumnIndex("date"));
            sms.body = cursor.getString(cursor.getColumnIndex("body"));
            smslist.add(sms);
        }
        // 测试是否保存到smslist
//        for(SmsBackup sms:smslist){
//            Log.v("MainActivity", sms.toString());
//        }
        // 设置Xml序列化器保存文件
        XmlSerializer serializer = Xml.newSerializer();
        try {
            // 创建一个Xml序列化器，设置输出
            serializer.setOutput(openFileOutput("sms.xml",MODE_PRIVATE),"utf-8");
            // 写入内容
            // 开头
            serializer.startDocument("utf-8",true);
            //开始标签
            serializer.startTag(null, "SmsList");
            //正文部分for循环
            for(SmsBackup sms:smslist){
                // 单个条目开始标签
                serializer.startTag(null, "sms");
                // 具体属性
                serializer.startTag(null, "address");
                serializer.text(sms.address);
                serializer.endTag(null, "address");

                serializer.startTag(null, "date");
                serializer.text(sms.date);
                serializer.endTag(null, "date");

                serializer.startTag(null, "body");
                serializer.text(sms.body);
                serializer.endTag(null, "body");

                // 单个条目结束标签
                serializer.endTag(null, "sms");
            }
            //结束标签
            serializer.endTag(null,"SmsList");
            //结尾
            serializer.endDocument();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
