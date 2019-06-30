package com.example.timothy.smscontprovd;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

public class MyProvider extends ContentProvider {
    private MyOpenHelper openHelper;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int QUERY_SUCCESS = 0;
    private static final int DELETE_SUCCESS = 1;
    private static final int INSERT_SUCCESS = 2;
    private static final int UPDATE_SUCCESS = 3;

    // 权限与manifest匹配，限定匹配的方法，匹配成功获得的Uri数值
    static {
        sUriMatcher.addURI("com.example.timothy.provider","query",QUERY_SUCCESS);
        sUriMatcher.addURI("com.example.timothy.provider","delete",DELETE_SUCCESS);
        sUriMatcher.addURI("com.example.timothy.provider","insert",INSERT_SUCCESS);
        sUriMatcher.addURI("com.example.timothy.provider","update",UPDATE_SUCCESS);
    }

    @Override
    public boolean onCreate() {
        // 获取一个openHelper,getContext()指当前提供方应用的上下文，自动获取的是MainActivity创建的数据库
        openHelper = new MyOpenHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // 拿着uri去static块找匹配规则
        int result = sUriMatcher.match(uri);
        // 如果匹配成功才执行
        if (result == QUERY_SUCCESS){
            // 获取database
            SQLiteDatabase db = openHelper.getReadableDatabase();
            // 执行query方法
            Cursor cursor = db.query("info", projection, selection, selectionArgs, null, null, sortOrder);
            return cursor;
        }else {
            return null;
        }
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int result = sUriMatcher.match(uri);
        if (result == INSERT_SUCCESS){
            SQLiteDatabase db = openHelper.getReadableDatabase();
            long insert = db.insert("info", null, values);
            return Uri.parse(String.valueOf(insert));
        }else {
            return null;
        }

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int result = sUriMatcher.match(uri);
        if (result == DELETE_SUCCESS){
            SQLiteDatabase db = openHelper.getReadableDatabase();
            int delete = db.delete("info", selection, selectionArgs);
            return delete;
        }else {
            return -1;
        }

    }

    @Override
    public int update(Uri uri, ContentValues values,String selection, String[] selectionArgs) {
        int result = sUriMatcher.match(uri);
        if (result == UPDATE_SUCCESS){
            SQLiteDatabase db = openHelper.getReadableDatabase();
            int up = db.update("info", values, selection, selectionArgs);
            db.close();
            return up;
        }else{
            return -1;
        }
    }
}
