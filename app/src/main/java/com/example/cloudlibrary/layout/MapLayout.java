package com.example.cloudlibrary.layout;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.Paint;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.example.cloudlibrary.PosActivity;
import com.example.cloudlibrary.R;
import com.example.cloudlibrary.util.ButtonInfo;
import com.example.cloudlibrary.util.Communication;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MapLayout extends FrameLayout implements ScaleGestureDetector.OnScaleGestureListener, View.OnClickListener {
    private boolean scaleFlag = false;
    private ScaleGestureDetector scaleGestureDetector;
    private Matrix mMatrix;
    private float scaleX;
    private float maxScale = 4.0f;
    private float minScale = 1.0f;
    private Context mContext;
    private ImageView map;
    private Bitmap mapBitMap;
    private String mapUrl;
    private List<ButtonInfo> buttonInfoList;
    private List<Button> buttonList = new ArrayList<>();
    private boolean isFirst = true;
    private boolean isHelp = false;
    private float downX;
    private float downY;
    private Button helpButton;
    private ObjectAnimator animator;

    public MapLayout(Context context){
        super(context);
        mContext = context;
    }
    public MapLayout(Context context, AttributeSet attrs){
        super(context,attrs);
        mContext = context;
    }
    public MapLayout(Context context, AttributeSet attrs, int defStyleAttr){
        super(context,attrs,defStyleAttr);
        mContext = context;
    }
    public void init(String mapUrl, List<ButtonInfo> bl){
        this.mapUrl = mapUrl;
        this.buttonInfoList = bl;
        map = new ImageView(mContext);
        addView(map);
        for(int i = 0 ; i < buttonInfoList.size() ; i++ ){
            Button y = new Button(mContext);
            addView(y);
            buttonList.add(y);
            y.setOnClickListener(this);
        }
        scaleGestureDetector = new ScaleGestureDetector(mContext,this);
        mMatrix = new Matrix();
        helpButton = new Button(mContext);
        addView(helpButton);
    }

    @Override
    public void onClick(View v) {
        for(int i = 0; i < buttonInfoList.size(); i++){
            if(v == buttonList.get(i)){
                Intent intent = new Intent(mContext, PosActivity.class);
                intent.putExtra("pos",buttonInfoList.get(i).getContent());
                new Communication().addVisited(buttonInfoList.get(i).getContent());
                mContext.startActivity(intent);
            }
        }
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        scaleFlag = true;
        return true;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        if(map.getDrawable() == null){
            Log.d("nulll","!!!");
        }
        float scale = detector.getScaleFactor();
        float preScale = getPreScale();
        if (preScale * scale < maxScale &&
                preScale * scale > minScale) {//preScale * scale可以计算出此次缩放执行的话，缩放值是多少

            //detector.getFocusX()缩放手势中心的x坐标，detector.getFocusY()y坐标
            mMatrix.postScale(scale, scale, detector.getFocusX(), detector.getFocusY());
            map.setImageMatrix(mMatrix);
            makeDrawableCenter();
        }
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {//OnScaleGestureListener里的方法，缩放结束

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if(scaleFlag) break;
                float x = event.getX() - downX;
                float y = event.getY() - downY;
                if((int)x !=0 || (int)y != 0){
                    mMatrix.postTranslate(x,y);
                    map.setImageMatrix(mMatrix);
                    downX = event.getX();
                    downY = event.getY();
                    makeDrawableCenter();
                }
                break;
            case MotionEvent.ACTION_UP:
                scaleFlag = false;
                break;
            default:
                break;
        }
        return scaleGestureDetector.onTouchEvent(event);
    }

    private float getPreScale() {
        float[] matrix = new float[9];
        mMatrix.getValues(matrix);
        return matrix[Matrix.MSCALE_X]/scaleX;
    }

    //缩小的时候让图片居中
    private void makeDrawableCenter() {
        RectF rect = new RectF();
        Drawable d = map.getDrawable();
        if (d != null) {
            rect.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());//设置rect的初始四个角值是图片的四个顶点值
            mMatrix.mapRect(rect);//获取通过当前矩阵变换后的四个角值
        }

        int width = map.getWidth();
        int height = map.getHeight();

        float dx=0;

        // 如果宽或高大于屏幕，则控制范围
        if (rect.width() >= width) {
            if (rect.left > 0) {
                dx = -rect.left;
            }
            if (rect.right < width) {
                dx = width - rect.right;
            }
        }
        float dy = 0;
        if (rect.height() >= height) {
            if (rect.top > 0) {
                dy = -rect.top;
            }
            if (rect.bottom < height){
                dy = height - rect.bottom;
            }
        }

        if (rect.width() < width) {
            dx = width/(float)2 - (rect.right - rect.width()/2);//控件中心点横坐标减去图片中心点横坐标为X方向应移动距离
        }
        if (rect.height() < height) {
            dy = height/(float)2 - (rect.bottom - rect.height()/2);
        }

        if (dx != 0 || dy != 0) {
            mMatrix.postTranslate(dx, dy);
            map.setImageMatrix(mMatrix);
        }
    }

    private void locateButton(){
        RectF rect = new RectF();
        rect.set(0,0,mapBitMap.getWidth(),mapBitMap.getHeight());
        mMatrix.mapRect(rect);
        for(int i = 0; i < buttonInfoList.size(); i++){
            LayoutParams layoutParams = new LayoutParams((int)(20*getPreScale()),(int)(20*getPreScale()));
            layoutParams.topMargin = (int)(buttonInfoList.get(i).getPosY()*rect.height()+rect.top);
            layoutParams.leftMargin = (int)(buttonInfoList.get(i).getPosX()*rect.width()+rect.left);
            Button tmp = buttonList.get(i);
            tmp.setLayoutParams(layoutParams);
        }
    }

    private void drawSequence(Canvas canvas){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        for(int i = 0; i < buttonInfoList.size(); i++){
            Button tmp = buttonList.get(i);
            paint.setTextSize(tmp.getWidth());
            canvas.drawText(buttonInfoList.get(i).getOrder(),(float)(tmp.getLeft()+tmp.getWidth()/5.0),(float)(tmp.getTop()),paint);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if(isFirst) {
            isFirst = false;
            Log.d("dispatchDraw", mapUrl);
            Thread t = new Thread(() -> {
                try{
                    URL url = new URL(mapUrl);
                    Bitmap bt1 = BitmapFactory.decodeStream(url.openStream());
                    double scaleOfX = 5500 / (bt1.getWidth() + 0.0);
                    double scaleOfY = 5500 / (bt1.getHeight() + 0.0);
                    if(scaleOfX < 1 || scaleOfY < 1){
                        Matrix tmp = new Matrix();
                        if(scaleOfX < scaleOfY){
                            tmp.postScale((float)scaleOfX,(float)scaleOfX,0,0);
                        }else{
                            tmp.postScale((float)scaleOfY,(float)scaleOfY,0,0);
                        }
                        Bitmap bt2 = Bitmap.createBitmap(bt1, 0, 0, bt1.getWidth(), bt1.getHeight(), tmp, true);
                        mapBitMap = bt2;
                    }else{
                        mapBitMap = bt1;
                    }
                }catch (Exception e){
                    Log.d("error","error");
                }
            });
            try{
                t.start();
                t.join();
            } catch (InterruptedException e) {
                Log.d("context", String.valueOf(e));
            }
            map.setScaleType(ImageView.ScaleType.MATRIX);
            map.setImageBitmap(mapBitMap);
            scaleX = map.getWidth() / (mapBitMap.getWidth() + 0.0f);
            float scaleY = map.getHeight() / (mapBitMap.getHeight() + 0.0f);
            mMatrix.postScale(scaleX, scaleY);
            map.setImageMatrix(mMatrix);
            for(Button x : buttonList){
                x.setBackgroundResource(R.drawable.shape_circle);
            }
            int count = 0;
            Path path = new Path();
            for(int i = 1; i <= buttonInfoList.size(); i++){
                for(ButtonInfo j : buttonInfoList){
                    if(j.getOrder().equals(i+"")){
                        if(i == 1){
                            isHelp = true;
                            LayoutParams layoutParams = new LayoutParams(40,40);
                            layoutParams.topMargin = (int)(j.getPosY()*getHeight());
                            layoutParams.leftMargin = (int)(j.getPosX()*getWidth());
                            helpButton.setLayoutParams(layoutParams);
                            path.moveTo((float)j.getPosX()*getWidth()-10,(float)j.getPosY()*getHeight()-40);
                        }else{
                            path.lineTo((float)j.getPosX()*getWidth()-10,(float)j.getPosY()*getHeight()-40);
                        }
                        count++;
                    }
                }
            }
            if(isHelp){
                helpButton.setBackgroundResource(R.drawable.help);
                helpButton.setVisibility(VISIBLE);
                animator = ObjectAnimator.ofFloat(helpButton,View.X,View.Y,path);
                animator.setDuration(count * 1000);
                animator.start();
            }else{
                helpButton.setVisibility(INVISIBLE);
            }
        }
        if(isHelp){
            if(!animator.isRunning() && getPreScale() < 1.05){
                helpButton.setVisibility(VISIBLE);
                animator.start();
            }else if(animator.isRunning() && getPreScale() >= 1.05){
                helpButton.setVisibility(INVISIBLE);
                animator.end();
            }
        }
        locateButton();
        super.dispatchDraw(canvas);
        drawSequence(canvas);
    }
}
