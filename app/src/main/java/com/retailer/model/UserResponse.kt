package com.retailer.model
import com.google.gson.annotations.SerializedName
import com.retailer.model.distributor.Distributor_businesslines
import com.retailer.model.distributor.Distributor_companies
import com.retailer.model.distributor.Distributor_details
import com.retailer.model.distributor.Distributor_locations

data class UserResponse(
    @SerializedName("user") val user : List<GetUser>,
    @SerializedName("image_path") val image_path : String
)
data class GetUser (
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("email") val email : String,
    @SerializedName("image") val image : String,
    @SerializedName("email_verified_at") val email_verified_at : String,
    @SerializedName("user_type") val user_type : String,
    @SerializedName("contact") val contact : String,
    @SerializedName("api_token") val api_token : String,
    @SerializedName("distributor_id") val distributor_id : String,
    @SerializedName("identity") val identity : String,
    @SerializedName("mobile") val mobile : String,
    @SerializedName("social_id") val social_id : String,
    @SerializedName("device_token") val device_token : String,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("updated_at") val updated_at : String,
    @SerializedName("distributor_details") val distributor_details : List<Distributor_details>,
    @SerializedName("distributor_locations") val distributor_locations : List<Distributor_locations>,
    @SerializedName("distributor_businesslines") val distributor_businesslines : List<Distributor_businesslines>,
    @SerializedName("distributor_companies") val distributor_companies : List<Distributor_companies>
)

