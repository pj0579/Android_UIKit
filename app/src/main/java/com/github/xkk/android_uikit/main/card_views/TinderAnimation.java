package com.github.xkk.android_uikit.main.card_views;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

public class TinderAnimation implements ViewPager.PageTransformer {
    @Override
    public void transformPage(@NonNull View page, float position) {
        if (position <= 0.0f) {
            //被滑动的那页
            float transX = -(page.getWidth() * position) + ((60) * position);
            page.setTranslationX(transX);
            //缩放比例
            float scale = (page.getWidth() + 40 * position) / (float) (page.getWidth());
            page.setScaleX(scale);
            page.setScaleY(scale);
            // 透明度改变
            if(position==0){
                page.setAlpha(1);
            }else if(position<-2){
                page.setAlpha(0);
            }else{
                page.setAlpha(1/-position);
            }
        } else {
            // 下一页
            float transX = -((300) * position);
            page.setTranslationX(transX);
        }
    }
}
