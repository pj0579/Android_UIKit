package com.github.xkk.android_uikit.main.nest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class ParentScrollVew extends ScrollView {
    boolean isFirstIntercept = false;
    public ParentScrollVew(Context context) {
        super(context);
    }

    public ParentScrollVew(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParentScrollVew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {
            isFirstIntercept = true;
        }

        boolean result = super.onInterceptTouchEvent(ev);

        Log.v("result",ev.getAction()+""+result+"");

        if (result && isFirstIntercept) {
            isFirstIntercept = false;
            return false;

        }

        return result;
    }
}
