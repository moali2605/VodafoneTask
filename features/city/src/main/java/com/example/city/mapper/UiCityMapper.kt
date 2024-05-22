package com.example.city.mapper

import com.example.domain.model.CityDomainModel
import com.example.domain.model.CitySearchResponseDomainModel


fun CitySearchResponseDomainModel.toCityDomainModel() = CityDomainModel(
    cityName = "$name, $state, $country" , cityLat = latitude , cityLong = longitude
)