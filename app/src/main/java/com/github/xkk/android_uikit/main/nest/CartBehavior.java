package com.github.xkk.android_uikit.main.nest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class CartBehavior extends CoordinatorLayout.Behavior {
    private boolean isinit = false;

    public CartBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return true;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        if(!isinit){
            isinit = true;
            child.setTranslationY(parent.getHeight());
        }
        return super.onDependentViewChanged(parent, child, dependency);
    }
}
