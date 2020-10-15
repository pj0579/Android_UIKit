package com.github.xkk.android_uikit.main.measure_spec;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MeasureView extends View {
    public int widthMeasureSpec;
    public String mode;

    public MeasureView(Context context) {
        super(context);
    }

    public MeasureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    

    public MeasureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public String getMode() {
        return mode;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.widthMeasureSpec = widthMeasureSpec;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int mMode = MeasureSpec.getMode(widthMeasureSpec) >>> 30;
        mode = mMode == 2 ? "AT_MOST" : "EXECTLY";
        Log.v("measureSpecMode", mMode + (mMode == 2 ? "AT_MOST" : "EXECTLY"));
    }

}
