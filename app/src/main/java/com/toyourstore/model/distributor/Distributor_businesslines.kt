package com.toyourstore.model.distributor

import com.google.gson.annotations.SerializedName



data class Distributor_businesslines (

		@SerializedName("id") val id : Int,
		@SerializedName("user_id") val user_id : Int,
		@SerializedName("businessline_id") val businessline_id : Int,
		@SerializedName("created_at") val created_at : String,
		@SerializedName("updated_at") val updated_at : String,
		@SerializedName("businessline") val businessline : BusinesslineDis
)