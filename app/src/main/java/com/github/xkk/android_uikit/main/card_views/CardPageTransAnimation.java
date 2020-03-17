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
            // 当前页面缩放左移动
            page.setTranslationX(-position * (page.getWidth())/4*5+30*position);
            page.setScaleX((float) (1 + position / 40));
            page.setScaleY((float) (1 + position / 40));
        } else if (position <= 0.8) {
            //
            page.bringToFront();
            // 这个7代表最后一个元素tag 有9个元素 就为8 以此类推
            // 最后一个滚动距离不为page.getWidth() 需要手动修正
            if(page.getTag().equals("7")){
                page.setTranslationX(-(4-5*position)/3 * (page.getWidth()/5+50));
            }
        } else {
            page.bringToFront();
        }
        //

    }
}
