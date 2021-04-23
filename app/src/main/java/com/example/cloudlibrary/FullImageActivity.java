package com.example.cloudlibrary;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import com.example.cloudlibrary.util.Communication;
import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class FullImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        String p = getIntent().getStringExtra("pos");
        String name = getIntent().getStringExtra("floor");
        VrPanoramaView vrPanoramaView = findViewById(R.id.pano_view);
        vrPanoramaView.setEventListener(new VrPanoramaEventListener());
        vrPanoramaView.setInfoButtonEnabled(false); //设置隐藏最左边信息的按钮
        vrPanoramaView.setStereoModeButtonEnabled(false); //设置隐藏立体模型的按钮
        VrPanoramaView.Options i = new VrPanoramaView.Options();
        i.inputType = VrPanoramaView.Options.TYPE_MONO;
        final byte[][] btImg = {null};
        Thread temp = new Thread(() -> {
            try {
                URL httpUrl = new URL(new Communication().getFloorFullImage(p, name));
                HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5 * 1000);
                InputStream inStream = conn.getInputStream();//通过输入流获取图片数据
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while ((len = inStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                inStream.close();

                btImg[0] = outStream.toByteArray();//得到图片的二进制数据
            } catch (Exception e) {
                Log.d("context", String.valueOf(e));
            }
        });
        temp.start();
        try{
            temp.join();
        } catch (Exception e) {
            Log.d("context", String.valueOf(e));
        }


        vrPanoramaView.loadImageFromByteArray(btImg[0],i);
    }
}
