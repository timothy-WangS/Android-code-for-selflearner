package com.example.timothy.sqlitelist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyOpenHelper extends SQLiteOpenHelper{

    public MyOpenHelper(Context context) {
        //public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
        //context：上下文
        //name：数据库名字；如果用null，数据库存在内存，掉电丢失
        //factory：如果使用系统默认，用null
        //version：版本号，控制数据库升级降级
//        super(context, name, factory, version);  // 此处节约书写，预定义name, factory, version变量
        super(context, "timothy.db", null, 2);  // 更改版本号观察onUpgrade和onDowngrade作用
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 数据库第一次创建时会调用onCreate方法
        // 做表结构创建和数据初始化
        // 先在MySQL试一下SQL语句是否正确，不然直接写代码报错不好找
        // SQLite中字段id一般写_id
        // SQLite数据库存储方式都是字符串
        db.execSQL("create table info(_id integer primary key autoincrement, name varchar(20), phone varchar(20))");
        Log.v("MainActivity", "SQLite onCreat");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 当版本号增加时执行onUpgrade方法 oldVersion<newVersion
        // 用oldVersion和newVersion判断用户要做哪些修改

        // 表结构修改：新增字段
        db.execSQL("alter table info add age integer");

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 如需降级，先注释掉super方法，再加降级语句
//        super.onDowngrade(db, oldVersion, newVersion);
        Log.v("MainActivity", "SQLite onDowngrade");
    }
}