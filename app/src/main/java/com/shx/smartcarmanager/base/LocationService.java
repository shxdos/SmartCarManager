package com.shx.smartcarmanager.base;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

/**
 * Created by 邵鸿轩 on 2017/6/15.
 */
public class LocationService extends Service implements BDLocationListener{
	private LocationClient client = null;
	private LocationClientOption mOption;
	private Object  objLock = new Object();
	private Intent intent;
	@Override
	public void onCreate() {
		super.onCreate();
		intent=new Intent();
		client = new LocationClient(getApplicationContext());
		setLocationOption(getDefaultLocationClientOption());
		start();
	}



	/***
	 * 
	 * @param option
	 * @return isSuccessSetOption
	 */
	public boolean setLocationOption(LocationClientOption option){
		boolean isSuccess = false;
		if(option != null){
			if(client.isStarted())
				client.stop();
			client.setLocOption(option);
		}
		return isSuccess;
	}
	
	/***
	 * 
	 * @return DefaultLocationClientOption
	 */
	public LocationClientOption getDefaultLocationClientOption(){
		if(mOption == null){
			mOption = new LocationClientOption();
			mOption.setLocationMode(LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
			mOption.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
			mOption.setScanSpan(3000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		    mOption.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
		    mOption.setIsNeedLocationDescribe(true);//可选，设置是否需要地址描述
		    mOption.setNeedDeviceDirect(false);//可选，设置是否需要设备方向结果
		    mOption.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		    mOption.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死   
		    mOption.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		    mOption.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		    mOption.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
		    mOption.setIsNeedAltitude(false);//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
		 
		}
		return mOption;
	}
	
	private void start(){
		synchronized (objLock) {
			if(client != null && !client.isStarted()){
				client.registerLocationListener(this);
				client.start();
			}
		}
	}
	private void stop(){
		synchronized (objLock) {
			if(client != null && client.isStarted()){
				client.stop();
			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		stop();
		startService(new Intent(this,LocationService.class));
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onReceiveLocation(BDLocation bdLocation) {
		Log.d("onReceiveLocation", bdLocation.getAddrStr()+"");
		Log.d("onReceiveLocation", bdLocation.getCity()+"");
		Log.d("onReceiveLocation", bdLocation.getStreet()+"");
		intent.putExtra("bdLocation", bdLocation);
		intent.setAction(LocationService.class.getSimpleName());
		sendBroadcast(intent);
	}

	@Override
	public void onConnectHotSpotMessage(String s, int i) {
	}
}
