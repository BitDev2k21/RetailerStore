package com.toyourstore.model.distributor

import com.google.gson.annotations.SerializedName




data class Distributor_locations (

		@SerializedName("id") val id : String,
		@SerializedName("user_id") val user_id : String,
		@SerializedName("location_id") val location_id : String,
		@SerializedName("created_at") val created_at : String,
		@SerializedName("updated_at") val updated_at : String,
		@SerializedName("locations") val locations : Locations
)