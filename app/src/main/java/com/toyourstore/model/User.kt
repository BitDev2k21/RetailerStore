package com.toyourstore.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

@Entity(tableName = "table_user" )
data class User (
    @PrimaryKey
    @SerializedName("id")
    @Expose
    var id: Int? = null,
    @SerializedName("name")
    @Expose
    var name: String? = null,
    @SerializedName("email")
    @Expose
    var email: String? = null,
    @SerializedName("email_verified_at")
    @Expose
    var emailVerifiedAt: String? = null,
    @SerializedName("api_token")
    @Expose
    var apiToken: String? = null,
    @SerializedName("image")
    @Expose
    var image: String? = null,
    @SerializedName("user_type")
    @Expose
    var userType: String? = null,
    @SerializedName("contact")
    @Expose
    var contact: String? = null,
    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null,
    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null




)