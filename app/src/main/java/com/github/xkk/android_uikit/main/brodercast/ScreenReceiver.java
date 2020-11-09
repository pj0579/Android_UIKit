package com.github.xkk.android_uikit.main.brodercast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScreenReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {    //屏幕关闭
            Log.i("song","锁屏");
            Intent intent1 =new Intent(context,HooliganActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {   //屏幕打开
            Log.i("song","开屏");
        }
    }
}
