package com.example.data.mapper

import com.example.data.model.CityEntity
import com.example.domain.model.CityDomainModel

fun CityEntity.toCityDomainModel() = CityDomainModel(
    cityName = cityName, cityLat = cityLat, cityLong = cityLong
)

fun CityDomainModel.toCityEntity() = CityEntity(
    cityName = cityName, cityLat = cityLat, cityLong = cityLong
)