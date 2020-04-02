package com.github.xkk.android_uikit.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.github.xkk.android_uikit.R;
import com.github.xkk.android_uikit.common.BaseRecyclerViewAdapter;
import com.github.xkk.android_uikit.main.card_views.CardPagerActivity;
import com.github.xkk.android_uikit.main.card_views.TinderActivity;
import com.github.xkk.android_uikit.main.measure_spec.MeasureSpecActivity;
import com.github.xkk.android_uikit.main.wangyiyun.WangyiyunActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.list)
    RecyclerView recyclerView;

    private Unbinder unbinder;


    private MainRecyclerViewAdapter mainRecyclerViewAdapter;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        initData();
    }

    public void initData() {
        List<String> list = new ArrayList<>();
        list.add("子View params对自己mode的影响");
        list.add("优酷首页高清板块切换效果");
        list.add("网易云切换(照着自定义-移花接木撸)");
        mainRecyclerViewAdapter = new MainRecyclerViewAdapter(list, this, R.layout.list_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mainRecyclerViewAdapter);
        mainRecyclerViewAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 0:
                        Intent inent = new Intent(MainActivity.this, MeasureSpecActivity.class);
                        startActivity(inent);
                        break;
                    case 1:
                        Intent card = new Intent(MainActivity.this, TinderActivity.class);
                        startActivity(card);
                        break;
                    case 2:
                        Intent wangyiyun = new Intent(MainActivity.this, WangyiyunActivity.class);
                        startActivity(wangyiyun);
                        break;
                }
            }
        });
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
