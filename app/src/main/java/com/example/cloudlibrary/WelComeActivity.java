package com.example.cloudlibrary;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.cloudlibrary.util.Communication;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class WelComeActivity extends Activity{
    private static final long SPLASH_LENGTH = 3000;
    Handler handler = new Handler();
    Timer timer = new Timer();
    int recLen = 3;
    TextView tv;
    TextView tvA;
    private  Runnable runnable;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        final String[] image = {""};
        Thread tr = new Thread(() -> {
            try {
                image[0] = new Communication().getWelcomeImage();

            } catch (Exception e) {
                Log.d("context", String.valueOf(e));
            }
        });
        tr.start();
        try {
            tr.join();
        } catch (Exception e) {
            Log.d("context", String.valueOf(e));
        }
        tv = findViewById(R.id.tv_welcome);
        tv.bringToFront();
        tv.setBackgroundResource(R.drawable.corner_view);
        tvA = findViewById(R.id.tv_attention);
        tvA.bringToFront();
        tvA.setBackgroundColor(0x00000000);
        tvA.setText("本应用封面图为手绘图书馆团队作品");
        final ImageView imageView =  (ImageView) findViewById(R.id.imageview_welcome);

        final String finalImage = image[0];
        Thread t = new Thread(() -> {
            try {
                URL url = new URL(finalImage);
                imageView.setImageBitmap(BitmapFactory.decodeStream(url.openStream()));
            } catch (Exception e) {
                Log.d("dede","notFound");
            }
        });
        t.start();
        try {
            t.join();
        } catch (Exception e) {
            Log.d("context", String.valueOf(e));
        }

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // UI thread
                runOnUiThread(() -> {
                    tv.setText("跳过(" + recLen +")");
                    recLen--;
                    if (recLen < 0) {
                        timer.cancel();
                        tv.setVisibility(View.GONE);//倒计时到0隐藏字体
                    }
                });

            }
        };
        timer.schedule(task,1000,1000);
        //使用handler的postDelayed实现延时跳转
        runnable = () -> {
            Intent intent = new Intent(WelComeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        };
        handler.postDelayed(runnable, SPLASH_LENGTH);//3秒后跳转至应用主界面MainActivity
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelComeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                timer.cancel();
                if (runnable != null) {
                    handler.removeCallbacks(runnable);
                }

            }
        });
        new Communication().addVisited("app");
    }

}