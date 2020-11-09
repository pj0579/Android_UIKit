package com.github.xkk.android_uikit.common;

import android.app.Application;
import android.content.Context;

import com.github.xkk.android_uikit.main.hotFix.HotFixUtil;

public class BaseApplication extends Application {
    private static Context context;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        BaseApplication.context = context;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        BaseApplication.setContext(this);
//        try {
//            new HotFixUtil().startFix();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
        super.onCreate();
    }
}
