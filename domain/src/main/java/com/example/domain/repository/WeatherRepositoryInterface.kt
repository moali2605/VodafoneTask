package com.example.domain.repository

import com.example.core.util.DataHolder
import com.example.domain.model.CitySearchResponseDomainModel
import com.example.domain.model.WeatherDomainModel

interface WeatherRepositoryInterface {
    suspend fun getWeather(lat: Double, lon: Double): DataHolder<WeatherDomainModel>
    suspend fun searchForCityByName(cityName: String): DataHolder<List<CitySearchResponseDomainModel>>
}