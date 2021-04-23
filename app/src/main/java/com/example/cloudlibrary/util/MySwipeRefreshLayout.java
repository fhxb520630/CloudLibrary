package com.example.cloudlibrary.util;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MySwipeRefreshLayout extends SwipeRefreshLayout {
    public MySwipeRefreshLayout(@NonNull Context context) {

        super(context);
    }

    public MySwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private int startX;
    private int startY;
    private boolean beginScroll;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("dispatch",ev.toString());
        Log.d("pager?",getParent().toString());
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getX();
                startY = (int) ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int endX = (int) ev.getX();
                int endY = (int) ev.getY();
                int disX = Math.abs(endX - startX);
                int disY = Math.abs(endY - startY);
                Log.d("ACTION_MOVE?",getParent().toString());
                if (disX > disY) {
                    if(!beginScroll)
                        getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    beginScroll = true;
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:

            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                beginScroll = false;
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);

    }
}
