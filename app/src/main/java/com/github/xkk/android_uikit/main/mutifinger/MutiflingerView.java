package com.github.xkk.android_uikit.main.mutifinger;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.github.xkk.android_uikit.R;

public class MutiflingerView extends View {

    String TAG = "Gcs";

    Bitmap mBitmap;         // 图片
    RectF mBitmapRectF;     // 图片所在区域
    Matrix mBitmapMatrix;   // 控制图片的 matrix
    private Paint paint;
    boolean canDrag = false;
    PointF lastPoint = new PointF(0, 0);


    public MutiflingerView(Context context) {
        super(context);
    }

    public MutiflingerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.outWidth = 960;
        options.outHeight = 960;

        mBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_launcher, options);
        mBitmapRectF = new RectF(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        mBitmapMatrix = new Matrix();

    }

    public MutiflingerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                // ▼ 判断是否是第一个手指 && 是否包含在图片区域内
                if (event.getPointerId(event.getActionIndex()) == 0 && mBitmapRectF.contains((int)event.getX(), (int)event.getY())){
                    canDrag = true;
                    lastPoint.set(event.getX(), event.getY());
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                // ▼ 判断是否是第一个手指
                if (event.getPointerId(event.getActionIndex()) == 0){
                    canDrag = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < event.getPointerCount(); i++) {
                    Log.v("eventMove", ""+event.getX(event.getPointerId(i))+canDrag);
                }
                // 如果存在第一个手指，且这个手指的落点在图片区域内
                if (canDrag) {
                    // ▼ 注意 getX 和 getY
                    int index = event.findPointerIndex(0);
                    // Log.i(TAG, "index="+index);
                    mBitmapMatrix.postTranslate(event.getX(index)-lastPoint.x, event.getY(index)-lastPoint.y);
                    lastPoint.set(event.getX(index), event.getY(index));

                    mBitmapRectF = new RectF(0,0,mBitmap.getWidth(), mBitmap.getHeight());
                    mBitmapMatrix.mapRect(mBitmapRectF);

                    invalidate();
                }
                break;
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, mBitmapMatrix, paint);
    }

}
