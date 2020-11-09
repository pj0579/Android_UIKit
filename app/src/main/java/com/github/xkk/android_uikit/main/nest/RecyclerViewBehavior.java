package com.github.xkk.android_uikit.main.nest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.github.xkk.android_uikit.R;

public class RecyclerViewBehavior extends CoordinatorLayout.Behavior {
    public RecyclerViewBehavior() {
    }

    public RecyclerViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency instanceof LinearLayout;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        //计算列表y坐标，最小为0
        float y = dependency.getHeight() + dependency.getTranslationY() + parent.findViewById(R.id.header).getHeight();
        if (y < 0) {
            y = 0;
        }
        child.setY(y);
        // 防止列表显示不全
        child.setPadding(0, 0, 0, parent.findViewById(R.id.header).getHeight() + parent.findViewById(R.id.footer).getHeight());
        return true;
    }
}
