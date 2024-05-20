package com.example.domain.model

data class CityResponseDomainModel(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String,
    val state: String
)
