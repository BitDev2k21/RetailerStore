package com.toyourstore.model.distributor

import com.google.gson.annotations.SerializedName

data class User (
		@SerializedName("id") val id : Int,
		@SerializedName("name") val name : String,
		@SerializedName("email") val email : String,
		@SerializedName("image") val image : String,
		@SerializedName("email_verified_at") val email_verified_at : String,
		@SerializedName("user_type") val user_type : String,
		@SerializedName("contact") val contact : Int,
		@SerializedName("api_token") val api_token : String,
		@SerializedName("created_at") val created_at : String,
		@SerializedName("updated_at") val updated_at : String,
		@SerializedName("distributor_details") val distributor_details : List<Distributor_details>,
		@SerializedName("distributor_locations") val distributor_locations : List<Distributor_locations>,
		@SerializedName("distributor_businesslines") val distributor_businesslines : List<Distributor_businesslines>,
		@SerializedName("distributor_companies") val distributor_companies : List<Distributor_companies>
)