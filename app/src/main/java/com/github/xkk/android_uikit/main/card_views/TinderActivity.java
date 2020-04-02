package com.github.xkk.android_uikit.main.card_views;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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
    private Handler handler;
    private Runnable runnable;
    private int currentPosition = 1;
    private ViewPager.OnPageChangeListener pageChangeListener;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinder);
        bindButter();
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("ViewPager切换效果");
        setSupportActionBar(toolbar);
        mFragments = getFragments();
        setLoopAuto();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        handler.removeCallbacks(runnable);
        viewPager.removeOnPageChangeListener(pageChangeListener);
        switch (item.getItemId()) {
            case R.id.action_youku:
                changeAnimation(false, new TinderAnimation());
                break;
            case R.id.action_common:
                changeAnimation(true, new CommonAniamtion());
                break;
            case R.id.action_loop_auto:
                setLoopAuto();
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    private void initPager() {
        viewPager.setOffscreenPageLimit(20);
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

    public void changeAnimation(boolean reverse, ViewPager.PageTransformer animation) {
        viewPager.setPageTransformer(reverse, animation);
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

    public void setLoopAuto() {
        final List<Fragment> mFragments = new ArrayList<>();
        mFragments.add(GuideFragment.newInstance(14));
        for (int i = 0; i < 15; i++) {
            Fragment fragment = GuideFragment.newInstance(i);
            mFragments.add(fragment);

        }
        mFragments.add(GuideFragment.newInstance(0));
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
        pageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                currentPosition = i;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //验证当前的滑动是否结束
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    Log.v("停止滚动",currentPosition+"");
                    if (currentPosition == 0) {
                        //切换，不要动画效果
                        viewPager.setCurrentItem(mAdapter.getCount() - 2, false);
                    } else if (currentPosition == mAdapter.getCount() - 1) {
                        //切换，不要动画效果
                        viewPager.setCurrentItem(1, false);
                    }
                }
            }
        };
        viewPager.addOnPageChangeListener(pageChangeListener);
        // 从第二个item开始开始
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(1,false);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                currentPosition = currentPosition +1;
                if (currentPosition == mAdapter.getCount() - 1) {
                    // 回到初始值
                    currentPosition = 1;
                    viewPager.setCurrentItem(1, false);
                    handler.postDelayed(this, 3000);
                }else {
                    viewPager.setCurrentItem(currentPosition
                            ,true);
                    handler.postDelayed(this, 3000);
                }
            }
        };
        handler.postDelayed(runnable, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}
