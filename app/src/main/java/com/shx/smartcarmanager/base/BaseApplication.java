package com.shx.smartcarmanager.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Display;

import com.baidu.mapapi.SDKInitializer;
import com.shx.smartcarmanager.commons.LayoutValue;
import com.shx.smartcarmanager.commons.LogGloble;

/**
 * Created by 邵鸿轩 on 2017/6/14.
 */

public class BaseApplication extends Application {
    public static boolean isExit = false;
    private static BaseApplication mZBaseApplication;
    private Display display;
    public static int mNetWorkState;
    /**
     * 双击退出的消息处理
     */
    public Handler mHandlerExit = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };
    @Override
    public void onCreate() {
        super.onCreate();
        mZBaseApplication = this;
        // 获取屏幕宽高
        DisplayMetrics dm = getResources().getDisplayMetrics();
        if (dm.widthPixels <= dm.heightPixels) {
            LayoutValue.SCREEN_WIDTH = dm.widthPixels;
            LayoutValue.SCREEN_HEIGHT = dm.heightPixels;
        } else {
            LayoutValue.SCREEN_WIDTH = dm.heightPixels;
            LayoutValue.SCREEN_HEIGHT = dm.widthPixels;
        }
        LogGloble.d("info", "LayoutValue.SCREEN_WIDTH-- "
                + LayoutValue.SCREEN_WIDTH);
        LogGloble.d("info", "LayoutValue.SCREEN_HEIGHT-- "
                + LayoutValue.SCREEN_HEIGHT);
        SDKInitializer.initialize(getApplicationContext());
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }
    /**
     * 描述:获取上下文
     *
     * @return
     */
    public static BaseApplication getContext() {
        return mZBaseApplication;
    }
}
