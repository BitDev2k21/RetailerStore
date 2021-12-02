package com.retailer.model.cart

import com.google.gson.annotations.SerializedName

data class CartListResponse(
	@SerializedName("cart_total") val cart_total : String,
	@SerializedName("cart") val carts: List<Cart>
)