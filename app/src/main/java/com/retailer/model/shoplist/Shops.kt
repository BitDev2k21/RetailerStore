package com.retailer.model.shoplist

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Shops (
	val id : Int,
	val name : String,
	val gst_no:String,
	val address:String,
	val owner_name : String,
	val contact : Int,
	val working_hours : String,
	val location_id : Int,
	val user_id : Int,
	val state_id : Int,
	val city_id : Int,
	val latitude : Double,
	val longitude : Double,
	val status : Int,
	val created_at : String,
	val updated_at : String,
	val location : Location
)  : Parcelable