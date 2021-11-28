package com.toyourstore.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("token")
    @Expose
    var token: String? = null,
    //message
    @SerializedName("message")
    @Expose
    var message: String? = null,
    @SerializedName("user")
    @Expose
    var user: User? = null




)

