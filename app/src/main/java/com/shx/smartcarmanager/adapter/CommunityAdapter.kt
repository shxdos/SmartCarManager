package com.shx.smartcarmanager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.shx.smartcarmanager.R

/**
 * Created by 邵鸿轩 on 2017/9/11.
 */
class CommunityAdapter constructor(): BaseAdapter() {
    var list: List<Object>? = null
    var mContext: Context? = null
    init {
    }
     constructor(list: List<Object>?,context: Context?) : this() {
        this.list = list
        this.mContext=context
    }



    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return LayoutInflater.from(mContext).inflate(R.layout.item_community,null)
    }

    override fun getItem(position: Int): Any {
        return list!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return list!!.size
    }
}