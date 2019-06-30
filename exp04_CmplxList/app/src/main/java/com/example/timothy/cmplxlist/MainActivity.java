package com.example.timothy.cmplxlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ListView lv_list;

    private String[] name = new String[20];
    private String[] phone = new String[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv_list = findViewById(R.id.lv_list);

        // ArrayAdapter
        // 用于item只有一个内容需要修改
//        String[] obj = {"Xiao Ming", "Xiao Hong", "Zhang San"};
        // 上下文，每个item的布局文件，相互区别的item的id，数组为各item相互区别的内容String[]
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_arr, R.id.arr_tv_text, obj);
//        lv_list.setAdapter(adapter);


        // SimpleAdapter
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        String[] from = {"name", "phone"};  // Map的键
        int[] to = new int[]{R.id.spl_tv_name, R.id.spl_tv_phone};  // 键对应的id

        Map<String, String> item_1 = new HashMap<String, String>();
        item_1.put("name", "Xiao Ming");
        item_1.put("phone", "123456789");
        data.add(item_1);
        Map<String, String> item_2 = new HashMap<String, String>();
        item_2.put("name", "Xiao Hong");
        item_2.put("phone", "987654321");
        data.add(item_2);

        // 上下文，里面有Map数据表的List，布局文件id，Map数据表的键数组String[]，Map键对应的item中组件的id数组int[]
        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.item_spl, from, to);
        lv_list.setAdapter(adapter);

    }

}
