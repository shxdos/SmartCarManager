package com.shx.smartcarmanager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.shx.smartcarmanager.R
import com.shx.smartcarmanager.entity.DangerSourceType


/**
 * Created by 邵鸿轩 on 2017/7/17.
 */

class DangerSourceAdapter(private val mList: List<DangerSourceType>, private val mContext: Context) : BaseAdapter() {

    override fun getCount(): Int {
        return mList.size
    }

    override fun getItem(position: Int): Any {
        return mList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        var holder: ViewHolder? = null
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_spinner, null)
            holder = ViewHolder()
            holder.spinnerText = convertView!!.findViewById(R.id.tv_spinner) as TextView
            convertView.tag = holder
        }
        holder = convertView.tag as ViewHolder
        holder.spinnerText!!.text = mList[position].name
        return convertView
    }

    private inner class ViewHolder {
        var spinnerText: TextView? = null
    }
}
