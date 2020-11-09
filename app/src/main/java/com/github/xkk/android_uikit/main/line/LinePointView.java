package com.github.xkk.android_uikit.main.line;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.github.xkk.android_uikit.R;

public class LinePointView extends View {

    private Paint paint;
    private Paint textPaint;
    private String title;
    private int line_color;
    private float text_size;
    private float margin_left_line;
    private float line_width;

    public LinePointView(Context context) {
        super(context);
    }

    public LinePointView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LinePointView);
        line_color = array.getColor(R.styleable.LinePointView_line_color, Color.BLACK);
        text_size = array.getDimension(R.styleable.LinePointView_title_size, 60);
        title = array.getString(R.styleable.LinePointView_title);
        margin_left_line = array.getDimension(R.styleable.LinePointView_line_margin_left, 15);
        line_width = array.getDimension(R.styleable.LinePointView_line_width, 1);
        array.recycle();
        paint = new Paint();
        paint.setColor(line_color);
        paint.setStrokeWidth(line_width);
        paint.setAntiAlias(true);
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(text_size);
        textPaint.setAntiAlias(true);
    }

    public LinePointView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // 绘制一条线和圆跟着一条线

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        canvas.drawLine(margin_left_line, 0, margin_left_line, height, paint);
        canvas.drawCircle(margin_left_line, height / 2, height / 10, paint);
        float baseLineY = height / 2 + Math.abs(textPaint.ascent() + textPaint.descent()) / 2;
        canvas.drawText(title, 2 * margin_left_line, baseLineY, textPaint);

    }
}
