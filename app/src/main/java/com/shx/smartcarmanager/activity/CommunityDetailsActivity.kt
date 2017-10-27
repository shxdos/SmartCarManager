package com.shx.smartcarmanager.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
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
import com.shx.smartcarmanager.entity.DangerSourceType
import com.shx.smartcarmanager.libs.http.MyJSON
import com.shx.smartcarmanager.libs.http.RequestCenter
import com.shx.smartcarmanager.libs.http.ZCResponse
import com.baidu.mapapi.map.BitmapDescriptorFactory
import com.baidu.mapapi.map.InfoWindow
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener
import com.shx.smartcarmanager.Utils.ImageUtils
import com.shx.smartcarmanager.commons.LogGloble
import com.shx.smartcarmanager.commons.SystemConfig
import com.shx.smartcarmanager.entity.DangerSource
import org.json.JSONObject


class CommunityDetailsActivity : BaseActivity(), OnTraceListener, View.OnClickListener {


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
    var mDangerSourceTypeName: String? = null
    var mDangerSourceType: DangerSourceType? = null
    var mCurrentLat: Double? = null
    var mCurrentLng: Double? = null
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
        topbar.setRightImageListener(this)
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
        LogGloble.d("ddddd",requestUrl+"=="+RequestCenter.dangersource_url)

        if (requestUrl.equals(RequestCenter.enclosure_url)) {
            mLatLngList = MyJSON.parseArray(respose?.rows, com.shx.smartcarmanager.entity.LatLng::class.java)
            if (mLatLngList == null || mLatLngList!!.size <= 0) {
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
                    .zoom(17f)
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

        }else if (requestUrl.equals(RequestCenter.dangersource_url)) {
            mDangerSourceList = MyJSON.parseArray(respose?.rows, DangerSource::class.java)
            if (mDangerSourceList == null) {
                return super.doSuccess(respose, requestUrl)
            }
            for (item in mDangerSourceList!!) {
                //定义Maker坐标点
                var points=MyJSON.parseObject(item.c_points)
                val point = LatLng(points.getDouble("lat"),points.getDouble("lng"))
                //构建Marker图标
                var bitmap=ImageUtils.getBitmapFromNetWork(SystemConfig.BASE_HOST_RELEASE+"/images/"+item.icon)
                var bitmapDescriptor:BitmapDescriptor?=null
                if(bitmap==null){
                    bitmapDescriptor=BitmapDescriptorFactory.fromResource(R.drawable.ic_dangersource)

                }else{
                    bitmapDescriptor= BitmapDescriptorFactory.fromBitmap(bitmap)
                }

                //构建MarkerOption，用于在地图上添加Marker
                var bundle=Bundle()
                bundle.putSerializable("info",item)
                val option = MarkerOptions()
                        .position(point)
                        .perspective(true)
                        .icon(bitmapDescriptor)
                        .extraInfo(bundle)
                //在地图上添加Marker，并显示
                mBaiduMap!!.addOverlay(option)
                mBaiduMap!!.setOnMarkerClickListener(OnMarkerClickListener { marker ->
                    //创建InfoWindow展示的view
                    val view = LayoutInflater.from(this).inflate(R.layout.dialog_danger,null)
                    var name=view.findViewById(R.id.tv_name) as TextView
                    var type=view.findViewById(R.id.tv_type) as TextView
                    var createTime=view.findViewById(R.id.tv_creattime) as TextView
                    var createUser=view.findViewById(R.id.tv_createuser) as TextView
                    var dangerSource=bundle.getSerializable("info") as DangerSource
                    name.setText(dangerSource.name)
                    type.setText(dangerSource.danger_source_type)
                    createTime.setText(dangerSource.create_time)
                    createUser.setText(dangerSource.create_user_id)

                    //定义用于显示该InfoWindow的坐标点
                    val pt = LatLng(marker.position.latitude,marker.position.longitude)
                    //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
                    val mInfoWindow = InfoWindow(view, pt, -47)
                    //显示InfoWindow
                    mBaiduMap!!.showInfoWindow(mInfoWindow)
                    true
                })

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
                mCurrentLat = bdLocation!!.latitude
                mCurrentLng = bdLocation!!.longitude
                var builder = MyLocationData.Builder()
                        .accuracy(bdLocation!!.radius)
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100f)
                //@TODO 修改为真实定位
                if (mLatLngList != null && mLatLngList!!.size > 0) {
                    builder.latitude(mLatLngList!![0].lat!!)
                            .longitude(mLatLngList!![0].lng!!)
                    mCurrentLat = mLatLngList!![0].lat!!
                    mCurrentLng = mLatLngList!![0].lng!!
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            mDangerSourceTypeName = intent.getStringExtra("name")
            mDangerSourceType = intent.getSerializableExtra("type") as DangerSourceType?
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.iv_right -> {
                var intent = Intent(this, AddDangerSourceActivity::class.java)
                intent.putExtra("currentLat", mCurrentLat.toString())
                intent.putExtra("currentLng", mCurrentLng.toString())
                intent.putExtra("communityId", mCommunity!!.id)
                startActivity(intent)
            }

        }
    }

}
