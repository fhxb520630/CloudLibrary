package com.example.cloudlibrary.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import java.util.Random;

public class FallObject {
    private int initX;
    private int initY;
    private Random random;
    private int parentWidth;
    private int parentHeight;
    private float objectHeight;

    private int initSpeed;
    private int initWindLevel = 5;
    private float presentX;
    private float presentY;
    private float presentSpeed;
    private float angle;

    private Bitmap bitmap;

    public Builder getBuilder() {
        return builder;
    }

    private Builder builder;

    private boolean isSpeedRandom;
    private boolean isSizeRandom;
    private boolean isWindRandom;
    private boolean isWindChange;

    private static final int DEFAULT_SPEED = 10;
    private static final int DEFAULT_WIND_SPEED = 10;
    private static final float HALF_PI = (float) Math.PI / 2;

    public FallObject(Builder builder, int parentWidth, int parentHeight){
        random = new Random();
        this.parentWidth = parentWidth;
        this.parentHeight = parentHeight;
        initX = random.nextInt(parentWidth);
        initY = random.nextInt(parentHeight)- parentHeight;
        presentX = initX;
        presentY = initY;

        this.builder = builder;
        isSpeedRandom = builder.isSpeedRandom;
        isSizeRandom = builder.isSizeRandom;
        isWindRandom = builder.isWindRandom;
        isWindChange = builder.isWindChange;

        initSpeed = builder.initSpeed;
        randomSpeed();
        randomSize();
        randomWind();
    }

    private FallObject(Builder builder) {
        this.builder = builder;
        initSpeed = builder.initSpeed;
        bitmap = builder.bitmap;

        isSpeedRandom = builder.isSpeedRandom;
        isSizeRandom = builder.isSizeRandom;
        isWindRandom = builder.isWindRandom;
        isWindChange = builder.isWindChange;
    }

    public static final class Builder {
        private int initSpeed;
        private Bitmap bitmap;

        private boolean isSpeedRandom;
        private boolean isSizeRandom;
        private boolean isWindRandom;
        private boolean isWindChange;

        public Builder(Bitmap bitmap) {
            this.initSpeed = DEFAULT_SPEED;
            this.bitmap = bitmap;

            this.isSpeedRandom = false;
            this.isSizeRandom = false;
            this.isWindRandom = false;
            this.isWindChange = false;
        }

        public Builder(Drawable drawable) {
            this.initSpeed = DEFAULT_SPEED;
            this.bitmap = drawableToBitmap(drawable);

            this.isSpeedRandom = false;
            this.isSizeRandom = false;
            this.isWindRandom = false;
            this.isWindChange = false;
        }

        public Builder setSpeed(int speed) {
            this.initSpeed = speed;
            return this;
        }

        public Builder setSpeed(int speed,boolean isRandomSpeed) {
            this.initSpeed = speed;
            this.isSpeedRandom = isRandomSpeed;
            return this;
        }

        public Builder setSize(int w, int h){
            this.bitmap = changeBitmapSize(this.bitmap,w,h);
            return this;
        }

        public Builder setSize(int w, int h, boolean isRandomSize){
            this.bitmap = changeBitmapSize(this.bitmap,w,h);
            this.isSizeRandom = isRandomSize;
            return this;
        }

        public Builder setWind(boolean isWindRandom, boolean isWindChange){
            this.isWindRandom = isWindRandom;
            this.isWindChange = isWindChange;
            return this;
        }

        public FallObject build() {
            return new FallObject(this);
        }
    }

    public void drawObject(Canvas canvas){
        moveObject();
        canvas.drawBitmap(bitmap,presentX,presentY,null);
    }

    private void moveObject(){
        moveX();
        moveY();
        if(presentY>parentHeight || presentX<-bitmap.getWidth() || presentX>parentWidth+bitmap.getWidth()){
            reset();
        }
    }

    private void moveX(){
        presentX += DEFAULT_WIND_SPEED * Math.sin(angle);
        if(isWindChange){
            angle += (float) (random.nextBoolean()?-1:1) * Math.random() * 0.0025;
        }
    }

    private void moveY(){
        presentY += presentSpeed;
    }

    private void reset(){
        presentY = -objectHeight;
        randomSpeed();
        randomWind();
    }

    private void randomSpeed(){
        if(isSpeedRandom){
            presentSpeed = (float)((random.nextInt(3)+1)*0.1+1)* initSpeed;
        }else {
            presentSpeed = initSpeed;
        }
    }

    private void randomSize(){
        if(isSizeRandom){
            float r = (random.nextInt(10)+1)*0.1f;
            float rW = r * builder.bitmap.getWidth();
            float rH = r * builder.bitmap.getHeight();
            bitmap = changeBitmapSize(builder.bitmap,(int)rW,(int)rH);
        }else {
            bitmap = builder.bitmap;
        }
        objectHeight = bitmap.getHeight();
    }

    private void randomWind(){
        if(isWindRandom){
            angle = (float) ((random.nextBoolean()?-1:1) * Math.random() * initWindLevel /50);
        }else {
            angle = (float) initWindLevel /50;
        }

        if(angle>HALF_PI){
            angle = HALF_PI;
        }else if(angle<-HALF_PI){
            angle = -HALF_PI;
        }
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap changeBitmapSize(Bitmap bitmap, int newW, int newH) {
        int oldW = bitmap.getWidth();
        int oldH = bitmap.getHeight();

        float scaleWidth = ((float) newW) / oldW;
        float scaleHeight = ((float) newH) / oldH;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        bitmap = Bitmap.createBitmap(bitmap, 0, 0, oldW, oldH, matrix, true);
        return bitmap;
    }
}
