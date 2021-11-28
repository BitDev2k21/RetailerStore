package com.toyourstore.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import android.R
import android.app.Activity
import android.app.AlertDialog

import android.content.Intent

import android.content.DialogInterface

import android.location.LocationManager
import android.provider.Settings
import java.lang.Exception
import android.text.TextUtils

import android.provider.Settings.SettingNotFoundException

import android.os.Build
import android.widget.Toast


object PermissionUtils {

    fun isPermissionForLocation(context: Context): Boolean {
        if ((ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) === PackageManager.PERMISSION_GRANTED)
            && ((ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) === PackageManager.PERMISSION_GRANTED)
                    )
        ) {
            return true
        } else {
            return false
        }
    }

    fun checkLocationEnabled(context: Context,activity: Activity):Boolean{
        val locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return true
        } else {
            Toast.makeText(context, "GPS is not enabled,please turn on GPS and retry", Toast.LENGTH_SHORT).show()
            return false
        }
    }

    // Request permissions if not granted before
    @SuppressLint("MissingPermission")
    fun requestPermissions(fragment: Fragment, pERMISSION_ID: Int) {
            fragment.requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                pERMISSION_ID
            )
        }
    }

