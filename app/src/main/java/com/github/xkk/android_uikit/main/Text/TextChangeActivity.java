package com.github.xkk.android_uikit.main.Text;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.xkk.android_uikit.R;
import com.github.xkk.android_uikit.common.BaseActivity;

public class TextChangeActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changetext);
        bindButter();
    }
}
