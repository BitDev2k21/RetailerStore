package com.toyourstore.model.distributor

import com.google.gson.annotations.SerializedName





data class Distributor_details (

		@SerializedName("id") val id : String,
		@SerializedName("name") val name : String,
		@SerializedName("owner_name") val owner_name : String,
		@SerializedName("contact") val contact : String,
		@SerializedName("working_hours") val working_hours : String,
		@SerializedName("gst_no") val gst_no : String,
		@SerializedName("routes") val routes : String,
		@SerializedName("credit_period") val credit_period : String,
		@SerializedName("address") val address : String,
		@SerializedName("user_id") val user_id : String,
		@SerializedName("state_id") val state_id : String,
		@SerializedName("city_id") val city_id : String,
		@SerializedName("latitude") val latitude : String,
		@SerializedName("longitude") val longitude : String,
		@SerializedName("status") val status : String,
		@SerializedName("created_at") val created_at : String,
		@SerializedName("updated_at") val updated_at : String
)