package com.toyourstore.api

import com.google.gson.JsonObject
import com.toyourstore.model.*
import com.toyourstore.model.businessline.BusinessLineModelResponse
import com.toyourstore.model.cart.AddCartResponse
import com.toyourstore.model.cart.CartListResponse
import com.toyourstore.model.distributor.DistributorResponse
import com.toyourstore.model.products.GetCompanyResponse
import com.toyourstore.model.products.GetProductTypeResponse
import com.toyourstore.model.products.ProductResponse
import com.toyourstore.model.shoplist.ShopListResponse
import de.footprinttech.wms.retrofit.RetrofitProvider
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @Headers(
            value = ["Accept: application/json",
                "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("user-login")
    suspend fun login(@FieldMap request: Map<String, String>): Response<LoginResponse>


    @Headers(
            value = ["Accept: application/json",
                "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("delete-shop")
    suspend fun deleteShop(@FieldMap request: Map<String, String>): Response<String>


    @Headers(
            value = ["Accept: application/json",
                "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("distributor-pending-orders")
    suspend fun orderList(@FieldMap request: Map<String, String>): Response<PendingOrderResponse>

    @Headers(
            value = ["Accept: application/json",
                "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("retailer-pending-orders")
    suspend fun retailerOrderList(@FieldMap request: Map<String, String>): Response<PendingOrderResponse>

    @Headers(
            value = ["Accept: application/json",
                "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("retailer-order-list")
    suspend fun retailerPastOrderList(@FieldMap request: Map<String, String>): Response<PendingOrderResponse>


    @Headers(
            value = ["Accept: application/json",
                "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("get-shop-list")
    suspend fun apishopList(@FieldMap request: Map<String, String>): Response<ShopListResponse>


    @Headers(
            value = ["Accept: application/json",
                "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("cart-item")
    suspend fun apiCartList(@FieldMap request: Map<String, String>): Response<CartListResponse>

    @Headers(
            value = ["Accept: application/json",
                "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("get-product-list")
    suspend fun apiCallingForProducts(@FieldMap request: Map<String, String>): Response<ProductResponse>


    @Headers(
            value = ["Accept: application/json",
                "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("add-cart-item")
    suspend fun addCart(@FieldMap request: Map<String, String>): Response<AddCartResponse>

    @Headers(
            value = ["Accept: application/json",
                "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("update-cart-item")
    suspend fun updateCart(@FieldMap request: Map<String, String>): Response<AddCartResponse>

    @Headers(
        value = ["Accept: application/json",
            "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("get-type-list")
    suspend fun getTypeList(@FieldMap request: Map<String, String>): Response<GetProductTypeResponse>

    @Headers(
        value = ["Accept: application/json",
            "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("get-company-list")
    suspend fun getCompanyList(@FieldMap request: Map<String, String>): Response<GetCompanyResponse>

    @Headers(
            value = ["Accept: application/json",
                "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("delete-cart-item")
    suspend fun deleteCart(@FieldMap request: Map<String, String>): Response<AddCartResponse>

    @Headers(
            value = ["Accept: application/json",
                "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("create-order")
    suspend fun createOrder(@FieldMap request: Map<String, String>): Response<JsonObject>


    @Headers(
            value = ["Accept: application/json",
                "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("distributor-operator-team")
    suspend fun apiOperator(@FieldMap request: Map<String, String>): Response<OperatorsResponse>

    @Headers(
            value = ["Accept: application/json",
                "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("distributor-inventory")
    suspend fun apiInventry(@FieldMap request: Map<String, String>): Response<InventoryResponse>


    @Headers(
            value = ["Accept: application/json",
                "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("dashboard")
    suspend fun apiForDashBoard(@FieldMap request: Map<String, String>): Response<DashBoardRetailer>


    @Headers(
            value = ["Accept: application/json",
                "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("user-registration")
    suspend fun register(@FieldMap request: Map<String, String>): Response<LoginResponse>


    @Headers(
            value = ["Accept: application/json",
                "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("get-location-list")
    suspend fun getLocationListApi(@FieldMap request: Map<String, String>): Response<GetLocation>


    @Headers(
            value = ["Accept: application/json",
                "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("get-state-list")
    suspend fun stateList(@FieldMap request: Map<String, String>): Response<StateResponse>

    @Headers(
            value = ["Accept: application/json",
                "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("get-city-list")
    suspend fun cityList(@FieldMap request: Map<String, String>): Response<CityResponse>


    @Headers(
            value = ["Accept: application/json",
                "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("update-distributor-details")
    suspend fun apiCallingUpdateDist(@FieldMap request: Map<String, String>): Response<DistributorResponse>


    @Headers(
            value = ["Accept: application/json",
                "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("get-businessline-list")
    suspend fun apiCallingBusinessLine(@FieldMap request: Map<String, String>): Response<BusinessLineModelResponse>


    @Headers(
            value = ["Accept: application/json",
                "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("add-shop")
    suspend fun apiCallingForAddShop(@FieldMap request: Map<String, String>): Response<LoginResponse>

    @Headers(
            value = ["Accept: application/json",
                "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("update-shop")
    suspend fun apiCallingForUpdateShop(@FieldMap request: Map<String, String>): Response<LoginResponse>

    companion object Factory {
        operator fun invoke(): ApiService {
            return RetrofitProvider.getRetrofitInstance().create(ApiService::class.java)
        }

    }


}