package com.example.cloudlibrary.util;

import android.content.Context;
import android.graphics.Canvas;

import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FallingView extends View {

    private List<FallObject> fallObjects;

    private int viewWidth;
    private int viewHeight;

    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 1000;
    private static final int INTERVAL_TIME = 5;

    public FallingView(Context context) {
        super(context);
        init();
    }

    public FallingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        fallObjects = new ArrayList<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = measureSize(DEFAULT_HEIGHT, heightMeasureSpec);
        int width = measureSize(DEFAULT_WIDTH, widthMeasureSpec);
        setMeasuredDimension(width, height);

        viewWidth = width;
        viewHeight = height;
    }

    private int measureSize(int defaultSize,int measureSpec) {
        int result = defaultSize;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);

        if (specMode == View.MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == View.MeasureSpec.AT_MOST) {
            result = Math.min(result, specSize);
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(fallObjects.size()>0){
            for (int i=0;i<fallObjects.size();i++) {
                fallObjects.get(i).drawObject(canvas);
            }
            getHandler().postDelayed(runnable, INTERVAL_TIME);
        }
    }

    private Runnable runnable = this::invalidate;

    public void addFallObject(final FallObject fallObject, final int num) {
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                getViewTreeObserver().removeOnPreDrawListener(this);
                for (int i = 0; i < num; i++) {
                    FallObject newFallObject = new FallObject(fallObject.getBuilder(),viewWidth,viewHeight);
                    fallObjects.add(newFallObject);
                }
                invalidate();
                return true;
            }
        });
    }
}
