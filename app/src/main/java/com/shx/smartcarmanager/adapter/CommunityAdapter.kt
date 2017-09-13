package com.shx.smartcarmanager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.shx.smartcarmanager.R
import com.shx.smartcarmanager.entity.Community

/**
 * Created by 邵鸿轩 on 2017/9/11.
 */
class CommunityAdapter constructor() : BaseAdapter() {
    var list: List<Community>? = null
    var mContext: Context? = null

    init {
    }

    constructor(list: List<Community>?, context: Context?) : this() {
        this.list = list
        this.mContext = context
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var holder: ViewHolder
        var v: View
        if (convertView == null) {
            v = LayoutInflater.from(mContext).inflate(R.layout.item_community, null)
            holder = ViewHolder()
            holder.communityName=v.findViewById(R.id.tv_name) as TextView
            v.tag = holder
        } else {
            v = convertView
            holder = v.tag as ViewHolder
        }
        holder.communityName!!.text = list!![position]?.name
        return v
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

    class ViewHolder {
        var communityName: TextView? = null
    }
}