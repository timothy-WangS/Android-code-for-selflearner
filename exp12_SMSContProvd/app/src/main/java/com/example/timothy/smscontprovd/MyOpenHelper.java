package com.example.timothy.smscontprovd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {
    public MyOpenHelper(Context context){
        super(context, "smscontprovd.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table info(_id integer primary key autoincrement,name varchar(20),phone varchar(20))");
        db.execSQL("insert into info(name,phone) values('Xiao Ming','123456789')");
        db.execSQL("insert into info(name,phone) values('Xiao Hong','123456789')");
        db.execSQL("insert into info(name,phone) values('Zhang San','123456789')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
