package com.toyourstore.model.businessline

import com.google.gson.annotations.SerializedName

data class BusinessLineModelResponse(
        @SerializedName("businessline") val businessline : List<Businessline>,
        @SerializedName("image_path") val image_path : String
)