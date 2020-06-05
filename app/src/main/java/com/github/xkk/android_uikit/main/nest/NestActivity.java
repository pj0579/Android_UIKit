package com.github.xkk.android_uikit.main.nest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.xkk.android_uikit.R;
import com.github.xkk.android_uikit.common.BaseActivity;
import com.github.xkk.android_uikit.common.BaseRecyclerViewAdapter;
import com.github.xkk.android_uikit.main.MainActivity;
import com.github.xkk.android_uikit.main.MainRecyclerViewAdapter;
import com.github.xkk.android_uikit.main.SwitchButton.SwitchActivity;
import com.github.xkk.android_uikit.main.Text.TextChangeActivity;
import com.github.xkk.android_uikit.main.card_views.TinderActivity;
import com.github.xkk.android_uikit.main.measure_spec.MeasureSpecActivity;
import com.github.xkk.android_uikit.main.wangyiyun.WangyiyunActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class NestActivity extends BaseActivity {

    @BindView(R.id.md_contentRecyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_nest);
        bindButter();
        initData();
    }


    public void initData() {
        List<String> list = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler(Looper.getMainLooper());
            }
        }).start();
        list.add("子View params对自己mode的影响");
        list.add("优酷首页高清板块切换效果");
        list.add("网易云切换(照着自定义-移花接木撸)");
        list.add("文字渐变");
        list.add("SwitchButton");
        list.add("了解NestedScrolling");
        list.add("文字渐变");
        list.add("SwitchButton");
        list.add("了解NestedScrolling");
        list.add("文字渐变");
        list.add("SwitchButton");
        list.add("了解NestedScrolling");
        list.add("文字渐变");
        list.add("SwitchButton");
        list.add("了解NestedScrolling");
        MainRecyclerViewAdapter mainRecyclerViewAdapter = new MainRecyclerViewAdapter(list, this, R.layout.list_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mainRecyclerViewAdapter);
        mainRecyclerViewAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 0:
                        Intent inent = new Intent(NestActivity.this, MeasureSpecActivity.class);
                        startActivity(inent);
                        break;
                    case 1:
                        Intent card = new Intent(NestActivity.this, TinderActivity.class);
                        startActivity(card);
                        break;
                    case 2:
                        Intent wangyiyun = new Intent(NestActivity.this, WangyiyunActivity.class);
                        startActivity(wangyiyun);
                        break;
                    case 3:
                        Intent text = new Intent(NestActivity.this, TextChangeActivity.class);
                        startActivity(text);
                        break;
                    case 4:
                        Intent switchActivity = new Intent(NestActivity.this, SwitchActivity.class);
                        startActivity(switchActivity);
                        break;
                    case 5:
                        Intent NestActivity = new Intent(NestActivity.this, com.github.xkk.android_uikit.main.nest.NestActivity.class);
                        startActivity(NestActivity);
                        break;
                }
            }
        });
    }
    
}
