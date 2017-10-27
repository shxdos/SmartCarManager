package com.shx.smartcarmanager.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ListView

import com.shx.smartcarmanager.R
import com.shx.smartcarmanager.adapter.DangerSourceAdapter
import com.shx.smartcarmanager.base.BaseActivity
import com.shx.smartcarmanager.entity.DangerSourceType
import com.shx.smartcarmanager.libs.dialog.ToastUtil
import com.shx.smartcarmanager.libs.http.HttpCallBack
import com.shx.smartcarmanager.libs.http.MyJSON
import com.shx.smartcarmanager.libs.http.RequestCenter
import com.shx.smartcarmanager.libs.http.ZCResponse

class AddDangerSourceActivity : BaseActivity(), HttpCallBack, AdapterView.OnItemClickListener {
    var mCommunityId:String??=null
    var mCurrentLat:String??=null
    var mCurrentLng:String??=null
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if(TextUtils.isEmpty(mDangerName!!.text.toString())){
            ToastUtil.getInstance().toastInCenter(this,"危险源名称不能为空")
            return
        }
        var dangerSourceType=parent!!.adapter.getItem(position) as DangerSourceType
        var cPoints=String.format("{\"lng\":%S,\"lat\":%S}",mCurrentLng,mCurrentLat)
        RequestCenter.SaveDangersource(mCurrentLat!!,mCurrentLng!!,cPoints,mDangerName!!.text.toString(),mCommunityId!!,dangerSourceType.code!!,dangerSourceType.icon!!,this)

    }


    private var mDangerListView: ListView? = null
    private var mDangerName: EditText? = null
    var mAdapter: DangerSourceAdapter? = null
    var mList: List<DangerSourceType>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_danger_source)
        mDangerListView = findViewById(R.id.lv_danger) as ListView?
        mDangerName = findViewById(R.id.et_dangername) as EditText?
        RequestCenter.getDangersourceType(1, 10, this)
        mCurrentLat= intent.getStringExtra("currentLat")
        mCurrentLng= intent.getStringExtra("currentLng")
        mCommunityId=intent.getStringExtra("communityId")
        mDangerListView!!.setOnItemClickListener(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }

    override fun doSuccess(respose: ZCResponse?, requestUrl: String?): Boolean {
        if (RequestCenter.dangersourcetype_url.equals(requestUrl)) {
            mList = MyJSON.parseArray(respose?.rows, DangerSourceType::class.java)
            if (mList == null) {
                return super.doSuccess(respose, requestUrl)
            }
            mAdapter = DangerSourceAdapter(mList!!, this)
            mDangerListView!!.setAdapter(mAdapter)
        }
        return super.doSuccess(respose, requestUrl)
    }

}
