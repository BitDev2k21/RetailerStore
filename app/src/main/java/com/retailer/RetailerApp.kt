package com.retailer

import android.app.Application
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class RetailerApp : Application() {
    override fun onCreate() {
        super.onCreate()
//        FacebookSdk.sdkInitialize(applicationContext)
//        AppEventsLogger.activateApp(this)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.v("@@@", "FirebaseMessaging Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = ""
            Log.v("@@@","FirebaseMessaging token : $token" )
        })
    }
}