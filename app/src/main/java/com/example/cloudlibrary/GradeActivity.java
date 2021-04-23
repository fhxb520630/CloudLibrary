package com.example.cloudlibrary;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cloudlibrary.util.Communication;
import com.example.cloudlibrary.util.FallObject;
import com.example.cloudlibrary.util.FallingView;

import java.util.Objects;

public class GradeActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);
        final RatingBar ratingBar = findViewById(R.id.rgb_ping);
        Button button = findViewById(R.id.confirm);
        button.setOnClickListener(view -> {

            new Communication().addGrade(String.valueOf(ratingBar.getRating()));
            Toast.makeText(GradeActivity.this,"评价了"+ ratingBar.getRating()+"星",Toast.LENGTH_LONG).show();
        });
        @SuppressLint("UseCompatLoadingForDrawables") FallObject.Builder builder = new FallObject.Builder(getResources().getDrawable(R.drawable.lib));
        FallObject fallObject = builder
                .setSpeed(7, true)
                .setSize(150, 150, true)
                .setWind(true, false)
                .build();
        FallingView fallingView = findViewById(R.id.fallingView);
        fallingView.addFallObject(fallObject, 15);
        fallingView.bringToFront();



    }
}

