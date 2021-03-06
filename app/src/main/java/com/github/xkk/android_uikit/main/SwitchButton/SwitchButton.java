package com.github.xkk.android_uikit.main.SwitchButton;

import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.github.xkk.android_uikit.R;

public class SwitchButton extends View {
    private Paint bg_paint;// 不变的背景
    private Paint change_paint; // 变化的背景
    private Paint circle_small_paint; // 小圆
    private Paint circle_big_paint; // 大圆
    private Paint big_graay_paint; // 底圆

    private int disable_backgroundColor; // 不变背景颜色
    private int able_backgroundColor; // 变化背景颜色
    private int border_color; // 边框颜色
    private float x = 0; // 圆圈所在

    private RectF big_rectF;
    private RectF small_rectF;

    private ValueAnimator mAnimator;
    private float startX = 0;
    private float scale = 1;
    private OnCheckedChangeListener listener;

    public SwitchButton(Context context) {
        super(context);
    }

    public SwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        // XML获取默认设置属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SwitchButton);
        disable_backgroundColor = array.getColor(R.styleable.SwitchButton_disable_backgroundColor, Color.WHITE);
        able_backgroundColor = array.getColor(R.styleable.SwitchButton_able_backgroundColor, Color.WHITE);
        border_color = array.getColor(R.styleable.SwitchButton_border_color, Color.GRAY);
        array.recycle();
        init();
    }

    public SwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    public void init() {
        bg_paint = new Paint();
        bg_paint.setColor(able_backgroundColor);
        bg_paint.setAntiAlias(true);
        bg_paint.setStyle(Paint.Style.FILL);
        bg_paint.setAlpha(0);

        big_graay_paint = new Paint();
        big_graay_paint.setColor(border_color);
        big_graay_paint.setAntiAlias(true);
        big_graay_paint.setStyle(Paint.Style.FILL);

        change_paint = new Paint();
        change_paint.setColor(Color.WHITE);
        change_paint.setStyle(Paint.Style.FILL);
        change_paint.setAntiAlias(true);

        circle_small_paint = new Paint();
        circle_small_paint.setColor(Color.WHITE);
        circle_small_paint.setAntiAlias(true);
        circle_small_paint.setStyle(Paint.Style.FILL);

        circle_big_paint = new Paint();
        circle_big_paint.setColor(border_color);
        circle_big_paint.setAntiAlias(true);
        circle_big_paint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (big_rectF == null) {
            big_rectF = new RectF(3, 3, getWidth() - 3, getHeight() - 3);
            small_rectF = new RectF(4, 4, getWidth() - 4, getHeight() - 4);
        }
        // 底圆
        canvas.drawRoundRect(big_rectF, (getHeight() - 6) / 2, (getHeight() - 6
        ) / 2, big_graay_paint);

        // 需要放大缩小的背景 这个在变到最大的时候会有一个边框
        canvas.save();
        canvas.scale(scale, scale, getWidth() / 2, getHeight() / 2);
        canvas.drawRoundRect(small_rectF, (getHeight() - 10) / 2, (getHeight() - 10) / 2, change_paint);
        canvas.restore();

        // 画一个圆角长方形 这个只变化透明度
        canvas.drawRoundRect(small_rectF, (getHeight() - 8) / 2, (getHeight() - 8) / 2, bg_paint);

        // 带边框的圆 两个圆组合
        canvas.drawCircle(getHeight() / 2 + x, getHeight() / 2, (getHeight() - 16) / 2, circle_big_paint);
        canvas.drawCircle(getHeight() / 2 + x, getHeight() / 2, (getHeight() - 20) / 2, circle_small_paint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        event.getActionMasked();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_UP:
                // 动画移动圆圈 缩放背景
                if (x == 0 || x == getWidth() - getHeight()) {
                    if (startX == 0) {
                        animateMove();
                    } else {
                        animateBack();
                    }
                }
                break;
        }
        return true;
    }

    /**
     * 左移动到右 左边默认是
     */
    public void animateMove() {

        mAnimator = ValueAnimator.ofFloat(0, getWidth() - getHeight());
        mAnimator.start();
        mAnimator.setDuration(500);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                x = (Float) animation.getAnimatedValue();
                scale = (1 - x / (getWidth() - getHeight()));
                bg_paint.setAlpha((int) (255 * (x / (getWidth() - getHeight()))));
                invalidate();
            }
        });
        startX = getWidth() - getHeight();
        if (this.listener != null) {
            this.listener.onCheckedChanged(true
            );
        }
    }

    public void animateBack() {

        mAnimator = ValueAnimator.ofFloat(getWidth() - getHeight(), 0);
        mAnimator.start();
        mAnimator.setDuration(500);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                x = (Float) animation.getAnimatedValue();
                scale = 1 - (x / (getWidth() - getHeight()));
                bg_paint.setAlpha((int) (255 * (x / (getWidth() - getHeight()))));
                invalidate();
            }
        });
        startX = 0;
        if (this.listener != null) {
            this.listener.onCheckedChanged(false
            );
        }
    }

    /**
     * 返回是否选中
     *
     * @return
     */
    public boolean isChecked() {
        return startX != 0;
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.listener = listener;
    }

    public interface OnCheckedChangeListener {
        public  void onCheckedChanged(boolean isChecked);
    }
}
