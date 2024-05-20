package com.example.myapplication.domain.model

import java.io.Serializable

data class CityDomainModel(
    val cityName: String,
    val cityLat: Double,
    val cityLong: Double
): Serializable
