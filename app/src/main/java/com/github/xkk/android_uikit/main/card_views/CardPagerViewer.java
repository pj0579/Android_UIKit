package com.github.xkk.android_uikit.main.card_views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class CardPagerViewer extends ViewPager {

    private float mTrans;
    private float mScale;
    /**
     * 最大的缩小比例
     */
    private static final float SCALE_MAX = 0.5f;
    private static final String TAG = "CardPagerViewer";
    /**
     * 保存position与对于的View
     */
    private HashMap<Integer, View> mChildrenViews = new LinkedHashMap<Integer, View>();
    /**
     * 滑动时左边的元素
     */
    private View mLeft;
    /**
     * 滑动时右边的元素
     */
    private View mRight;

    public CardPagerViewer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {

        Log.e(TAG, "position=" + position + ", positionOffset = " + positionOffset + " ,positionOffsetPixels =  " + positionOffsetPixels + " , currentPos = " + getCurrentItem());

        //滑动特别小的距离时，我们认为没有动，可有可无的判断
        float effectOffset = isSmall(positionOffset) ? 0 : positionOffset;

        // 添加切换动画效果
        animateStack(position, effectOffset, positionOffsetPixels);
        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    public void setObjectForPosition(View view, int position) {
        mChildrenViews.put(position, view);
    }

    /**
     * 通过过位置获得对应的View
     *
     * @param position
     * @return
     */
    public View findViewFromObject(int position) {
        return mChildrenViews.get(position);
    }

    private boolean isSmall(float positionOffset) {
        return Math.abs(positionOffset) < 0.0001;
    }

    protected void animateStack(int position, float effectOffset,
                                int positionOffsetPixels) {
    }

    public void setPageTransformer(PageTransformer pageTransformer) {
    }


}
