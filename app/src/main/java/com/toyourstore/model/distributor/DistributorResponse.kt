package com.toyourstore.model.distributor

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class DistributorResponse(
		@SerializedName("message")
		@Expose
		var message: String? = null,
		@SerializedName("user") val user: List<User>
)