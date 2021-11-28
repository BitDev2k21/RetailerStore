package com.toyourstore.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class OperatorsResponse {

    @SerializedName("operator_team")
    @Expose
    var operatorTeam: List<OperatorTeam?>? = null

    class OperatorTeam {
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
        var apiToken: Any? = null

        @SerializedName("distributor_id")
        @Expose
        var distributorId: Int? = null

        @SerializedName("identity")
        @Expose
        var identity: String? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: Any? = null

        @SerializedName("updated_at")
        @Expose
        var updatedAt: Any? = null
    }


}