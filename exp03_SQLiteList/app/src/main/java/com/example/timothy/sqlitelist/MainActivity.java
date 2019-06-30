package com.example.timothy.sqlitelist;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private MyOpenHelper openHelper;
    private ListView lv_list;

    private String[] name = new String[20];
    private String[] phone = new String[20];

    private ArrayList<Person> persons = new ArrayList<Person>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_list = findViewById(R.id.lv_list);
        openHelper = new MyOpenHelper(this);

    }

    public void query(View v){

        SQLiteDatabase database = openHelper.getReadableDatabase();
        String sql = "select * from info";
        Cursor cursor = database.rawQuery(sql, null);
        // 用cursor遍历结果，默认指向所有结果前一行

        while (cursor.moveToNext()){
            // 通过列索引取出当前行
            // name处于第一列(0, 1, 2...)
//            cursor.getString(1);
            // 通过字段名获取索引(列)
            String per_name = cursor.getString(cursor.getColumnIndex("name"));
            String per_phone = cursor.getString(cursor.getColumnIndex("phone"));
            Log.v("MainActivity",per_name + "-" + per_phone);
        }
        cursor.close();
        database.close();
    }

    public void insert(View v){
        // 获取db对象
        //getReadableDatabase与getWritableDatabase 在大部分情况一样，在磁盘满的时候Writable报错，Readable返回只读

        // 用谷歌封装的api实现，也可类似实现query，delete，upgrade
//        SQLiteDatabase database = openHelper.getReadableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("name", "Zhang San");  // 列名， 值
//        values.put("phone", "193746285");
//        values.put("age", 20);
//        String nullColumnHack = "name";  // 禁止insert into info null values null, 如果传入用该变量替代第一个null；可定义为null
//        database.insert("info", nullColumnHack, values);  // 若出错返回值为-1，若正确返回对应_id

        // 普通的sql语句实现
        SQLiteDatabase database = openHelper.getReadableDatabase();
        String sql_1 = "insert into info (name, phone, age) values('Xiao Ming', '123456789', 30)";
        // 用SQLiteDatabase可以直接执行SQL语句
        database.execSQL(sql_1);
        String sql_2 = "insert into info (name, phone, age) values('Xiao Hong', '987654321', 30)";
        database.execSQL(sql_2);
        database.close();
    }

    public void delete(View v){
        SQLiteDatabase database = openHelper.getReadableDatabase();
        String sql_1 = "delete from info where name = 'Xiao Ming'";
        String sql_2 = "delete from info where name = 'Xiao Hong'";
        database.execSQL(sql_1);
        database.execSQL(sql_2);
        database.close();
    }

    public void upgrd(View v){
        SQLiteDatabase database = openHelper.getReadableDatabase();
        String sql = "update info set phone = '111111111' where name = 'Xiao Hong'";
        database.execSQL(sql);
        database.close();
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount(){
            // 决定listview显示条目数
//            return 20;
            return persons.size();
        }

        @Override
        public Object getItem(int position) {
            return persons.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 这样会浪费效率，导致崩溃
//            TextView tv_text = new TextView(MainActivity.this);
//            TextView tv_text = null;

            // 复杂ListView
            // 没有可重用的View对象，需要创建新的
            View tv_text = null;
            if(convertView == null) {
                tv_text = View.inflate(MainActivity.this, R.layout.item, null);
            } else {
                // 有可以重用的View
                tv_text = convertView;
            }
            // 给tv_text设置数据
            // 找要修改的对象
            // 注意，到tv_text里面找对应id，不然会报空指针
            TextView name = tv_text.findViewById(R.id.tv_name);
            TextView phone = tv_text.findViewById(R.id.tv_phone);
            // 获取要显示的数据
            Person person = persons.get(position);
            name.setText(person.name);
            phone.setText(person.phone);

            // 简单的ListView
//            TextView tv_text = null;
//             // 没有可重用的View对象，需要创建新的
//            if(convertView == null) {
//                tv_text = new TextView(MainActivity.this);
//            } else {
//                // 有可以重用的View
//                tv_text = (TextView) convertView;
//            }
//            // 给tv_text设置数据
//            tv_text.setText(persons.get(position).name+"-"+persons.get(position).phone);

            return tv_text;
        }

    }
    public void rfreshList(View v){

        SQLiteDatabase database = openHelper.getReadableDatabase();
        String sql = "select * from info";
        Cursor cursor = database.rawQuery(sql, null);

        persons.clear();
        while (cursor.moveToNext()){
            Person person = new Person();
            person.name = cursor.getString(cursor.getColumnIndex("name"));
            person.phone = cursor.getString(cursor.getColumnIndex("phone"));
            persons.add(person);
        }

        cursor.close();
        database.close();
        for (Person person:persons){
            Log.v("MainActivity",person.toString());
        }

        lv_list.setAdapter(new MyAdapter());
    }

}
