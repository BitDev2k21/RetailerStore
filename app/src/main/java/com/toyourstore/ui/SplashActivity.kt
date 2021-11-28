package com.toyourstore.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import com.toyourstore.R
import com.toyourstore.preference.SessionData

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        try {
            init()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun init() {
        Handler().postDelayed({
            if(TextUtils.isEmpty(SessionData.getInstance(this).getToken()) ){
                val intent =  Intent(this, LoginActivity::class.java)
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish()
            } else  {
                val intent =  Intent(this, MainActivity::class.java)
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish()
            }
         }, 3000)
     }
}