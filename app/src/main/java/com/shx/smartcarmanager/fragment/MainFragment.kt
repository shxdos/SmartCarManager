package com.shx.smartcarmanager.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baidu.location.BDLocation
import com.baidu.mapapi.map.*
import com.baidu.trace.model.OnTraceListener
import com.baidu.trace.model.PushMessage
import com.shx.smartcarmanager.R
import com.shx.smartcarmanager.base.LocationService
import com.shx.smartcarmanager.base.TranceService

/**
 * Created by 邵鸿轩 on 2017/7/13.
 */
class MainFragment : Fragment() , OnTraceListener {


    private var mMapView: MapView? = null
    private var mBaiduMap: BaiduMap? = null
    private var receiver: MyReceiver? = null
    private var mCurrentMarker: BitmapDescriptor? = null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_main,null)
    }
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mMapView= view?.findViewById(R.id.bmapView) as MapView
        mBaiduMap = mMapView!!.map as BaiduMap
        mBaiduMap!!.mapType=BaiduMap.MAP_TYPE_NORMAL
        // 开启定位图层
        mBaiduMap!!.setMyLocationEnabled(true)
        mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_tracing)
    }
    override fun onStart() {
        super.onStart()
        context.startService(Intent(context, LocationService::class.java))
        //注册广播接收器
        receiver = MyReceiver()
        val filter = IntentFilter()
        filter.addAction(LocationService::class.java.simpleName)
        context.registerReceiver(receiver, filter)
        context.startService(Intent(context, TranceService::class.java))
    }
    override fun onResume() {
        super.onResume()
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView!!.onResume()
    }
    override fun onDestroy() {
        super.onDestroy()
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView!!.onDestroy()
    }
    override fun onStop() {
        super.onStop()
        context.unregisterReceiver(receiver)
    }
    override fun onPause() {
        super.onPause()
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView!!.onPause()
    }
    override fun onStartGatherCallback(p0: Int, p1: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindServiceCallback(p0: Int, p1: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStopGatherCallback(p0: Int, p1: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPushCallback(p0: Byte, p1: PushMessage?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStartTraceCallback(p0: Int, p1: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStopTraceCallback(p0: Int, p1: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
     inner class MyReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (LocationService::class.java.simpleName == intent.action) {
                val bundle = intent.extras
                val bdLocation = bundle.getParcelable<BDLocation>("bdLocation")
                //                Log.d("onReceiveLocation", bdLocation.getAddrStr()+"");
                //                Log.d("onReceiveLocation", bdLocation.getCity()+"");
                //                Log.d("onReceiveLocation", bdLocation.getStreet()+"");
                // 构造定位数据
                val locData = MyLocationData.Builder()
                        .accuracy(bdLocation!!.radius)
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100f).latitude(bdLocation.latitude)
                        .longitude(bdLocation.longitude).build()
                // 设置定位数据
                mBaiduMap?.setMyLocationData(locData)
                val config = MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, mCurrentMarker)
                mBaiduMap?.setMyLocationConfiguration(config)
            }
        }
    }
}