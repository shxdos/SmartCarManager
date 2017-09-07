package com.shx.smartcarmanager.activity

import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import com.shx.smartcarmanager.R
import com.shx.smartcarmanager.base.BaseActivity

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : BaseActivity() {

    // UI references.
    private var mEmailView: AutoCompleteTextView? = null
    private var mPasswordView: EditText? = null
    private var mProgressView: View? = null
    private var mLoginFormView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // Set up the login form.
        mEmailView = findViewById(R.id.email) as AutoCompleteTextView

        mPasswordView = findViewById(R.id.password) as EditText

        val mEmailSignInButton = findViewById(R.id.email_sign_in_button) as Button

        mLoginFormView = findViewById(R.id.login_form)
    }






}

