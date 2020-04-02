package com.github.xkk.android_uikit.main.card_views;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

public class CardPageTransAnimation implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.75f;

    public void transformPage(View page, float position) {
        Log.v(page.getTag() + "", "position:" + position);
        if (position <= 0) {

        } else if (position <= 0.8) {

        } else {

        }

    }
}
