package com.example.cloudlibrary;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.example.cloudlibrary.util.Communication;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URL;
import java.util.Objects;

public class PosActivity extends AppCompatActivity {
    private ImageView imageView;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private  String[] permissionsStorage = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };
    public  void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, permissionsStorage,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos);
        TextView textView = (TextView) findViewById(R.id.pos_text);
        imageView = (ImageView) findViewById(R.id.imageview1);
        VideoView videoView = (VideoView) findViewById(R.id.videoview);
        TextView posName = (TextView) findViewById(R.id.pos_name);
        String name = getIntent().getStringExtra("pos");
        TextView visited = (TextView) findViewById(R.id.pos_visited);
        posName.setText(name);
        TextView videoText = (TextView) findViewById(R.id.video_text);

        verifyStoragePermissions(this);
        String describe = "";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new Communication().getInformation(name);

        } catch (JSONException e) {
            Log.d("context", String.valueOf(e));
        }
        try {
            describe = jsonObject.getString("describe");
        } catch (JSONException e) {
            Log.d("context", String.valueOf(e));
        }
        textView.setText(describe);


        String mUrl = "http://166.111.226.244:11352/";
        String image = "";
        try {
            image = jsonObject.getString("image");
        } catch (JSONException e) {
            Log.d("context", String.valueOf(e));
        }
        if( ! "media/".equals(image)){
            final String finalImage = image;
            Thread t = new Thread(() -> {
                try {
                    URL url = new URL(mUrl+ finalImage);
                    imageView.setImageBitmap(BitmapFactory.decodeStream(url.openStream()));
                } catch (Exception e) {
                    Log.d("context", String.valueOf(e));
                }
            });
            t.start();
            try {
                t.join();
            } catch (Exception e) {
                Log.d("context", String.valueOf(e));
            }
        }else{
            imageView.setVisibility(View.GONE);
        }
//        String video = "";
//        try {
//            video = jsonObject.getString("video");
//        } catch (JSONException e) {
//            Log.d("context", String.valueOf(e));
//        }
//        final Uri[] uri = {null};
//        if( !"media/".equals(video)){
//            final String finalVideo = video;
//            Thread t = new Thread(() -> {
//                try {
//                    String url = mUrl+ finalVideo;
//                    uri[0] = Uri.parse( url );
//                } catch (Exception e) {
//                    Log.d("context", String.valueOf(e));
//                }
//            });
//            t.start();
//            try {
//                t.join();
//            } catch (Exception e) {
//                Log.d("context", String.valueOf(e));
//            }
//            videoView.setMediaController(new MediaController(this));
//            videoView.setVideoURI(uri[0]);
//            videoText.setVisibility(View.GONE);
//        }else {
//            videoView.setVisibility(videoView.GONE);
//        }

//        play_example(videoView);
        play_from_remote(videoView);

        visited.setText("访问次数："+new Communication().getVisited(name));
    }
    private void play_example(VideoView videoView) {
        String videoUrl1 = "/storage/emulated/0/Movies/Browser/video.mp4";   // 或者 file:///storage/emulated/0/test.mp4
        Uri uri = Uri.parse( videoUrl1 );
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(uri);
        videoView.start();
    }

    private void play_from_remote(VideoView videoView) {
        String videoUrl2 = "http://121.199.20.70:8080/download/uploads/video.mp4" ;
        Uri uri = Uri.parse( videoUrl2 );
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();
    }

}


