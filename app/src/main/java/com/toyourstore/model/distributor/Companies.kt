package com.toyourstore.model.distributor

import com.google.gson.annotations.SerializedName



data class Companies (

		@SerializedName("id") val id : Int,
		@SerializedName("location_id") val location_id : Int,
		@SerializedName("code") val code : String,
		@SerializedName("name") val name : String,
		@SerializedName("address_1") val address_1 : String,
		@SerializedName("address_2") val address_2 : String,
		@SerializedName("image") val image : String,
		@SerializedName("status") val status : Int,
		@SerializedName("created_at") val created_at : String,
		@SerializedName("updated_at") val updated_at : String
)