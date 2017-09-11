package com.shx.smartcarmanager.fragment

import android.os.Bundle
import android.widget.ListView
import com.shx.smartcarmanager.R
import com.shx.smartcarmanager.adapter.CommunityAdapter
import com.shx.smartcarmanager.base.BaseActivity
import com.shx.smartcarmanager.libs.http.RequestCenter

/**
 * Created by 邵鸿轩 on 2017/9/11.
 */
class CommunityListActivity : BaseActivity() {
    var listView: ListView? = null
    var mAdapter: CommunityAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_communitylist)
        listView = findViewById(R.id.lv_community) as ListView
        mAdapter=CommunityAdapter()
        mAdapter!!.mContext=this
        RequestCenter.getCommunityList("","","",1,30,this)
    }

}