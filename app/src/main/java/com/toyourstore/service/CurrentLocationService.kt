package com.toyourstore.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.*
import java.text.DateFormat
import java.util.*

class CurrentLocationService : Service() {

    private var context: Context? = null
    private val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 2000
    private val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2
    private var mSettingsClient: SettingsClient? = null
    private var mLocationRequest: LocationRequest? = null
    private var mLocationSettingsRequest: LocationSettingsRequest? = null
    private var mLocationCallback: LocationCallback? = null
    private var mCurrentLocation: Location? = null
    private var mRequestingLocationUpdates: Boolean=false
    private var mLastUpdateTime: String? = null
    private val SMALLEST_DISPLACEMENT = 1.0f
    private  var fusedLocationProviderClient: FusedLocationProviderClient?=null
    private val TAG=CurrentLocationService::class.java.simpleName

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        context = this
        mSettingsClient = LocationServices.getSettingsClient(this)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        createLocationCallback()
        createLocationRequest()
        buildLocationSettingsRequest()
        startLocationUpdates()
        return START_STICKY
    }

    /**
     * Creates a callback for receiving location events.
     */
    private fun createLocationCallback() {
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                mCurrentLocation = locationResult.lastLocation
                mLastUpdateTime = DateFormat.getTimeInstance().format(Date())
                updateLocationUI()
            }
        }
    }

    private fun stopLocationUpdates() {
        if (!mRequestingLocationUpdates!!) {
            return
        }
        fusedLocationProviderClient?.removeLocationUpdates(mLocationCallback)?.addOnCompleteListener {
            mRequestingLocationUpdates = false
        }

    }

    @SuppressLint("RestrictedApi")
    private fun createLocationRequest() {
        mLocationRequest = LocationRequest()
        mLocationRequest?.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS)
        mLocationRequest?.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS)
        mLocationRequest?.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        mLocationRequest?.setSmallestDisplacement(SMALLEST_DISPLACEMENT)
    }


    private fun buildLocationSettingsRequest() {
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest!!)
        mLocationSettingsRequest = builder.build()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        mSettingsClient?.checkLocationSettings(mLocationSettingsRequest)?.addOnSuccessListener {
            mRequestingLocationUpdates = true
            fusedLocationProviderClient!!.
            requestLocationUpdates(
                mLocationRequest,
                mLocationCallback,
                Looper.myLooper()
            )
            updateLocationUI()
        }?.addOnFailureListener {
            val statusCode = (it  as ApiException).statusCode
            when (statusCode) {
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                    Log.i(
                        TAG,
                        "Location settings are not satisfied. Attempting to upgrade " +
                                "location settings "
                    )
                }
                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                    val errorMessage = "Location settings are inadequate, and cannot be " +
                            "fixed here. Fix in Settings."
                    Log.e(TAG, errorMessage)
                    mRequestingLocationUpdates = false
                }
            }
            updateLocationUI()
        }
    }

    /**
     * Sets the value of the UI fields for the location latitude, longitude and last update time.
     */
    private fun updateLocationUI() {
        if (mCurrentLocation != null) {
            val intent = Intent("current-location-event")
            intent.putExtra("message", "This is my message!")
            intent.putExtra("latitude", mCurrentLocation!!.latitude)
            intent.putExtra("longitude", mCurrentLocation!!.longitude)
            mCurrentLocation
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                intent.putExtra("speed", mCurrentLocation!!.speedAccuracyMetersPerSecond)
            }
            LocalBroadcastManager.getInstance(context!!).sendBroadcast(intent)
        }
    }


    override fun onDestroy() {
        stopLocationUpdates()
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }




}