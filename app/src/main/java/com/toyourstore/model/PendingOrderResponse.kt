package com.toyourstore.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class PendingOrderResponse {

    @SerializedName("orders")
    @Expose
    var orders: List<Order> = emptyList()

    class Order {
        @SerializedName("id")
        @Expose
        var id: Int? = null
        @SerializedName("order_id")
        @Expose
        var orderId: Int? = null

        @SerializedName("order_no")
        @Expose
        var order_no: String = ""

        @SerializedName("shop_id")
        @Expose
        var shopId: Int? = null
        @SerializedName("location_id")
        @Expose
        var locationId: Int? = null
        @SerializedName("distributor_id")
        @Expose
        var distributorId: Int? = null
        @SerializedName("user_id")
        @Expose
        var userId: Int? = null
        @SerializedName("quantity")
        @Expose
        var quantity: Int? = null
        @SerializedName("total")
        @Expose
        var total: Int =  0
        @SerializedName("status")
        @Expose
        var status: String? = null
        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null
        @SerializedName("updated_at")
        @Expose
        var updatedAt: Any? = null
        @SerializedName("shop")
        @Expose
        var shop: Shop? = null
        @SerializedName("distributor")
        @Expose
        var distributor: Distributor? = null
        @SerializedName("orderitems")
        @Expose
        var orderitems: List<Orderitem>? = null

        class Orderitem {
            @SerializedName("id")
            @Expose
            var id: Int? = null

            @SerializedName("order_id")
            @Expose
            var orderId: Int? = null

            @SerializedName("product_id")
            @Expose
            var productId: Int? = null

            @SerializedName("requested_qty")
            @Expose
            var requestedQty: Int? = null

            @SerializedName("approved_qty")
            @Expose
            var approvedQty: Int? = null

            @SerializedName("unit_price")
            @Expose
            var unitPrice: Int? = null

            @SerializedName("total_price")
            @Expose
            var totalPrice: Int = 0

            @SerializedName("created_at")
            @Expose
            var createdAt: String? = null

            @SerializedName("updated_at")
            @Expose
            var updatedAt: Any? = null

            @SerializedName("product")
            @Expose
            var product: Product? = null

            class Product {
                @SerializedName("id")
                @Expose
                var id: Int? = null

                @SerializedName("category_id")
                @Expose
                var categoryId: Int? = null

                @SerializedName("businessline_id")
                @Expose
                var businesslineId: Int? = null

                @SerializedName("type_id")
                @Expose
                var typeId: Int? = null

                @SerializedName("company_id")
                @Expose
                var companyId: Int? = null

                @SerializedName("code")
                @Expose
                var code: String? = null

                @SerializedName("name")
                @Expose
                var name: String? = null

                @SerializedName("weight")
                @Expose
                var weight: Int? = null

                @SerializedName("exp_validity")
                @Expose
                var expValidity: String? = null

                @SerializedName("total")
                @Expose
                var total: String? = null

                @SerializedName("exp_validity_unit")
                @Expose
                var expValidityUnit: String? = null

                @SerializedName("unit_price")
                @Expose
                var unit_price: Int? = null

                @SerializedName("image")
                @Expose
                var image: Any? = null

                @SerializedName("status")
                @Expose
                var status: String? = null

                @SerializedName("created_at")
                @Expose
                var createdAt: String? = null

                @SerializedName("updated_at")
                @Expose
                var updatedAt: String? = null
            }

        }
        class Shop {
            @SerializedName("id")
            @Expose
            var id: Int? = null

            @SerializedName("name")
            @Expose
            var name: String? = null

            @SerializedName("owner_name")
            @Expose
            var ownerName: String? = null

            @SerializedName("contact")
            @Expose
            var contact: String? = null

            @SerializedName("working_hours")
            @Expose
            var workingHours: String? = null

            @SerializedName("location_id")
            @Expose
            var locationId: Int? = null

            @SerializedName("user_id")
            @Expose
            var userId: Int? = null

            @SerializedName("state_id")
            @Expose
            var stateId: Int? = null

            @SerializedName("city_id")
            @Expose
            var cityId: Int? = null

            @SerializedName("latitude")
            @Expose
            var latitude: Double? = null

            @SerializedName("longitude")
            @Expose
            var longitude: Double? = null

            @SerializedName("status")
            @Expose
            var status: String? = null

            @SerializedName("created_at")
            @Expose
            var createdAt: String? = null

            @SerializedName("updated_at")
            @Expose
            var updatedAt: String? = null
        }
        class Distributor {
            @SerializedName("id")
            @Expose
            var id: Int? = null

            @SerializedName("name")
            @Expose
            var name: String? = null

            @SerializedName("email")
            @Expose
            var email: String? = null

            @SerializedName("image")
            @Expose
            var image: Any? = null

            @SerializedName("email_verified_at")
            @Expose
            var emailVerifiedAt: Any? = null

            @SerializedName("user_type")
            @Expose
            var userType: String? = null

            @SerializedName("contact")
            @Expose
            var contact: String? = null

            @SerializedName("api_token")
            @Expose
            var apiToken: String? = null

            @SerializedName("created_at")
            @Expose
            var createdAt: String? = null

            @SerializedName("updated_at")
            @Expose
            var updatedAt: String? = null
        }




    }



}