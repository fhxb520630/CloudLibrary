package com.example.cloudlibrary;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cloudlibrary.util.Communication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class GradeManagerActivity extends AppCompatActivity {
    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_grade);
        final JSONArray[] jsonArray = new JSONArray[1];
        Thread t = new Thread(() -> {
            try {
                jsonArray[0] = new Communication().getGrade();
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
        int sum = 0;
        for(int i = 0 ; i < 5 ; i++){
            try {
                JSONObject jsonObject= jsonArray[0].getJSONObject(i);
                sum += jsonObject.getInt("sum");
            } catch (JSONException e) {
                Log.d("context", String.valueOf(e));
            }
        }
        TextView t1 = findViewById(R.id.t1);
        ProgressBar p1 = findViewById(R.id.progress_bar_1);
        TextView t2 = findViewById(R.id.t2);
        ProgressBar p2 = findViewById(R.id.progress_bar_2);
        TextView t3 = findViewById(R.id.t3);
        ProgressBar p3 = findViewById(R.id.progress_bar_3);
        TextView t4 = findViewById(R.id.t4);
        ProgressBar p4 = findViewById(R.id.progress_bar_4);
        TextView t5 = findViewById(R.id.t5);
        ProgressBar p5 = findViewById(R.id.progress_bar_5);
        for(int i = 0 ; i < 5 ; i++){
            try {
                JSONObject jsonObject= jsonArray[0].getJSONObject(i);
                int s = jsonObject.getInt("sum");
                String grade = jsonObject.getString("grade");
                switch (grade){
                    case "1.0":
                        t1.setText("  " + s + "/" + sum);
                        p1.setProgress(s * 100/ sum );
                        break;
                    case "2.0":
                        t2.setText("  " + s + "/" + sum);
                        p2.setProgress(s * 100/ sum );
                        break;
                    case "3.0":
                        t3.setText("  " + s + "/" + sum);
                        p3.setProgress(s * 100/ sum );
                        break;
                    case "4.0":
                        t4.setText("  " + s + "/" + sum);
                        p4.setProgress(s * 100/ sum );
                        break;
                    case "5.0":
                        t5.setText("  " + s + "/" + sum);
                        p5.setProgress(s * 100/ sum );
                        break;
                    default:
                        break;
                }


            } catch (JSONException e) {
                Log.d("context", String.valueOf(e));
            }
        }
    }
}
