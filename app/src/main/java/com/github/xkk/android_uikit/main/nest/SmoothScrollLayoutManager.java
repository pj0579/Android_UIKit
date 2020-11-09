package com.github.xkk.android_uikit.main.nest;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import static android.support.v7.widget.LinearSmoothScroller.SNAP_TO_START;

public class SmoothScrollLayoutManager extends LinearSmoothScroller {
    public SmoothScrollLayoutManager(Context context) {
        super(context);
    }

    @Override
    protected int getHorizontalSnapPreference() {
        return SNAP_TO_START;
    }

    @Override
    protected int getVerticalSnapPreference() {
        return SNAP_TO_START;  // 将子view与父view顶部对齐
    }


}
