package com.toyourstore.model.distributor

import com.google.gson.annotations.SerializedName


data class Locations (
		@SerializedName("id") val id : String,
		@SerializedName("code") val code : String,
		@SerializedName("name") val name : String,
		@SerializedName("city") val city : String,
		@SerializedName("state") val state : String,
		@SerializedName("pincode") val pincode : String,
		@SerializedName("latitude") val latitude : Double,
		@SerializedName("longitude") val longitude : Double,
		@SerializedName("status") val status : String,
		@SerializedName("created_at") val created_at : String,
		@SerializedName("updated_at") val updated_at : String

)