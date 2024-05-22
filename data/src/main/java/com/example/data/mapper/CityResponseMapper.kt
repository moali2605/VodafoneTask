package com.example.data.mapper

import com.example.data.model.CitySearchResponse
import com.example.domain.model.CitySearchResponseDomainModel

fun CitySearchResponse.toCityResponseDomainModel() = CitySearchResponseDomainModel(
    name = name ?: "",
    latitude = latitude ?: 0.0,
    longitude = longitude ?: 0.0,
    country = country ?: "",
    state = state ?: ""
)