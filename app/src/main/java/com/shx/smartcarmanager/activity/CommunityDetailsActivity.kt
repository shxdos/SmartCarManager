package com.shx.smartcarmanager.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.TextView
import com.baidu.location.BDLocation
import com.baidu.mapapi.map.*
import com.baidu.mapapi.map.MapStatus
import com.baidu.mapapi.model.LatLng
import com.baidu.trace.model.OnTraceListener
import com.baidu.trace.model.PushMessage
import com.shx.smartcarmanager.R
import com.shx.smartcarmanager.base.BaseActivity
import com.shx.smartcarmanager.base.LocationService
import com.shx.smartcarmanager.base.TranceService
import com.shx.smartcarmanager.entity.Community
import com.shx.smartcarmanager.entity.DangerSource
import com.shx.smartcarmanager.libs.http.MyJSON
import com.shx.smartcarmanager.libs.http.RequestCenter
import com.shx.smartcarmanager.libs.http.ZCResponse




class CommunityDetailsActivity : BaseActivity(), OnTraceListener {
    private var mMapView: MapView? = null
    private var mBaiduMap: BaiduMap? = null
    private var receiver: MyReceiver? = null
    private var mCurrentMarker: BitmapDescriptor? = null
    private var mCommunity: Community? = null
    private var mRegion: TextView? = null
    private var mName: TextView? = null
    private var mArea: TextView? = null
    private var mType: TextView? = null
    private var mAddress: TextView? = null
    private var mLatLngList: List<com.shx.smartcarmanager.entity.LatLng>? = null
    private var mDangerSourceList: List<DangerSource>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_details)
        mCommunity = intent.getSerializableExtra("community") as Community?
        mMapView = findViewById(R.id.bmapView) as MapView

        mRegion = findViewById(R.id.tv_region) as TextView
        mName = findViewById(R.id.tv_name) as TextView
        mArea = findViewById(R.id.tv_area) as TextView
        mType = findViewById(R.id.tv_type) as TextView
        mAddress = findViewById(R.id.tv_address) as TextView
        displayMap()
        if (mCommunity == null) {
            return
        }
        mRegion!!.setText(mCommunity!!.regionCode)
        mName!!.setText(mCommunity!!.name)
        mArea!!.setText(mCommunity!!.area.toString())
        mType!!.setText(mCommunity!!.communityType)
        mAddress!!.setText(mCommunity!!.address)
        RequestCenter.getEnclosure(mCommunity!!.id!!, this)
        RequestCenter.getDangersourceList(mCommunity!!.id!!, 1, 10, this)
    }

    private fun displayMap() {
        mBaiduMap = mMapView!!.map as BaiduMap
        mBaiduMap!!.mapType = BaiduMap.MAP_TYPE_NORMAL
        // 开启定位图层
        mBaiduMap!!.setMyLocationEnabled(true)
        mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_tracing)
        startService(Intent(this, LocationService::class.java))
        //注册广播接收器
        receiver = MyReceiver()
        val filter = IntentFilter()
        filter.addAction(LocationService::class.java.simpleName)
        registerReceiver(receiver, filter)
        startService(Intent(this, TranceService::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    override fun onStartGatherCallback(p0: Int, p1: String?) {
    }

    override fun onBindServiceCallback(p0: Int, p1: String?) {
    }

    override fun onStopGatherCallback(p0: Int, p1: String?) {
    }

    override fun onPushCallback(p0: Byte, p1: PushMessage?) {
    }

    override fun onStartTraceCallback(p0: Int, p1: String?) {
    }

    override fun onStopTraceCallback(p0: Int, p1: String?) {
    }

    override fun doSuccess(respose: ZCResponse?, requestUrl: String?): Boolean {
        if (requestUrl.equals(RequestCenter.enclosure_url)) {
            mLatLngList = MyJSON.parseArray(respose?.rows, com.shx.smartcarmanager.entity.LatLng::class.java)
            if (mLatLngList == null||mLatLngList!!.size<=0) {
                return super.doSuccess(respose, requestUrl)
            }
            val pts = ArrayList<com.baidu.mapapi.model.LatLng>()
            for (item in mLatLngList!!) {
                val pt1 = com.baidu.mapapi.model.LatLng(item!!.lat!!, item!!.lng!!)
                pts.add(pt1)
            }
            val cenpt = LatLng(mLatLngList!![0].lat!!, mLatLngList!![0].lng!!)
            //定义地图状态
            val mMapStatus = MapStatus.Builder()
                    .target(cenpt)
                    .zoom(30f)
                    .build()
            //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
            val mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus)
            //改变地图状态
            mBaiduMap?.setMapStatus(mMapStatusUpdate)
            //构建用户绘制多边形的Option对象
            val polygonOption = PolygonOptions()
                    .points(pts)
                    .stroke(Stroke(5, 0xAA00FF00.toInt()))
                    .fillColor(0xAAFFFF00.toInt())
            //在地图上添加多边形Option，用于显示
            mBaiduMap!!.addOverlay(polygonOption)

        }
        if (requestUrl.equals(RequestCenter.dangersource_url)) {
            mDangerSourceList = MyJSON.parseArray(respose?.rows, DangerSource::class.java)
            if (mDangerSourceList == null) {
                return super.doSuccess(respose, requestUrl)
            }
            for (item in mDangerSourceList!!) {
                //定义Maker坐标点
                val point = LatLng(item.cLatitude!!, item.cLongtide!!)
                //构建Marker图标
                val bitmap = BitmapDescriptorFactory
                        .fromResource(R.mipmap.icon_tracing)
                //构建MarkerOption，用于在地图上添加Marker
                val option = MarkerOptions()
                        .position(point)
                        .icon(bitmap)
                //在地图上添加Marker，并显示
                mBaiduMap!!.addOverlay(option)
            }

        }
        return super.doSuccess(respose, requestUrl)
    }

    inner class MyReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (LocationService::class.java.simpleName == intent.action) {
                val bundle = intent.extras
                val bdLocation = bundle.getParcelable<BDLocation>("bdLocation")
                // 构造定位数据
                var builder = MyLocationData.Builder()
                        .accuracy(bdLocation!!.radius)
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100f)
                //@TODO 修改为真实定位
                if (mLatLngList != null && mLatLngList!!.size > 0) {
                    builder.latitude(mLatLngList!![0].lat!!)
                            .longitude(mLatLngList!![0].lng!!)
                } else {
                    builder.latitude(bdLocation.latitude)
                            .longitude(bdLocation.longitude)
                }
                val locData = builder.build()
                // 设置定位数据
                mBaiduMap?.setMyLocationData(locData)
                val config = MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, mCurrentMarker)
                mBaiduMap?.setMyLocationConfiguration(config)

            }
        }
    }
}
