package com.toyourstore.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetLocation {

    @SerializedName("locations")
    @Expose
    var locations: List<Location?>? = null

    class Location {

        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("code")
        @Expose
        var code: String? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("city_id")
        @Expose
        var cityId: Int? = null

        @SerializedName("state_id")
        @Expose
        var stateId: Int? = null

        @SerializedName("pincode")
        @Expose
        var pincode: String? = null

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

        @SerializedName("state")
        @Expose
        var state: State? = null

        @SerializedName("city")
        @Expose
        var city: City? = null
        var isSelected: Boolean = false

    }

    class State {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("description")
        @Expose
        var description: String? = null

        @SerializedName("status")
        @Expose
        var status: String? = null
    }

    class City {
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