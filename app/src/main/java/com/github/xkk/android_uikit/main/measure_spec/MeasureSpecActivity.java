package com.github.xkk.android_uikit.main.measure_spec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.xkk.android_uikit.R;
import com.github.xkk.android_uikit.common.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class MeasureSpecActivity extends BaseActivity  {
    @BindView(R.id.measurespec)
    TextView measurespec;
    @BindView(R.id.measure)
    MeasureView measureView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure);
        bindButter();

    }
    @OnClick(R.id.match)
    public void match() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) measureView.getLayoutParams();
        params.width = MATCH_PARENT;
        measureView.setLayoutParams(params);
        changeMode();
    }

    @OnClick(R.id.wrap)
    public void wrap() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) measureView.getLayoutParams();
        params.width = WRAP_CONTENT;
        measureView.setLayoutParams(params);
        changeMode();
    }

    @OnClick(R.id.execly)
    public void execly() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) measureView.getLayoutParams();
        params.width = 700;
        // 异步改变mode 多点几次获取正确mode
        measureView.setLayoutParams(params);
        changeMode();
    }

    public void changeMode(){
       measurespec.setText("子View measureSpecMode："+measureView.getMode());
    }
}
