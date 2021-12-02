package com.retailer.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

open class MyFirebaseInstanceIdService: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.v("@@@", "MyFirebaseInstanceIdService onNewToken Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
//        sendRegistrationToServer(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.v("@@@ ", "MyFirebaseInstanceIdService onMessageReceived Message :: $message")
    }

}