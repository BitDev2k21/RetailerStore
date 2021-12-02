package com.retailer.model.products

import com.retailer.model.ShelfLife


data class ProductRequest (
		var shelfLife : String?,
		var shelfLifeUnit : String?,
		var productType : ArrayList<ProductType>?,
		var company : ArrayList<Company>?,
		var priceRangeMin : String?,
		var priceRangeMax : String?,
		var priceMarginMin : String?,
		var priceMarginMax : String?,
		var mostOrderedProduct : Boolean?,
)