package com.shx.smartcarmanager.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.TextView
import com.baidu.location.BDLocation
import com.baidu.mapapi.map.*
import com.baidu.trace.model.OnTraceListener
import com.baidu.trace.model.PushMessage
import com.shx.smartcarmanager.R
import com.shx.smartcarmanager.base.BaseActivity
import com.shx.smartcarmanager.base.LocationService
import com.shx.smartcarmanager.base.TranceService
import com.shx.smartcarmanager.entity.Community
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
            if (mLatLngList == null) {
                return super.doSuccess(respose, requestUrl)
            }
            val pts = ArrayList<com.baidu.mapapi.model.LatLng>()
            for (item in mLatLngList!!) {
                val pt1 = com.baidu.mapapi.model.LatLng(item!!.lat!!, item!!.lng!!)
                pts.add(pt1)
            }
            //构建用户绘制多边形的Option对象
            val polygonOption = PolygonOptions()
                    .points(pts)
                    .stroke(Stroke(5, 0xAA00FF00.toInt()))
                    .fillColor(0xAAFFFF00.toInt())
            //在地图上添加多边形Option，用于显示
            mBaiduMap!!.addOverlay(polygonOption)

        }
        return super.doSuccess(respose, requestUrl)
    }

    inner class MyReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (LocationService::class.java.simpleName == intent.action) {
                val bundle = intent.extras
                val bdLocation = bundle.getParcelable<BDLocation>("bdLocation")
                // 构造定位数据
                val locData = MyLocationData.Builder()
                        .accuracy(bdLocation!!.radius)
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100f)
//                        .latitude(bdLocation.latitude)
//                        .longitude(bdLocation.longitude)
                        .latitude(40.056483)
                        .longitude(116.306812)
                        .build()
                // 设置定位数据
                mBaiduMap?.setMyLocationData(locData)
                mBaiduMap?.setMapStatus(MapStatusUpdateFactory.zoomTo(40f))
                val config = MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, mCurrentMarker)
                mBaiduMap?.setMyLocationConfiguration(config)

            }
        }
    }
}
