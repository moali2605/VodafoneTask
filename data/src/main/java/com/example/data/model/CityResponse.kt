package com.example.data.model

import com.google.gson.annotations.SerializedName

data class CityResponse (
    @SerializedName("name") val name: String?,
    @SerializedName("lat") val latitude: Double?,
    @SerializedName("lon") val longitude: Double?,
    @SerializedName("country") val country: String?,
    @SerializedName("state") val state: String?
)
