package com.retailer.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class InventoryResponse {

    @SerializedName("inventory")
    @Expose
     var inventory: List<Inventory?>? = null

    class Inventory {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("distributor_id")
        @Expose
        var distributorId: Int? = null

        @SerializedName("product_id")
        @Expose
        var productId: Int? = null

        @SerializedName("dist_price")
        @Expose
        var distPrice: Int? = null

        @SerializedName("inventory")
        @Expose
        var inventory: Int? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: Any? = null

        @SerializedName("updated_at")
        @Expose
        var updatedAt: Any? = null

        @SerializedName("products")
        @Expose
        var products: Any? = null
    }

}