package com.shx.smartcarmanager.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.shx.smartcarmanager.R
import com.shx.smartcarmanager.base.BaseActivity
import com.shx.smartcarmanager.libs.http.RequestCenter
import com.shx.smartcarmanager.libs.http.ZCResponse

/**
 * A login screen that offers login via username/password.
 */
class LoginActivity : BaseActivity(), View.OnClickListener {


    // UI references.
    private var mUserNameView: EditText? = null
    private var mPasswordView: EditText? = null
    private var mButtonLogin: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mUserNameView = findViewById(R.id.user_name) as EditText

        mPasswordView = findViewById(R.id.password) as EditText

        mButtonLogin = findViewById(R.id.btn_login) as Button
        mButtonLogin!!.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_login -> {
                RequestCenter.doLogin(mUserNameView!!.text.toString(), mPasswordView!!.text.toString(), this)
            }
        }
    }

    override fun doSuccess(respose: ZCResponse?, requestUrl: String?): Boolean {
        intent = Intent(this, CommunityListActivity::class.java)
        startActivity(intent)
        return super.doSuccess(respose, requestUrl)
    }
}

