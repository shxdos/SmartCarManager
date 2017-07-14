package com.shx.smartcarmanager.base;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.Trace;
import com.baidu.trace.model.OnTraceListener;
import com.baidu.trace.model.PushMessage;
import com.shx.smartcarmanager.Utils.DeviceUtil;

/**
 * Created by 邵鸿轩 on 2017/6/15.
 */

public class TranceService extends Service implements OnTraceListener {

    // 鹰眼服务ID，开发者创建的鹰眼服务对应的服务ID
    private static final long serviceId = 143514;
    // 是否需要对象存储服务，默认为：false，关闭对象存储服务。注：鹰眼 Android SDK v3.0以上版本支持随轨迹上传图像等对象数据，若需使用此功能，该参数需设为 true，且需导入bos-android-sdk-1.0.2.jar。
    boolean isNeedObjectStorage = false;
    private Trace mTrace;
    private LBSTraceClient mTraceClient;
    // 定位周期(单位:秒)
    private int gatherInterval = 5;
    // 打包回传周期(单位:秒)
    private int packInterval = 10;
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化轨迹服务
        String name=DeviceUtil.getDeviceId(this);
        Log.d("name",name+"000000");
        mTrace = new Trace(serviceId, name, isNeedObjectStorage);
        // 初始化轨迹服务客户端
        mTraceClient = new LBSTraceClient(getApplicationContext());
        // 设置定位和打包周期
        mTraceClient.setInterval(gatherInterval, packInterval);
        // 开启服务
        mTraceClient.startTrace(mTrace, this);
        // 开启采集
        mTraceClient.startGather(this);
    }
    @Override
    public void onDestroy() {
        mTraceClient.stopTrace(mTrace,this);
        mTraceClient.startGather(this);
        startService(new Intent(this,TranceService.class));
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onBindServiceCallback(int i, String s) {
        Log.d("TraceCallback","onBindServiceCallback"+i+";"+s);
    }

    @Override
    public void onStartTraceCallback(int i, String s) {
        Log.d("TraceCallback","onStartTraceCallback"+i+";"+s);
    }

    @Override
    public void onStopTraceCallback(int i, String s) {
        Log.d("TraceCallback","onStopTraceCallback"+i+";"+s);
    }

    @Override
    public void onStartGatherCallback(int i, String s) {
        Log.d("TraceCallback","onStartGatherCallback"+i+";"+s);
    }

    @Override
    public void onStopGatherCallback(int i, String s) {
        Log.d("TraceCallback","onStopGatherCallback"+i+";"+s);
    }

    @Override
    public void onPushCallback(byte b, PushMessage pushMessage) {
        Log.d("TraceCallback","onPushCallback"+pushMessage.toString());
    }
}
