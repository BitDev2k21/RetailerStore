package com.toyourstore.model.distributor

import com.google.gson.annotations.SerializedName


data class Distributor_companies (

		@SerializedName("id") val id : Int,
		@SerializedName("user_id") val user_id : Int,
		@SerializedName("company_id") val company_id : Int,
		@SerializedName("created_at") val created_at : String,
		@SerializedName("updated_at") val updated_at : String,
		@SerializedName("companies") val companies : Companies
)