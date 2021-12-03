package com.retailer.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class DashBoardRetailer {
    @SerializedName("data")
    @Expose
     var data: Data? = null

    class Data {
        @SerializedName("items_in_cart")
        @Expose
         var itemsInCart: Int? = null
        @SerializedName("pending_orders")
        @Expose
         var pendingOrders: Int? = null
      }

}