package com.github.xkk.android_uikit.main.nest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParent2;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import android.widget.Scroller;

import com.github.xkk.android_uikit.R;

public class StickLayout extends LinearLayout implements NestedScrollingParent {

    private int mTopViewHeight = 0;
    private OverScroller mScroller;
    private View topView;
    private RecyclerView recyclerView;

    public StickLayout(Context context) {
        super(context);
    }

    public StickLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new OverScroller(context);
    }

    public StickLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        recyclerView = findViewById(R.id.md_contentRecyclerView);
        topView = findViewById(R.id.topView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        params.height = getMeasuredHeight();
        mTopViewHeight = topView.getMeasuredHeight();
        setMeasuredDimension(getMeasuredWidth(), topView.getMeasuredHeight() + recyclerView.getMeasuredHeight());
    }

    /**
     * 是否监听子View的滑动参数
     *
     * @param child
     * @param target
     * @param nestedScrollAxes
     * @return
     */
    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        mTopViewHeight = topView.getMeasuredHeight();
    }


    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        Log.v("dy",dy+"");
        boolean hiddenTop = dy > 0 && getScrollY() < mTopViewHeight;
        boolean showTop = dy < 0 && getScrollY() > 0 && !ViewCompat.canScrollVertically(target, -1);
        if (hiddenTop || showTop) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        Log.v("出发fling","test"+getY()+getScrollY());
        if (getScrollY() >= mTopViewHeight) return false;
        fling((int) velocityY);
        return true;
    }

    public void fling(int velocityY) {
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, mTopViewHeight);
        invalidate();
    }

    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > mTopViewHeight) {
            y = mTopViewHeight;
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
    }
}
