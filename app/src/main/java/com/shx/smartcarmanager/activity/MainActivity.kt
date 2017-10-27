package com.shx.smartcarmanager.activity

import android.os.Build
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.MenuItem
import android.widget.Toast
import com.shx.smartcarmanager.R
import com.shx.smartcarmanager.base.BaseActivity
import com.shx.smartcarmanager.fragment.MainFragment

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var mMainFragment: Fragment? = null
    private var mFragmentManager: FragmentManager? = null
    private var mBottomNavigationView: BottomNavigationView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getPersimmions()
        }
        topbar!!.setTitle("汽车管家")
        mBottomNavigationView = findViewById(R.id.bottom_navigation_view) as BottomNavigationView
        mBottomNavigationView!!.setOnNavigationItemSelectedListener(this)
        mBottomNavigationView!!.itemTextColor = resources.getColorStateList(R.drawable.selector_color_menu)
        mBottomNavigationView!!.itemIconTintList = resources.getColorStateList(R.drawable.selector_color_menu)
        mFragmentManager = supportFragmentManager
        mMainFragment = MainFragment()
        mBottomNavigationView!!.selectedItemId=R.id.item_home

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val transaction = mFragmentManager!!.beginTransaction()
        when (item.itemId) {
            R.id.item_home -> {
                transaction.replace(R.id.content, mMainFragment)
            }
            R.id.item_zoom -> {
                Toast.makeText(this, "社区", Toast.LENGTH_LONG).show()
            }

            R.id.item_my -> {
                Toast.makeText(this, "我的", Toast.LENGTH_LONG).show()
            }

        }
        transaction.commitAllowingStateLoss()
        return true
    }

}
