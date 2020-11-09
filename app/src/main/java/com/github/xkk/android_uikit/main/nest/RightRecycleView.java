package com.github.xkk.android_uikit.main.nest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class RightRecycleView extends RecyclerView {
    private boolean isNeedScroll;

    public RightRecycleView(@NonNull Context context) {
        super(context);
    }

    public RightRecycleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RightRecycleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean isNeedScroll() {
        return isNeedScroll;
    }

    public void setNeedScroll(boolean needScroll) {
        isNeedScroll = needScroll;
    }
}
