package com.github.xkk.android_uikit.main.wangyiyun;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class WangyiyunView extends ViewGroup {
    private List<Integer> indexList = new ArrayList<>(); // 循环滚动里面的数据
    private boolean isBeingDragged = false;
    private float lastX;
    private float lastY;
    private float mOffsetPercent;
    private float mOffsetX;
    private boolean isReordered = false;

    public WangyiyunView(Context context) {
        super(context);
    }

    public WangyiyunView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WangyiyunView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = indexList.size();
        for (int i = 0; i < count; i++) {
            // 布局i下的View
            View view = getChildAt(i);
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = indexList.size();
        for (int i = 0; i < count; i++) {
            // 布局i下的View
            View view = getChildAt(i);
            layoutChild(view, i);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_UP:
                float offsetX = x - lastX;
                float offsetY = y - lastY;
                if (Math.abs(offsetX) > 8 && Math.abs(offsetY) > 8) {
                    lastX = x;
                    lastY = y;
                    isBeingDragged = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                isBeingDragged = false;
                break;
        }
        return isBeingDragged;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 滚动处理
        float x = event.getX();
        float y = event.getY();
        float offsetX;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                offsetX = x - lastX;
                Log.v("offSetX", offsetX + "");
                mOffsetX += offsetX;
                onItemMove();
                break;
            case MotionEvent.ACTION_MOVE:
                offsetX = x - lastX;
                mOffsetX += offsetX;
                onItemMove();
                break;
            case MotionEvent.ACTION_UP:
                isBeingDragged = false;
                break;
        }
        lastX = x;
        lastY = y;
        return true;
    }



    private void onItemMove() {
        //更新滑动百分比
        mOffsetPercent = mOffsetX / getMeasuredWidth();
        Log.v("test", "mOffsetX:" + mOffsetX + "width:" + getMeasuredWidth());
        //更新子View的出发点和目的地
        updateChildrenFromAndTo();
        //更新子View的层级顺序
        updateChildrenOrder();
        updateChildrenAlphaAndScale();
        //请求重新布局
        requestLayout();
    }

    private void updateChildrenAlphaAndScale() {
        //遍历子View
        for (int i = 0; i < 3; i++) {
            updateAlphaAndScale(getChildAt(i));
        }
    }


    public void updateAlphaAndScale(View v) {
        ScaleAndAlphaParams params = (ScaleAndAlphaParams) v.getLayoutParams();
        switch (params.from) {
            case 0:
                if (params.to == 1) {
                    setAsBottom(v);
                } else if (params.to == 2) {
                    params.alpha = 0.4F + 0.6F * mOffsetPercent;
                    params.scale = 0.8F + 0.2F * mOffsetPercent;
                }
                break;
            case 1:
                if (params.to == 0) {
                    setAsBottom(v);
                } else if (params.to == 2) {
                    params.alpha = 0.4F - 0.6F * mOffsetPercent;
                    params.scale = 0.8F - 0.2F * mOffsetPercent;
                }
                break;
            case 2:
                params.alpha = 1 - 0.6F * Math.abs(mOffsetPercent);
                params.scale = 1 - 0.2F * Math.abs(mOffsetPercent);
                break;
        }
    }

    public void setAsBottom(View v) {
        exchangeOrder(indexOfChild(v), 0);
    }

    /**
     * 更新位置
     */
    public void updateChildrenFromAndTo() {
        if (Math.abs(mOffsetPercent) >= 1) {
            // 已经无法滚动
            isReordered = false;
            for (int i = 0; i < 3; i++) {
                ScaleAndAlphaParams params = (ScaleAndAlphaParams) getChildAt(i).getLayoutParams();
                params.from = params.to;
            }
            mOffsetX %= getWidth();
            mOffsetPercent %= 1F;
        } else {
            for (int i = 0; i < 3; i++) {
                ScaleAndAlphaParams params = (ScaleAndAlphaParams) getChildAt(i).getLayoutParams();
                switch (params.from) {
                    case 0:
                        params.to = mOffsetPercent < 0 ? 1 : 2;
                        break;
                    case 1:
                        params.to = mOffsetPercent < 0 ? 2 : 0;
                        break;
                    case 2:
                        params.to = mOffsetPercent < 0 ? 0 : 1;
                        break;
                }
            }
        }
        Log.v("percent", mOffsetPercent + "");
    }


    public void updateChildrenOrder() {
        //如果滑动距离超过了ViewGroup宽度的一半，
        //就把索引为1，2的子View交换顺序，并标记已经交换过
        if (Math.abs(mOffsetPercent) > .5F) {
            if (!isReordered) {
                exchangeOrder(1, 2);
                isReordered = true;
            }
        } else {
            //滑动距离没有超过宽度一半，即有可能是滑动超过一半然后滑动回来
            //如果isReordered=true，就表示本次滑动已经交换过顺序
            //所以要再次交换一下
            if (isReordered) {
                exchangeOrder(1, 2);
                isReordered = false;
            }
        }
    }

    public void exchangeOrder(int fromIndex, int toIndex) {
        // 一样的就不用换了
        if (fromIndex == toIndex) {
            return;
        }
        //先获取引用
        View from = getChildAt(fromIndex);
        View to = getChildAt(toIndex);

        //分离出来
        detachViewFromParent(from);
        detachViewFromParent(to);

        //重新放回去，但是index互换了
        Log.v("test", "发生变化" + getChildCount());
        attachViewToParent(from, toIndex > getChildCount() ? getChildCount() : toIndex, from.getLayoutParams());
        Log.v("test", "发生变化" + getChildCount());
        attachViewToParent(to, fromIndex > getChildCount() ? getChildCount() : fromIndex, to.getLayoutParams());

        //通知重绘，刷新视图
        invalidate();
    }

    public void init() {
        int count = 3;
        for (int i = 0; i < count; i++) {
            indexList.add(i);
        }
    }

    /**
     * 获取view的中心轴X
     *
     * @param v
     * @return
     */
    public float getBaseLine(View v) {
        float left = getMeasuredWidth() / 4;
        float center = getMeasuredWidth() / 2;
        float right = getMeasuredWidth() - getMeasuredWidth() / 4;
        float base = 0;
        int i = indexOfChild(v);
        ScaleAndAlphaParams params = (ScaleAndAlphaParams) v.getLayoutParams();
        Log.v("from", params.from + "");
        switch (params.from) {
            case 0:
                if (params.to == 1) {
                    return left + (right - left) * -mOffsetPercent;
                } else if (params.to == 2) {
                    return left + (center - left) * mOffsetPercent;
                } else {
                    return left;
                }
            case 1:
                if (params.to == 0) {
                    return right + (right - left) * -mOffsetPercent;
                } else if (params.to == 2) {
                    return right + (right - center) * mOffsetPercent;
                } else {
                    return right;
                }
            case 2:
                if (params.to == 1) {
                    return center + (right - center) * mOffsetPercent;
                } else if (params.to == 0) {
                    return center + (center - left) * mOffsetPercent;
                } else {
                    return center;
                }
            default:
                return base;
        }

    }


    /**
     * @param v 对应的view
     * @param i 下标索引
     */
    public void layoutChild(View v, int i) {
        ScaleAndAlphaParams params = (ScaleAndAlphaParams) v.getLayoutParams();
        v.setScaleX(params.scale);
        v.setScaleY(params.scale);
        v.setAlpha(params.alpha);
        int measureWidth = v.getMeasuredWidth();
        int measureheight = v.getMeasuredHeight();
        float baseLineX = getBaseLine(v);
        int baseLineY = getMeasuredHeight() / 2;
        float left = baseLineX - measureWidth / 2;
        int top = baseLineY - measureheight / 2;
        float right = baseLineX + measureWidth / 2;
        int bottom = baseLineY + measureheight / 2;
        Log.v("layout", "left:" + left + "top:" + top + "right:" + right + "bottom:" + bottom);
        v.layout((int) left, top, (int) right, bottom);
    }

    /**
     * @param view   目标view
     * @param points 坐标点(x, y)
     * @return 坐标点是否在view范围内
     */
    private boolean pointInView(View view, float[] points) {
        // 像ViewGroup那样，先对齐一下Left和Top
        points[0] -= view.getLeft();
        points[1] -= view.getTop();
        // 获取View所对应的矩阵
        Matrix matrix = view.getMatrix();
        // 如果矩阵有应用过变换
        if (!matrix.isIdentity()) {
            // 反转矩阵
            matrix.invert(matrix);
            // 映射坐标点
            matrix.mapPoints(points);
        }
        //判断坐标点是否在view范围内
        return points[0] >= 0 && points[1] >= 0 && points[0] < view.getWidth() && points[1] < view.getHeight();
    }


    @Override
    protected void detachViewFromParent(int index) {
        // 将要删除这个View
        super.detachViewFromParent(index);
    }


    @Override
    public void attachViewToParent(View child, int index, ViewGroup.LayoutParams params) {
        super.attachViewToParent(child, index, params);
    }

    @Override
    public void addView(View child, int index, LayoutParams params) {
        ScaleAndAlphaParams scaleAndAlphaParams = new ScaleAndAlphaParams(params);
        scaleAndAlphaParams.from = index;
        if (index < 2) {
            scaleAndAlphaParams.alpha = 0.4F;
            scaleAndAlphaParams.scale = 0.8F;
        } else {
            scaleAndAlphaParams.alpha = 1F;
            scaleAndAlphaParams.scale = 1F;
        }
        super.addView(child, index, scaleAndAlphaParams);
    }


    class ScaleAndAlphaParams extends MarginLayoutParams {
        public float scale;
        public float alpha;
        public int from;
        public int to;


        public ScaleAndAlphaParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public ScaleAndAlphaParams(int width, int height) {
            super(width, height);
        }

        public ScaleAndAlphaParams(LayoutParams source) {
            super(source);
        }


    }
}
