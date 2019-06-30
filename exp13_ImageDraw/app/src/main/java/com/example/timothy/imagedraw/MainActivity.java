package com.example.timothy.imagedraw;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private ImageView iv_image;
    // a_pic.jpg已经存放于mnt/sdcard目录下，随便找的一张图片
    private String path = "mnt/sdcard/a_pic.jpg";
    private Point outsize;

    private float x_strt;
    private float y_strt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_image = findViewById(R.id.iv_img);
        // 设置画画绘画指尖位置输入
        iv_image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                // public void drawrimg前定义了变量canvas，paint，copy
                switch (action){
                    case MotionEvent.ACTION_DOWN:
                        Log.v("MainActivity", "finger touched");
                        x_strt = event.getX();
                        y_strt = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
//                        Log.v("MainActivity", "finger move");
                        // 获取坐标，分散的点
                        float x = event.getX();
                        float y = event.getY();
                        canvas.drawLine(x_strt, y_strt, x, y, paint);
                        x_strt = x;
                        y_strt = y;
                        iv_image.setImageBitmap(copy);
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.v("MainActivity", "finger left");
                        break;
                    default:
                        break;
                }
                // 必须返回true，才能进行处理
                return true;
            }
        });
        // 设置一个存放屏幕信息的变量
        outsize = new Point();
        // 把屏幕信息保存到outsize
        getWindowManager().getDefaultDisplay().getSize(outsize);
    }

    public void loadimg(View view) {
        // 比较图片和屏幕宽高
        // 设置选项
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 如果为true，只解析图片宽高和类型，不会解码图片
        options.inJustDecodeBounds = true;
        // 创建图片对象(路径，选项)
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        // 求宽高
        int width = options.outWidth;
        int height = options.outHeight;
        Log.v("MainActivity", "w "+width+" h "+height);
        // 计算宽高比例
        if(width>outsize.x || height>outsize.y){
            int x_ratio = Math.round((float)width/(float)outsize.x);
            int y_ratio = Math.round((float)height/(float)outsize.y);
            options.inSampleSize = Math.max(x_ratio, y_ratio);
        }
        // 关闭inJustDecodeBounds
        options.inJustDecodeBounds = false;
        // 用新选项再次创建
        bitmap = BitmapFactory.decodeFile(path, options);
        // 把bitmap显示到image view
        iv_image.setImageBitmap(bitmap);
    }

    public void setimg(View view) {
        // getResorces返回res文件夹上下文，用id找图片文件，存于res/drawable下，文件名b_pic.jpg，随便找的一张图片
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.b_pic);
        // 修改图片对应像素点颜色，在res下的图片是不可修改的，需要创建新的
//        bitmap.setPixel(30, 30, Color.RED);
        // 使用原图创建可修改的对象
        // 用宽度，高度，配置信息初始化对象，后面加数的是为了让旋转后全部显示的空余量
        Bitmap copy = Bitmap.createBitmap(bitmap.getWidth()+20, bitmap.getHeight()+20, bitmap.getConfig());
        // 创建画布，传入对象应该是可以修改的
        Canvas canvas = new Canvas(copy);
        // 用于对图片进行处理，如旋转，平移，缩放等，
        Matrix matrix = new Matrix();
        // rotate旋转，传入角度，默认绕左上角;后两个参数是旋转中心，此处设为中心点
//        matrix.setRotate(90, copy.getWidth()/2, copy.getHeight()/2);
        // translate平移，x方向移动30，y不动
//        matrix.setTranslate(30, 0);
        // scale缩放，x变成0.5倍，y变成0.5倍，必须加'f'。如果传负数可以镜像
//        matrix.setScale(0.5f, 0.5f);
        // 上面三个set操作每次set都会撤销上个set，如果需要叠加效果，需要把set改成post，如
        matrix.setScale(-1f, 1f);
        matrix.postTranslate(bitmap.getWidth(), 0);
        // 画笔
        Paint paint = new Paint();
        // 执行后原图会被画到copy上，第一个参数为原图bitmap
        canvas.drawBitmap(bitmap, matrix, paint);
        // 修改指定像素点
        for(int i =0;i<50;i++){
            copy.setPixel(i, i, Color.RED);
        }
        iv_image.setImageBitmap(copy);
    }

    private Canvas canvas;
    private Paint paint;
    private Bitmap copy;
    public void drawrimg(View view) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        copy = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        canvas = new Canvas(copy);
        Matrix matrix = new Matrix();
        paint = new Paint();
        canvas.drawBitmap(bitmap, matrix, paint);
        iv_image.setImageBitmap(bitmap);

    }

    public void color(View view) {
        // 改变画笔颜色
        paint.setColor(Color.RED);
        // 设置画笔透明，显示后面的图片
        // 如果要实现刮刮卡效果，在layout里面同一个位置放两个imageview，底下的为中奖与否，上一层为遮盖
        // 透明画笔划过之后会显示下面一层
//        paint.setColor(Color.TRANSPARENT);
    }

    public void width(View view) {
        // 改变画笔宽度
        paint.setStrokeWidth(5);
    }

    public void save(View view) {
        //保存图片
        // 保存到sd卡路径，时间+.jpg，需要sd卡写权限
        File file = new File(Environment.getExternalStorageDirectory(),System.currentTimeMillis()+".jpg");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            // jpg压缩，100为最高质量、0最低，输出流
            copy.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            // 发送sd卡挂载广播，系统会扫描sd卡，把文件添加到数据库
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_MEDIA_MOUNTED);
            intent.setData(Uri.fromFile(Environment.getExternalStorageDirectory()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
