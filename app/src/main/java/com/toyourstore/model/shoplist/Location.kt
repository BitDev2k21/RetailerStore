package com.toyourstore.model.shoplist

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location (

	val id : Int,
	val code : String,
	val name : String,
	val city_id : Int,
	val state_id : Int,
	val pincode : Int,
	val latitude : Double,
	val longitude : Double,
	val status : Int,
	val created_at : String,
	val updated_at : String
)  : Parcelable