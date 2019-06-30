package com.example.timothy.webbrowser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

class Utils {
    public static String getStringFromStream(InputStream inputStream){
        ByteArrayOutputStream baso = new ByteArrayOutputStream();
        int len = -1;
        byte[] buffer = new byte[1024];
        // 读取输入流
        try{
            while((len = inputStream.read(buffer))!=-1){
                baso.write(buffer, 0, len);
            }
            // 转字节数组
            byte[] byteArray = baso.toByteArray();
            baso.close();
            inputStream.close();
            // 转字符串数组并返回
            return new String(byteArray);
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
