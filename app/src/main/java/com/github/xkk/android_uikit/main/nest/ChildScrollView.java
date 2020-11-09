package com.github.xkk.android_uikit.main.nest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class ChildScrollView extends ScrollView {
    public ChildScrollView(Context context) {
        super(context);
    }

    public ChildScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChildScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }


}
