package com.github.xkk.android_uikit.main.nest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class MyBehavior extends CoordinatorLayout.Behavior<LinearLayout> {
    // 界面整体向上滑动，达到列表可滑动的临界点
    private boolean upReach;
    // 列表向上滑动后，再向下滑动，达到界面整体可滑动的临界点
    private boolean downReach;
    // 列表上一个全部可见的item位置
    private int lastPosition = -1;

    public MyBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout parent, @NonNull LinearLayout child, @NonNull MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downReach = false;
                upReach = false;
                break;
        }
        return super.onInterceptTouchEvent(parent, child, ev);
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull LinearLayout child, @NonNull View dependency) {
        return false;
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull LinearLayout child, @NonNull View dependency) {
        return super.layoutDependsOn(parent, child, dependency);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull LinearLayout child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return true;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull LinearLayout child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        if (target instanceof RecyclerView && target.getTag() == null) {

            RecyclerView list = (RecyclerView) target;

            // 列表第一个全部可见Item的位置
            int pos = ((LinearLayoutManager) list.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
            float finalY = (child.getTranslationY() - dy);

            // 思路是滑动时候发现问题并修改
            if (finalY < -child.getHeight()) {
                // 修正回归
                if (child.getTranslationY() > -child.getHeight()) {
                    consumed[1] = dy;
                }
                finalY = -child.getHeight();
            } else if (finalY > 0) {
                //  只滑动列表 不滑动头部 修正回归
                finalY = 0;
            } else if (pos != 0 && child.getTranslationY() < 0 && child.getTranslationY() >= -child.getHeight()) {
                // 只滑动列表 不滑动头部 中部
                finalY = -child.getHeight();
            } else {
                // 只滑动头部
                consumed[1] = dy;
            }

            if (pos != 0) {
                // 修正回归
                child.setTranslationY(-child.getHeight());
            } else {
                child.setTranslationY(finalY);
            }
        }
    }

}
