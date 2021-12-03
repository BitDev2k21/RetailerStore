package com.retailer.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class CityResponse {
    @SerializedName("cities")
    @Expose
     var cities: List<City?>? = null

    class City {
        var isSelected: Boolean = false
        @SerializedName("id")
        @Expose
        var id: Int? = null
        @SerializedName("name")
        @Expose
        var name: String? = null
        @SerializedName("districts_id")
        @Expose
        var districtsId: Int? = null
        @SerializedName("states_id")
        @Expose
        var statesId: Int? = null
        @SerializedName("description")
        @Expose
        var description: String? = null
        @SerializedName("status")
        @Expose
        var status: String? = null
    }


}