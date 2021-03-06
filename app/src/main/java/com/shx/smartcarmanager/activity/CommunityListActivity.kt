package com.shx.smartcarmanager.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import com.shx.smartcarmanager.R
import com.shx.smartcarmanager.adapter.CommunityAdapter
import com.shx.smartcarmanager.base.BaseActivity
import com.shx.smartcarmanager.entity.Community
import com.shx.smartcarmanager.libs.http.MyJSON
import com.shx.smartcarmanager.libs.http.RequestCenter
import com.shx.smartcarmanager.libs.http.ZCResponse

/**
 * Created by 邵鸿轩 on 2017/9/11.
 */
class CommunityListActivity : BaseActivity(), AdapterView.OnItemClickListener {


    var mListView: ListView? = null
    var mAdapter: CommunityAdapter? = null
    var mCommunityList: List<Community>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_communitylist)
        topbar!!.setTitle("社区管理")
        mListView = findViewById(R.id.lv_community) as ListView
        mAdapter = CommunityAdapter()
        mAdapter!!.mContext = this
        mListView!!.setOnItemClickListener(this)
        RequestCenter.getCommunityList("", "", "", 1, 10, this)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var intent = Intent(this, CommunityDetailsActivity::class.java)

        intent.putExtra("community", mCommunityList!!.get(position))
        startActivity(intent)
    }

    override fun doSuccess(respose: ZCResponse?, requestUrl: String?): Boolean {
        if (RequestCenter.communityList_url.equals(requestUrl)) {
            mCommunityList = MyJSON.parseArray(respose?.rows, Community::class.java)
            if (mCommunityList == null) {
                return super.doSuccess(respose, requestUrl)
            }
            mAdapter!!.list = mCommunityList
            mListView!!.setAdapter(mAdapter)
        }
        return super.doSuccess(respose, requestUrl)
    }
}