package com.github.xkk.android_uikit.main.card_views;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

public class CommonAniamtion implements ViewPager.PageTransformer {
    @Override
    public void transformPage(@NonNull View view, float position) {
        Log.v("position111",position+"");
    }
}
