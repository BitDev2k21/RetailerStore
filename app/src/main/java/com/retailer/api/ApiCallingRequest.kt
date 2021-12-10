package com.retailer.api

import com.google.gson.JsonObject
import com.retailer.model.*
import com.retailer.model.businessline.BusinessLineModelResponse
import com.retailer.model.cart.AddCartResponse
import com.retailer.model.cart.CartListResponse
import com.retailer.model.distributor.DistributorResponse
import com.retailer.model.products.GetCompanyResponse
import com.retailer.model.products.GetProductTypeResponse
import com.retailer.model.products.ProductResponse
import com.retailer.model.shoplist.ShopListResponse

class ApiCallingRequest : com.retailer.api.SafeApiRequest() {

    var apiService: com.retailer.api.ApiService

    init {
        apiService = com.retailer.api.ApiService.Factory.invoke()
    }

    suspend fun apiCallingForLogin(
            params: HashMap<String, String>
    ): LoginResponse {
        return apiRequest { apiService.login(params) }
    }


    suspend fun apiCallingForGetUser(
        params: HashMap<String, String>
    ): UserResponse {
        return apiRequest { apiService.getUser(params) }
    }


    suspend fun apiCallingBusinessLine(
            params: HashMap<String, String>
    ): BusinessLineModelResponse {
        return apiRequest { apiService.apiCallingBusinessLine(params) }
    }

    suspend fun apiCallingForDashBoard(
            params: HashMap<String, String>
    ): DashBoardRetailer {
        return apiRequest { apiService.apiForDashBoard(params) }
    }

    suspend fun apiCallingForUpdateDist(
            params: HashMap<String, String>
    ): DistributorResponse {
        return apiRequest { apiService.apiCallingUpdateDist(params) }
    }

    suspend fun apiCallingRegister(
            params: HashMap<String, String>
    ): LoginResponse {
        return apiRequest { apiService.register(params) }
    }

    suspend fun getLocationList(
            params: HashMap<String, String>
    ): GetLocation {
        return apiRequest { apiService.getLocationListApi(params) }
    }

    suspend fun apiCallingAddShop(
            params: HashMap<String, String>
    ): LoginResponse {
        return apiRequest { apiService.apiCallingForAddShop(params) }
    }

    suspend fun apiCallingAUpdateShop(
            params: HashMap<String, String>
    ): LoginResponse {
        return apiRequest { apiService.apiCallingForUpdateShop(params) }
    }

    suspend fun apiCallingForSateResponse(
            params: HashMap<String, String>
    ): StateResponse {
        return apiRequest { apiService.stateList(params) }
    }

    suspend fun apiCallingForCityResponse(
            params: HashMap<String, String>
    ): CityResponse {
        return apiRequest { apiService.cityList(params) }
    }


    suspend fun apiCallingForOrderList(
            params: HashMap<String, String>
    ): PendingOrderResponse {
        return apiRequest { apiService.orderList(params) }
    }

    suspend fun apiCallingForRetailerOrderList(
            params: HashMap<String, String>
    ): PendingOrderResponse {
        return apiRequest { apiService.retailerOrderList(params) }
    }

    suspend fun apiCallingForRetailerPastOrder(
            params: HashMap<String, String>
    ): PendingOrderResponse {
        return apiRequest { apiService.retailerPastOrderList(params) }
    }

    suspend fun apiCallingForShopList(
            params: HashMap<String, String>
    ): ShopListResponse {
        return apiRequest { apiService.apishopList(params) }
    }


    suspend fun apiCartList(
            params: HashMap<String, String>
    ): CartListResponse {
        return apiRequest { apiService.apiCartList(params) }
    }

    suspend fun apiCallingForProducts(
            params: HashMap<String, String>
    ): ProductResponse {
        return apiRequest { apiService.apiCallingForProducts(params) }
    }

    suspend fun addCart(
            params: HashMap<String, String>
    ): AddCartResponse {
        return apiRequest { apiService.addCart(params) }
    }

    suspend fun updateCart(
            params: HashMap<String, String>
    ): AddCartResponse {
        return apiRequest { apiService.updateCart(params) }
    }

    suspend fun getTypeList(
        params: HashMap<String, String>
    ): GetProductTypeResponse {
        return apiRequest { apiService.getTypeList(params) }
    }

    suspend fun getCompanyList(
        params: HashMap<String, String>
    ): GetCompanyResponse {
        return apiRequest { apiService.getCompanyList(params) }
    }


    suspend fun deleteCart(
            params: HashMap<String, String>
    ): AddCartResponse {
        return apiRequest { apiService.deleteCart(params) }
    }

    suspend fun createOrder(
            params: HashMap<String, String>
    ): JsonObject {
        return apiRequest { apiService.createOrder(params) }
    }

    suspend fun apiCallingForOperator(
            params: HashMap<String, String>
    ): OperatorsResponse {
        return apiRequest { apiService.apiOperator(params) }
    }

    suspend fun apiDeleteShop(
            params: HashMap<String, String>
    ): String {
        return apiRequest { apiService.deleteShop(params) }
    }

    suspend fun apiCallingInventry(
            params: HashMap<String, String>
    ): InventoryResponse {
        return apiRequest { apiService.apiInventry(params) }
    }


}


