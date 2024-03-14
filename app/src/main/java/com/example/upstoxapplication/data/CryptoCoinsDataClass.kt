package com.example.upstoxapplication.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Cryptocurrencies (

    @SerializedName("name")
    @Expose
    var name : String?  = null,

    @SerializedName("symbol")
    @Expose
    val symbol: String?  = null,

    @SerializedName("is_new")
    @Expose
    val isNew: Boolean? = null,

    @SerializedName("is_active")
    @Expose
    val isActive: Boolean? = null,

    @SerializedName("type")
    @Expose
    val type: String?  = null
)