package com.shx.smartcarmanager.base;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by 邵鸿轩 on 2017/6/14.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
    }
}
