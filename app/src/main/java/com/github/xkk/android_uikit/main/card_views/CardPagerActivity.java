package com.github.xkk.android_uikit.main.card_views;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.xkk.android_uikit.R;
import com.github.xkk.android_uikit.common.BaseActivity;

public class CardPagerActivity extends BaseActivity {
    protected static final String TAG = "MainActivity";
    private int[] mImgIds;
    private ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardpager);
        bindButter();
        mImgIds = new int[]{R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark,
                R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary};
        mViewPager = findViewById(R.id.id_viewPager);
        mViewPager.setOffscreenPageLimit(6);
        mViewPager.setPageTransformer(false
                , new CardPageTransAnimation());
        mViewPager.setAdapter(new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView((View) object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(CardPagerActivity.this);
                imageView.setImageDrawable(new ColorDrawable(getResources().getColor(mImgIds[position])));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(50, 50, 50, 50);
                imageView.setTag(position + "");
                container.addView(imageView);
                return imageView;
            }

            @Override
            public int getCount() {
                return mImgIds.length;
            }

            @Override
            public float getPageWidth(int position) {
                if (position == mImgIds.length - 1) {
                    return (float) 0.8;
                } else {
                    return (float) 0.8;
                }
            }
        });
    }
}
