package com.example.data.mapper

import com.example.data.model.CityResponse
import com.example.domain.model.CityResponseDomainModel

fun CityResponse.toCityResponseDomainModel() = CityResponseDomainModel(
    name = name ?: "",
    latitude = latitude ?: 0.0,
    longitude = longitude ?: 0.0,
    country = country ?: "",
    state = state ?: ""
)