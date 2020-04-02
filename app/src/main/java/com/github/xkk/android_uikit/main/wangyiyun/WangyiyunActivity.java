package com.github.xkk.android_uikit.main.wangyiyun;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.xkk.android_uikit.R;
import com.github.xkk.android_uikit.common.BaseActivity;

import butterknife.BindView;

public class WangyiyunActivity extends BaseActivity {
    @BindView(R.id.wangyi)
    WangyiyunView wangyiyunView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wangyiyun);
        bindButter();
        addView();
    }


    public void addView() {
        for (int i = 0; i < 3; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.wangyi_item, wangyiyunView, false);
            view.getLayoutParams();
            wangyiyunView.addView(view, i, view.getLayoutParams());
        }
    }

}
