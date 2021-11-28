package com.toyourstore.model.businessline

import com.google.gson.annotations.SerializedName



data class Producttype (

		@SerializedName("id") val id : Int,
		@SerializedName("businessline_id") val businessline_id : Int,
		@SerializedName("code") val code : String,
		@SerializedName("name") val name : String,
		@SerializedName("image") val image : String,
		@SerializedName("status") val status : Int,
		@SerializedName("created_at") val created_at : String,
		@SerializedName("updated_at") val updated_at : String
)