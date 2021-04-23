package com.example.cloudlibrary.util;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.cloudlibrary.R;



public class EditTextClear extends AppCompatEditText{


    private Drawable clearDrawable;
    private Drawable searchDrawable;

    public EditTextClear(Context context) {
        super(context);
        init();
    }

    public EditTextClear(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextClear(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        clearDrawable = getResources().getDrawable(R.drawable.clear);
        searchDrawable = getResources().getDrawable(R.drawable.search);

        setCompoundDrawablesWithIntrinsicBounds(searchDrawable, null,
                null, null);
    }



    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        setClearIconVisible(hasFocus() && text.length() > 0);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        setClearIconVisible(focused && length() > 0);
    }

    private void setClearIconVisible(boolean visible) {
        setCompoundDrawablesWithIntrinsicBounds(searchDrawable, null,
                visible ? clearDrawable : null, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_UP ){
                Drawable drawable = clearDrawable;
                if (drawable != null && event.getX() <= (getWidth() - getPaddingRight())
                        && event.getX() >= (getWidth() - getPaddingRight() - drawable.getBounds().width())) {
                    setText("");
                }
        }

        return super.onTouchEvent(event);
    }


}

