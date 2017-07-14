package com.shx.smartcarmanager.base

import android.graphics.drawable.Drawable
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView
import com.shx.smartcarmanager.R

/**
 * Created by 邵鸿轩 on 2017/7/13.
 */
class ToolBarView {
    private var mToolBar: Toolbar? = null
    private var titleView: TextView? = null
    private var rightView: TextView? = null

    constructor(view: View?) {
        if (view == null) {
            return
        }
        mToolBar = view as Toolbar
        titleView = view.findViewById(R.id.title) as TextView
        rightView=view.findViewById(R.id.tv_right)as TextView
    }

    /**
     * 设置中间文字
     */
    fun setTitle(title: String) {
        titleView?.text = title
    }

    /**
     * 设置左侧图标
     */
    fun setLeftImage(drawable: Drawable) {
        mToolBar?.navigationIcon = drawable
    }

    /**
     * 设置左侧文字
     */
    fun setLeftText(str: String) {
        mToolBar?.title = str
    }

    fun setRightText(str: String) {
        rightView?.text=str
    }

}