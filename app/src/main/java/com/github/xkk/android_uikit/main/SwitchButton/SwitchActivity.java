package com.github.xkk.android_uikit.main.SwitchButton;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import com.github.xkk.android_uikit.R;
import com.github.xkk.android_uikit.common.BaseActivity;

import butterknife.BindView;

public class SwitchActivity extends BaseActivity {
    @BindView(R.id.switchButton)
    public SwitchButton switchButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switchbutton);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        bindButter();
        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(boolean isChecked) {
                Log.v("isChecked",isChecked+"");
            }
        });
    }



}
