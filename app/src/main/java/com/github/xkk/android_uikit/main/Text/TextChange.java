package com.github.xkk.android_uikit.main.Text;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class TextChange extends View {

    private float initX = 350;
    private float offsetX = 0;
    private float lastX = 0;
    private Paint redPaint = new Paint();
    private Paint bluePaint = new Paint();

    public TextChange(Context context) {
        super(context);
    }

    public TextChange(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextChange(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        redPaint.setColor(Color.RED);
        bluePaint.setColor(Color.BLUE);
        redPaint.setTextSize(80);
        redPaint.setStyle(Paint.Style.STROKE);
        bluePaint.setTextSize(80);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.drawText("滑动滑块查看文字颜色变动", 0, 100, redPaint);
        canvas.clipRect(0+offsetX, 0, initX+offsetX, 600, Region.Op.INTERSECT);//设置显示范围
        canvas.drawColor(Color.BLACK);
        canvas.drawText("滑动滑块查看文字颜色变动", 0, 100, bluePaint);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                // 初始化
                offsetX = 0;
                lastX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float offsetx = event.getX()-lastX;
                offsetX = offsetX +offsetx;
                if (Math.abs(offsetx) > 8) {
                    lastX = event.getX();
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                // 通过属性动画返回原点
                initAnimation();
                break;
        }
        return true;
    }

    public void initAnimation(){
        float start = offsetX;
        float end = 0;
        ValueAnimator animator = ValueAnimator.ofFloat(start,end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                offsetX = (float)animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setDuration(1000);
        animator.start();
    }
}
