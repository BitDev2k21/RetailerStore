package com.toyourstore.model.distributor

import com.google.gson.annotations.SerializedName



data class BusinesslineDis (

		@SerializedName("id") val id : Int,
		@SerializedName("category_id") val category_id : Int,
		@SerializedName("name") val name : String,
		@SerializedName("image") val image : String,
		@SerializedName("status") val status : Int,
		@SerializedName("created_at") val created_at : String,
		@SerializedName("updated_at") val updated_at : String
)