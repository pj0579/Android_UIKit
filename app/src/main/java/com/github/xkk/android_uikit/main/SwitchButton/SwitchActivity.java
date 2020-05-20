package com.github.xkk.android_uikit.main.SwitchButton;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.xkk.android_uikit.R;
import com.github.xkk.android_uikit.common.BaseActivity;

public class SwitchActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switchbutton);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        bindButter();
    }
}
