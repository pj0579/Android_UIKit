package com.github.xkk.android_uikit.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.github.xkk.android_uikit.R;
import com.github.xkk.android_uikit.common.BaseRecyclerViewAdapter;
import com.github.xkk.android_uikit.main.SwitchButton.SwitchActivity;
import com.github.xkk.android_uikit.main.Text.TextChangeActivity;
import com.github.xkk.android_uikit.main.brodercast.HooliganActivity;
import com.github.xkk.android_uikit.main.brodercast.ScreenReceiver;
import com.github.xkk.android_uikit.main.card_views.TinderActivity;
import com.github.xkk.android_uikit.main.hotFix.Test;
import com.github.xkk.android_uikit.main.kotlin.FirstKotlinActivity;
import com.github.xkk.android_uikit.main.line.LinePointViewActvity;
import com.github.xkk.android_uikit.main.measure_spec.MeasureSpecActivity;
import com.github.xkk.android_uikit.main.mutifinger.MutiFlingersActivity;
import com.github.xkk.android_uikit.main.nest.LuxuryPriceActivity;
import com.github.xkk.android_uikit.main.wangyiyun.WangyiyunActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    public RecyclerView recyclerView;

    private Unbinder unbinder;
    private BroadcastReceiver screenBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                HooliganActivity.startHooligan();
            } else if(intent.getAction().equals(Intent.ACTION_SCREEN_ON)){
                HooliganActivity.killHooligan();
            }
        }
    };



    private MainRecyclerViewAdapter mainRecyclerViewAdapter;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.list);
        initData();
    }

    public void initData() {
        List<String> list = new ArrayList<>();
        registerService();
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
        list.add("多指触控-响应第一个手指");
        list.add("linePoint");
        list.add("kotlin");
        mainRecyclerViewAdapter = new MainRecyclerViewAdapter(list, this, R.layout.list_item);
        Log.v("ClassLoader1111", this.getClassLoader().toString() + "parent:" + this.getClassLoader().getParent());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mainRecyclerViewAdapter);
        mainRecyclerViewAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 0:
                        new Test().test();
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
                    case 3:
                        Intent text = new Intent(MainActivity.this, TextChangeActivity.class);
                        startActivity(text);
                        break;
                    case 4:
                        Intent switchActivity = new Intent(MainActivity.this, SwitchActivity.class);
                        startActivity(switchActivity);
                        break;
                    case 5:
                        Intent NestActivity = new Intent(MainActivity.this, LuxuryPriceActivity.class);
                        startActivity(NestActivity);
                    case 6:
                        Intent MutiActivity = new Intent(MainActivity.this, MutiFlingersActivity.class);
                        startActivity(MutiActivity);
                        break;
                    case 7:
                        Intent lineActivity = new Intent(MainActivity.this, LinePointViewActvity.class);
                        startActivity(lineActivity);
                        break;
                    case 8:
                        Intent kotlin = new Intent(MainActivity.this, FirstKotlinActivity.class);
                        startActivity(kotlin);
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
        getApplicationContext().unregisterReceiver(screenBroadcastReceiver);
    }

    private void registerService() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        getApplicationContext().registerReceiver(screenBroadcastReceiver, filter);

    }
}
