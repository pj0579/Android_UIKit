package com.github.xkk.android_uikit.main.card_views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.github.xkk.android_uikit.R;
import com.github.xkk.android_uikit.common.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TinderActivity extends BaseActivity {

    @BindView(R.id.id_viewPager)
    ViewPager viewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinder);
        bindButter();
        mFragments = getFragments();
        initPager();
    }

    private void initPager() {
        viewPager.setOffscreenPageLimit(10);
        viewPager.setPageTransformer(false, new TinderAnimation());
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mFragments.get(i);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };
        viewPager.setAdapter(mAdapter);
    }

    private List<Fragment> getFragments() {
        List<Fragment> mFragments = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            Fragment fragment = GuideFragment.newInstance(i);
            mFragments.add(fragment);

        }
        return mFragments;
    }

}
