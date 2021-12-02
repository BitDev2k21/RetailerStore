package com.retailer.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class DashBoardResponse {
    @SerializedName("data")
    @Expose
     var data: Data? = null

    class Data {

        @SerializedName("pending_orders")
        @Expose
        var pendingOrders: Int? = null
        @SerializedName("pending_payments")
        @Expose
        var pendingPayments: Int? = null

    }

}